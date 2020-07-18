package fr.bastion.rest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;

import fr.bastion.rest.specificMessages.SpecificMessages;
import fr.bastion.utiles.HttpMethod;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;

public class RestServiceManagement {

	// Configurations.
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RestServiceManagement.class);
	private static Processor proc = new Processor(Configuration.newConfiguration());
	private static DocumentBuilder builder = proc.newDocumentBuilder();
	private static XPathCompiler compiler = proc.newXPathCompiler();
	private static XdmNode contextNode = null;

	// Parameters.
	private HttpMethod method;
	private String url;
	private static String headerCookie = null;
	private String body;
	private Path outputFilePath;

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
			// TODO: classPath or pom.xml. 
			contextNode = builder.build(new File("src/main/resources/messages/parameters.xml"));
			headerCookie = applyXpath("parameters/parameter/cookies/@value");
			if (headerCookie==null || headerCookie.isBlank()) {
				throw new IllegalArgumentException("Cookies have been eaten!");
			}
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}
		logger.info("Cookies loaded");
	}

	public void setParameter(SpecificMessages source) {
		//this.method = source.getMethod();
		//this.url = source.getUrl();
		//this.body = source.getBody();
		this.outputFilePath = Paths.get(applyXpath("parameters/parameter[@name='"+source.getClass().getSimpleName()+"']/outputFile/@path"));
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

		//RestService.restMessage(method, url, headerCookie, body, outputFilePath);

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
