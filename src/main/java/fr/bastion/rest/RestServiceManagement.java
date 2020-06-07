package fr.bastion.rest;

import java.io.File;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;

public class RestServiceManagement {
	//config
	private Processor proc;
	private DocumentBuilder builder;
	private XPathCompiler compiler;
	private XdmNode contextNode;
	
	//params
	private String method;
	private String url;
	private String header;
	private String body;
	private String outputFile;
	
	public void setParameter(String source) {
		try {
			//Configuration
			proc = new Processor(Configuration.newConfiguration());
			builder = proc.newDocumentBuilder();
			compiler = proc.newXPathCompiler();
			contextNode = builder.build(new File(source)); //Chargement du document
		
			//Initialisations des params
			this.method = applyXpath("document/parameters/uri/@method");
			this.url = applyXpath("document/parameters/uri/@url");
			//this.header = applyXpath("");
			this.body = applyXpath("document/parameters/body/@value");
			this.outputFile = applyXpath("document/parameters/outputFile/@path");
			
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}

	}
	
	private String applyXpath(String xpath) {
		try {
			XPathSelector seq = compiler.compile(xpath).load(); //Compilation du xpath
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
		new ApiRest().messageRest(method, url, header, body, outputFile);
	}

	@Override
	public String toString() {
		return "Method: " + method + "\nUrl: " + url + "\nHeader: " + header + "\nBody: " + body
				+ "\nOutputFile: " + outputFile;
	}
	
	
		
		

}
