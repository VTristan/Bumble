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
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;

public class ApiRest {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ApiRest.class);
	
	public void messageRest(String method, String url, String headerCookie, String body, String outputFile) {
		
		checkingFile(outputFile);
		
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpUriRequestBase verbs = null;

			switch (method) {
			case "GET":
				verbs = new HttpGet(url);
				break;
			case "POST":
				verbs = new HttpPost(url);
				verbs.addHeader("cookie",headerCookie);
				verbs.addHeader("x-use-session-cookie","1");
				verbs.setEntity(new StringEntity(body,StandardCharsets.UTF_8));
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
				throw new IllegalArgumentException("Method is not correct "+method+". Exepected:[GET,POST,PUT,PATCH,DELETE]");
			}

			try (CloseableHttpResponse response = httpclient.execute(verbs)) {
				logger.info("{} : {}", new Object[] { response.getCode(), response.getReasonPhrase() });
				//TODO: Throw exception if response is like 'Session not found' despite 200 code.

				response.addHeader("Content-Type", "charset=UTF-8");
				HttpEntity entity = response.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()),StandardCharsets.UTF_8));
				String output;
				String date = new SimpleDateFormat("'['dd/MM/yyyy hh:mm:ss']'").format(new Date());
				
				while ((output = br.readLine()) != null) {
					Files.writeString(Paths.get(outputFile), date + output + ",\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
				}
				EntityUtils.consume(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkingFile(String file) {
		if (Files.notExists(Paths.get(file), LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createFile(Paths.get(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
