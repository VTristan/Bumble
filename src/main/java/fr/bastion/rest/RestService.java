package fr.bastion.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class RestService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RestService.class);

	protected static void displayParameters(String method, String url, String headerCookie, String body, String outputFilePath) {
		logger.info("method: " + method + " url:" + url + " headerCookie:" + headerCookie + " body:" + body + " outputFilePath:" + outputFilePath);
	}

	protected static void restMessage(String method, String url, String headerCookie, String body, String outputFilePath) {
		displayParameters(method, url, headerCookie, body, outputFilePath);
		
		HttpUriRequestBase verbs = request(method, url, headerCookie, body);
		response(verbs, outputFilePath);
	}

	private static HttpUriRequestBase request(String method, String url, String headerCookie, String body) {
		HttpUriRequestBase verbs = null;

		switch (method) {
		case "GET":
			verbs = new HttpGet(url);
			break;
		case "POST":
			verbs = new HttpPost(url);
			verbs.addHeader("cookie", headerCookie);
			// Always like this.
			verbs.addHeader("x-use-session-cookie", "1");
			verbs.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
			break;
		case "PUT":
			verbs = new HttpPut(url);
			break;
		case "PATCH":
			verbs = new HttpPatch(url);
			break;
		case "DELETE":
			verbs = new HttpDelete(url);
			break;
		default:
			throw new IllegalArgumentException("Method is not correct " + method + ". Exepected:[GET,POST,PUT,PATCH,DELETE]");
		}
		return verbs;
	}

	private static void response(HttpUriRequestBase verbs, String outputFile) {
		try (CloseableHttpClient httpclient = HttpClients.createDefault();
				CloseableHttpResponse response = httpclient.execute(verbs)) {
			// TODO: Throw exception if response is like 'Session not found' despite 200 code.
			logger.info("{} : {}", new Object[] { response.getCode(), response.getReasonPhrase() });

			if (outputFile != null && !outputFile.isBlank()) {
				copyResponse(response, outputFile);
			}

			 EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copyResponse(CloseableHttpResponse response, String outputFile) {
		checkingOutputFilePath(outputFile);

		try (BufferedReader br = new BufferedReader( new InputStreamReader((response.getEntity().getContent()), StandardCharsets.UTF_8))) {
			String output;
			String date = new SimpleDateFormat("'['dd/MM/yyyy hh:mm:ss']'").format(new Date());

			while ((output = br.readLine()) != null) {
				Files.writeString(Paths.get(outputFile), date + output + ",\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void checkingOutputFilePath(String file) {
		if (Files.notExists(Paths.get(file), LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createFile(Paths.get(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}