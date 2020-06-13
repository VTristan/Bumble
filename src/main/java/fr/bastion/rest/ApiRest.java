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
	
	public void messageRest(String method, String url, String header, String body, String outputFile) {
		
		checkingFile(outputFile);
		
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpUriRequestBase verbs = null;

			switch (method) {
			case "GET":
				verbs = new HttpGet(url);
				break;
			case "POST":
				verbs = new HttpPost(url);
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
			//TODO: verbs.setEntity(new StringEntity(header));
			verbs.setEntity(new StringEntity("Cookie: session_cookie_name=session; device_id=f4c46bd3-6bd3-d33c-3c87-874756af82cb; aid=792169440; cpc=%7B%22c%22%3A1%2C%22e%22%3A1906672397470%2C%22u%22%3A%22792169440%22%7D; _scid=dfc6bb96-1c90-4958-b2bd-f452524c0966; _ga=GA1.2.409567416.1591318000; _pin_unauth=dWlkPU1XTmxOR1ZsTmpRdFlUUTRZUzAwTURnekxUazBZV1F0TjJGak5qZ3habUZtWkRFeQ; _derived_epik=dj0yJnU9RkJZZ2M1TndFMVJNOXY5bEdRWDhWLXNoQ1lGOTlJNUEmbj1ZWnd3UFByWFFodWdJTFFoeUhBNW1RJm09MSZ0PUFBQUFBRjdiY21jJnJtPTEmcnQ9QUFBQUFGN2JjbWM; SL_GWPT_Show_Hide_tmp=1; SL_wptGlobTipTmp=1; HDR-X-User-id=792169440; session=s1:427:a7Q2ZaQHzXHHnGclSD91oUYNqNqL9mgJQBDVNHOO\r\n" + 
					"x-use-session-cookie: 1"));
			
			//TODO: No body in case of GET method
			verbs.setEntity(new StringEntity(body));

			try (CloseableHttpResponse response = httpclient.execute(verbs)) {
				logger.info("{} : {}", new Object[] { response.getCode(), response.getReasonPhrase() });
				//TODO: Lancer une exception si le contenu de la 'response' est une erreure de type : 'Session not found' malgré un code 200.

				HttpEntity entity = response.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
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
