package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.User;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.AsyncTask;

public class CustomerFormRequester extends AsyncTask<User, Void, Boolean>{

	private InputStream requestEmptyForm(){
		String url = Functions.urlConcat(Vars.wsServer,Vars.wsCustomersPath+"/?ws_key="+Vars.wsKey+"&schema=blank");
		Response resp = new Response(url);
		InputStream in = resp.getResponse();
		return in;
	}
	
	private Document setUserInfo(User user, InputStream emptyForm) throws ParserConfigurationException, SAXException, IOException{
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
	
	private String getXMLString(Document doc) throws TransformerException{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		
		StreamResult resu =  new StreamResult(new StringWriter());
		transformer.transform(source, resu);
		String xmlString = resu.getWriter().toString();
		
		return xmlString;
	}
	@Override
	protected Boolean doInBackground(User... params) {
		User user = params[0];
		
		InputStream emptyForm = requestEmptyForm();
		
		Document doc;
		try {
			doc = setUserInfo(user, emptyForm);
			String xmlString = getXMLString(doc);
			BasicHttpResponse response = makePostRequest(xmlString);
			System.out.println("customfrom requester SEnd XML : "+response.getStatusLine()+"  "+
					EntityUtils.toString(response.getEntity()));
			if(!response.getStatusLine().toString().equals("HTTP/1.1 201 Created")){
				return false;
			}
					
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private BasicHttpResponse makePostRequest(String xmlString) throws ClientProtocolException, IOException {
		String urlSend = Functions.urlConcat(Vars.wsServer,Vars.wsCustomersPath+"/?ws_key="+Vars.wsKey+"&xml=");
		
		String encode = URLEncoder.encode(xmlString, "UTF-8");
		String out = urlSend.concat(encode);
		
		HttpPost post = new HttpPost(out);
		post.setEntity(new StringEntity(xmlString));
		
		DefaultHttpClient client = new DefaultHttpClient();
		BasicHttpResponse response = (BasicHttpResponse) client.execute(post);
		
		return  response;
	}

}
