package fr.bastion.rest.specificMessages;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import fr.bastion.rest.RestService;
import fr.bastion.utiles.HttpMethod;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;

public abstract class SpecificMessages {

	// Parameters.
	private static String headerCookie = null;
	protected HttpMethod method = null;
	protected String url = null;
	protected String body = null;
	protected Path outputFilePath = null;


	// Configurations.
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SpecificMessages.class);
	private static Processor proc = new Processor(Configuration.newConfiguration());
	private static DocumentBuilder builder = proc.newDocumentBuilder();
	private static XPathCompiler compiler = proc.newXPathCompiler();
	private static XdmNode contextNode = null;
	
	// Constructor.
	public SpecificMessages() {
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
	
	/**
	 * If the extends-class have an outputFilePath, implements this:</br>
	 * @Override public void loadOutputFilePath() {</br> super.outputFilePath = Paths.get(super.applyXpath("parameters/parameter[@name='"+this.getClass().getSimpleName()+"']/outputFile/@path"));</br>}</br>
	 *</br>Do not forget to invoke this method from the constructor of the extends-class !
	 */
	protected abstract void loadOutputFilePath();

	protected String applyXpath(String xpath) {
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
		//RestService.displayParameters(method, url, headerCookie, body, outputFilePath);
		//TODO:à faire dans la classe Bumble get encounter
	Map<String,String> dataBaseParameters = new HashMap<String,String>();
	dataBaseParameters.put("dabaseName", "test");
	dataBaseParameters.put("collectionName", "BumbleGetEncouters");
		
	//RestService.restMessage(method, url, headerCookie, body,null, outputFilePath);
	RestService.restMessage(method, url, headerCookie, body, dataBaseParameters, null);
	}
	
	public void messaging(String body) {
		//RestService.displayParameters(method, url, headerCookie, body, outputFilePath);
		RestService.restMessage(method, url, headerCookie, body, null, null);
	}

}
