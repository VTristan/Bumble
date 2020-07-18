package fr.bastion.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;

import fr.bastion.dao.DaoMongo;
import fr.bastion.utiles.HttpMethod;

public class RestService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RestService.class);
//Rendre outputFilePath + genric :source TODO
	
	public static void displayParameters(HttpMethod method, String url, String headerCookie, String body, Object output) {
		logger.info("method: " + method + " url:" + url + " headerCookie:" + headerCookie + " body:" + body + " output:" + output);
	}

//	public static void restMessage(HttpMethod method, String url, String headerCookie, String body, Path outputFilePath) {
//		displayParameters(method, url, headerCookie, body, outputFilePath);
//		
//		HttpUriRequestBase verbs = request(method, url, headerCookie, body, outputFilePath);
//		response(verbs, outputFilePath);
//	}
	
	public static void restMessage(HttpMethod method, String url, String headerCookie, String body, Map<String,String> dataBaseParameters) {
		displayParameters(method, url, headerCookie, body, dataBaseParameters);
		
		HttpUriRequestBase verbs = request(method, url, headerCookie, body);
		response(verbs, dataBaseParameters);
	}

	private static HttpUriRequestBase request(HttpMethod method, String url, String headerCookie, String body) {
		HttpUriRequestBase verbs = null;

		switch (method) {
		case GET:
			verbs = new HttpGet(url);
			break;
		case POST:
			verbs = new HttpPost(url);
			verbs.addHeader("cookie", headerCookie);
			// Always like this.
			verbs.addHeader("x-use-session-cookie", "1");
			verbs.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
			break;
		case PUT:
			verbs = new HttpPut(url);
			break;
		case PATCH:
			verbs = new HttpPatch(url);
			break;
		case DELETE:
			verbs = new HttpDelete(url);
			break;
		default:
			throw new IllegalArgumentException("Method is not correct " + method + ". Exepected:[GET,POST,PUT,PATCH,DELETE]");
		}
		return verbs;
	}

	private static void response(HttpUriRequestBase verbs, Map<String,String> dataBaseParameters) {
		try (CloseableHttpClient httpclient = HttpClients.createDefault();
				CloseableHttpResponse response = httpclient.execute(verbs)) {
			logger.info("{} : {}", new Object[] { response.getCode(), response.getReasonPhrase() });

//			if (output != null && !output.toString().isBlank()) {
//				copyResponse(response, output);
//			}
			if (dataBaseParameters != null) {
				copyResponseInDataBase(response, dataBaseParameters);
			}

			 EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copyResponseInDataBase(CloseableHttpResponse response, Map<String,String> dataBaseParameters) {
		DaoMongo dao = DaoMongo.getInstance();
		String base = dataBaseParameters.get("dabaseName");
		String collection = dataBaseParameters.get("collectionName");
		String newContent = null;
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), StandardCharsets.UTF_8))) {
			String line = null;
			String content = "";
//			String date = new SimpleDateFormat("'['dd/MM/yyyy hh:mm:ss']'").format(new Date());

			while ((line = br.readLine()) != null) {
				content = content +line;
			}
			newContent = content.replace('$','e');
			dao.insertOneString(base, collection, newContent);
			
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

//	private static void copyResponse(CloseableHttpResponse response, Path outputFile) {
//		checkingOutputFilePath(outputFile);
//
//		try (BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), StandardCharsets.UTF_8))) {
//			String output;
//			String date = new SimpleDateFormat("'['dd/MM/yyyy hh:mm:ss']'").format(new Date());
//
//			while ((output = br.readLine()) != null) {
//				Files.writeString(outputFile, date + output + ",\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
//			}
//		} catch (UnsupportedOperationException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static void checkingOutputFilePath(Path outputFile) {
//		if (Files.notExists(outputFile, LinkOption.NOFOLLOW_LINKS)) {
//			try {
//				Files.createDirectories(outputFile.getParent());
//				Files.createFile(outputFile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}