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

public class RestServiceManagement {

	// Configurations.
	private static Processor proc = new Processor(Configuration.newConfiguration());
	private static DocumentBuilder builder = proc.newDocumentBuilder();
	private static XPathCompiler compiler = proc.newXPathCompiler();
	private static XdmNode contextNode = null;
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RestServiceManagement.class);

	// Parameters.
	private String method;
	private String url;
	private static String header = null;
	private String body;
	//TODO: Path better than String.
	private String outputFile;

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
			// I use Paths.get(source).toFile() but I can also use the class io.File.
			contextNode = builder.build(Paths.get("src/main/resources/messages/cookies.xml").toFile());
			header = applyXpath("document/parameter/cookies/@value");
			if (header.isBlank()) {
				throw new IllegalArgumentException("Cookies have been eaten!");
			}
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}
		//TODO: Remove logger.
		logger.info("Cookies loaded");
	}

	public void setParameter(String source) {
		try {
			// Load source. 
			// I use Paths.get(source).toFile() but I can also use the class io.File.
			contextNode = builder.build(Paths.get(source).toFile());

			// Initialization parameters. 
			// TODO: Add documentation for the x-path.
			this.method = applyXpath("document/parameters/uri/@method");
			this.url = applyXpath("document/parameters/uri/@url");
			this.body = applyXpath("document/parameters/body/@value");
			this.outputFile = applyXpath("document/parameters/outputFile/@path");
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
		//messageRest or restMessage?
		ApiRest.messageRest(method, url, header, body, outputFile);
	}

	@Override
	public String toString() {
		return "Method: " + method + "\nUrl: " + url + "\nHeader: " + header + "\nBody: " + body + "\nOutputFile: "
				+ outputFile;
	}

}
