package fr.bastion.rest;

import java.nio.file.Paths;
import org.slf4j.Logger;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;

//TODO: Change name. InitializerRestService?
public class RestServiceManagement {

	// Configurations.
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RestServiceManagement.class);
	private static Processor proc = new Processor(Configuration.newConfiguration());
	private static DocumentBuilder builder = proc.newDocumentBuilder();
	private static XPathCompiler compiler = proc.newXPathCompiler();
	private static XdmNode contextNode = null;

	// Parameters.
	private String method;
	private String url;
	private static String headerCookie = null;
	private String body;
	//TODO: Path better than String.
	private String outputFilePath;

	// Constructor.
	public RestServiceManagement() {
		// Singleton.
		if (contextNode == null) {
			loadCookies();
		}
	}

	private void loadCookies() {
		try {
			// Configuration.
			// TODO: Add path in the classPath or in the pom.xml. 
			// TODO: Add documentation for the x-path.
			contextNode = builder.build(Paths.get("src/main/resources/messages/cookies.xml").toFile());
			headerCookie = applyXpath("document/parameter/cookies/@value");
			if (headerCookie==null || headerCookie.isBlank()) {
				throw new IllegalArgumentException("Cookies have been eaten!");
			}
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}
		logger.info("Cookies loaded");
	}

	public void setParameter(String source) {
		try {
			// Load source. 
			contextNode = builder.build(Paths.get(source).toFile());

			// Initialization parameters. 
			// TODO: Add documentation for the x-path.
			this.method = applyXpath("document/parameters/uri/@method");
			this.url = applyXpath("document/parameters/uri/@url");
			this.body = applyXpath("document/parameters/body/@value");
			this.outputFilePath = applyXpath("document/parameters/outputFile/@path");
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}
	}

	private String applyXpath(String xpath) {
		try {
			// x-path compilation.
			XPathSelector seq = compiler.compile(xpath).load();
			seq.setContextItem(contextNode);
			String parameter = null;
			for (XdmItem item : seq) {
				parameter = item.getStringValue();
			}
			return parameter;
		} catch (SaxonApiException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void messaging() {
		RestService.displayParameters(method, url, headerCookie, body, outputFilePath);
		RestService.restMessage(method, url, headerCookie, body, outputFilePath);

		//ApiRest.restMessage(method, url, header, body, outputFile);
	}
	
	public void messaging(String body) {
		RestService.displayParameters(method, url, headerCookie, body, outputFilePath);
		//RestService.restMessage(method, url, headerCookie, body, outputFilePath);
	}

	@Override
	public String toString() {
		return "Method: " + method + "\nUrl: " + url + "\nHeader: " + headerCookie + "\nBody: " + body + "\nOutputFile: " + outputFilePath;
	}

}
