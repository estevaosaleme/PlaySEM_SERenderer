package br.ufes.inf.lprm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Utils {

	private static ServerSocket serverSocket;
	
    public static boolean checkIfAlreadyRunning() throws IOException {
    	ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    	
        try {
        	serverSocket = new ServerSocket(65535);
	    	return false;
        }
	    catch (Exception ex){
	    	return true;
	    }
    }

    static class ShutdownHook extends Thread {
        public void run() {
        	try {
        		if (serverSocket != null)
        			serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public static String fileToBuffer(InputStream is) throws IOException {
	    StringBuilder sb = new StringBuilder("");
	    try (BufferedReader rdr = new BufferedReader(new InputStreamReader(is))) { 
	        for (int c; (c = rdr.read()) != -1;) {
	            sb.append((char) c);
	        }
	    }
	    return sb.toString();
	}
    
    public static String getConfigurationValue(String attributeToReturn, String itemToSearch, String xpathToSearch, File xmlFile) {
    	String textFound = "";
    	try {
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	Document doc = builder.parse(xmlFile);
			XPathFactory xPathfactory = XPathFactory.newInstance();
	    	XPath xpath = xPathfactory.newXPath();
	    	XPathExpression expr;
	    	if (itemToSearch.isEmpty())
	    		expr = xpath.compile(xpathToSearch);
	    	else
	    		expr = xpath.compile(xpathToSearch+"[text()='"+itemToSearch+"']");
	    	NodeList nodeList = (NodeList)(expr.evaluate(doc, XPathConstants.NODESET));
	    	if (nodeList.getLength() == 1) {
	    	    Node parent = nodeList.item(0).getParentNode();
	    	    for (int i =0; i< parent.getChildNodes().getLength(); i++) {
	    	    	if (parent.getChildNodes().item(i).getNodeName() == attributeToReturn) {
	    	    		textFound = parent.getChildNodes().item(i).getFirstChild().getNodeValue();
	    	    		break;
	    	    	}
				}
	    	}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    	return textFound;
    }
    
    public static HashMap<String, String> getConfigurationListValues(String attributeToReturn, String itemToSearch, String xpathToSearch, File xmlFile) {
    	HashMap<String, String> listValues = null;
    	try {
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	Document doc = builder.parse(xmlFile);
			XPathFactory xPathfactory = XPathFactory.newInstance();
	    	XPath xpath = xPathfactory.newXPath();
	    	XPathExpression expr = xpath.compile(xpathToSearch+"[text()='"+itemToSearch+"']");
	    	NodeList nodeList = (NodeList)(expr.evaluate(doc, XPathConstants.NODESET));
	    	if (nodeList.getLength() == 1) {
	    	    Node parent = nodeList.item(0).getParentNode();
	    	    for (int i =0; i< parent.getChildNodes().getLength(); i++) {
	    	    	if (parent.getChildNodes().item(i).getNodeName() == attributeToReturn) {
	    	    		listValues = new HashMap<String, String>();
	    	    		for (int j =0; j< parent.getChildNodes().item(i).getChildNodes().getLength(); j++)
	    	    			if (parent.getChildNodes().item(i).getChildNodes().item(j).hasChildNodes())
	    	    				listValues.put(parent.getChildNodes().item(i).getChildNodes().item(j).getNodeName(), parent.getChildNodes().item(i).getChildNodes().item(j).getFirstChild().getNodeValue());
	    	    		break;
	    	    	}
				}
	    	}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    	return listValues;
    }
    
    public static int normalizeValue(byte data){
		if (data < 0)
		    return data + 256;
		else
			return data;
	}
    
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
}
