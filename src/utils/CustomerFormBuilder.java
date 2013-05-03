package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.User;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CustomerFormBuilder {
	
	public static String build(User user) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		InputStream emptyForm = requestEmptyForm();
		Document doc = setUserInfo(user, emptyForm);
		return getXMLResult(doc);
	}
	
	private static InputStream requestEmptyForm(){
		String url = Functions.urlConcat(Vars.wsServer,Vars.wsCustomersPath+"/?ws_key="+Vars.wsKey+"&schema=blank");
		Response resp = new Response(url);
		InputStream in = resp.getResponse();
		return in;
	}
	
	private static Document setUserInfo(User user, InputStream emptyForm) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(emptyForm);
		
		doc.normalize();
		
		System.out.println("CustomerFormRequester: setUserInfo: firstChildName: "+doc.getFirstChild().getNodeName());
		
		Node prestashop = doc.getFirstChild();
		Node customer = doc.getElementsByTagName("customer").item(0);
		NodeList list = customer.getChildNodes();
		
		for(int i=0;i<list.getLength();i++){
			Node node = list.item(i);
			
			if(node.getNodeName().equals("email")){
				node.setTextContent(user.getLogin());
			}
			if(node.getNodeName().equals("passwd")){
				node.setTextContent(user.getPass());
			}
			if(node.getNodeName().equals("firstname")){
				node.setTextContent(user.getFirstname());
			}
			if(node.getNodeName().equals("lastname")){
				node.setTextContent(user.getLastname());
			}
			if(node.getNodeName().equals("active")){
				node.setTextContent("1");
			}
		}
		return doc;
		
	}
	
	private static String getXMLResult(Document doc) throws TransformerException{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		
		StreamResult resu =  new StreamResult(new StringWriter());
		transformer.transform(source, resu);
		String xmlString = resu.getWriter().toString();
		
		return xmlString;
	}
	
}
