package com.p.jackson;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerStuff {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		
		/** Define various path Strings */
		final String OBJECT_PATH = "src/main/java/com/p/jackson/";
		final String XML_FILE_PATH = "src/main/resources/PersonsPackage.xml";
		final String TEMPLATE_FOLDER_PATH = "templates";
		final String FTL_FILE_PATH = "test.ftl";
		final String JAVA_CLASS = ".java";
		
		System.out.println("Configuring Template Loading...");
		
		/** Create and adjust the configuration singleton */		
		Configuration cfg = new Configuration();		
		/** Specify template location */
		cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FOLDER_PATH));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		
		System.out.println("Processing XML File...");
		
		/** Specify xml file location */
		File fXmlFile = new File(XML_FILE_PATH);
		
		/** Build DOM and parse xml file */
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		/** Grab root node ObjectInventory */
		Node root = null;
		Element rootElement = null;
		NodeList rootNode = doc.getElementsByTagName("ObjectInventory");
		for (int i = 0; i < rootNode.getLength(); i++) {
			root = rootNode.item(i);
			rootElement = (Element) root;
		}
		
		/** Create package String path */
		String PACKAGE_PATH = rootElement.getAttribute("package") + "/";
		
		/** Grab nodeList of Objects; For every Object create a java class file */
		NodeList objList = doc.getElementsByTagName("Object");	
		for (int i = 0; i < objList.getLength(); i++) {
			Node obj = objList.item(i);			
			if (obj.getNodeType() == Node.ELEMENT_NODE) {
				/** Create Element object; Set root 'package' attr to Object */
				Element eElement = (Element) obj;
				eElement.setAttribute("package", rootElement.getAttribute("package"));
				Node newObj = eElement.cloneNode(true);
				/** Create a map and process the template */
				Map rootMap = new HashMap();
				rootMap.put("doc", NodeModel.wrap(newObj));				
				Template temp = cfg.getTemplate(FTL_FILE_PATH);
							
				System.out.println("Creating java class for " + eElement.getAttribute("name") + " in package " + PACKAGE_PATH + "...");
				
				/** Create dir/file String paths */
				String dirPath = OBJECT_PATH + PACKAGE_PATH;
				String javaClassPath = OBJECT_PATH + PACKAGE_PATH + eElement.getAttribute("name") + JAVA_CLASS;
				
				/** Create directories */
				File dirPathCreate = new File(dirPath);
				dirPathCreate.mkdirs();
				
				/** Create file */
				File javaFile = new File(javaClassPath);
				javaFile.createNewFile();
				
				/** Write to class; Merge data-model with template */
				PrintWriter writer = new PrintWriter(javaClassPath);
				temp.process(rootMap, writer);
			    writer.close();			    
			}
		}
		System.out.println("Done.");
	}
}
