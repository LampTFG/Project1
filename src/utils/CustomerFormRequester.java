package utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.User;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.ByteArrayBuffer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

public class CustomerFormRequester extends AsyncTask<User, Void, String>{

	@Override
	protected String doInBackground(User... params) {
		/*Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsCustomersPath + "/?ws_key="+ Vars.wsKey+"&"+filters+"&"+display));
				*/
		User user = params[0];
		String url = Functions.urlConcat(Vars.wsServer,Vars.wsCustomersPath+"/?ws_key="+Vars.wsKey+"&schema=blank");
		Response resp = new Response(url);
		InputStream in = resp.getResponse();
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(in);
			Document doc2 = docBuilder.newDocument();
			
			Element presta = doc2.createElement("prestashop");
			doc2.appendChild(presta);
			Element customers = doc2.createElement("customers");
			presta.appendChild(customers);
			/*Element customer1 = doc2.createElement("customer");
			customers.appendChild(customer1);
			
			Element email = doc2.createElement("email");
			email.appendChild(doc2.createTextNode(user.getLogin()));
			customer1.appendChild(email);
			
			Element passwd = doc2.createElement("passwd");
			passwd.appendChild(doc2.createTextNode(Functions.md5(user.getPass())));
			customer1.appendChild(passwd);
			
			Element first = doc2.createElement("firstname");
			first.appendChild(doc2.createTextNode(user.getFirstname()));
			customer1.appendChild(first);
			
			Element last = doc2.createElement("lastname");
			last.appendChild(doc2.createTextNode(user.getLastname()));
			customer1.appendChild(last);
			doc2.normalize();*/
			doc.normalize();
			System.out.println("CustomerFormRequester: "+doc.getFirstChild().getNodeName());
			
			Node prestashop = doc.getFirstChild();
			
			
			Node customer = doc.getElementsByTagName("customer").item(0);
			//doc2.getDocumentElement().appendChild(cus);
			
			doc2.normalize();
			
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
			}
			customers.appendChild(doc2.adoptNode(customer));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			DOMSource source2 = new DOMSource(doc2);
			StreamResult resu2 =  new StreamResult(new StringWriter());
			transformer.transform(source2, resu2);
			String xmlString2 = resu2.getWriter().toString();
			
			System.out.println("customfrom requester: "+xmlString2);
			
			StreamResult resu =  new StreamResult(new StringWriter());
			transformer.transform(source, resu);
			String xmlString = resu.getWriter().toString();
			
			//System.out.println("customfrom requester: "+xmlString);
			
			String urlSend = Functions.urlConcat(Vars.wsServer,Vars.wsCustomersPath+"/?ws_key="+Vars.wsKey+"&xml=");
			InputStream input = new ByteArrayInputStream(xmlString2.getBytes("UTF-8"));
			
			String encode = URLEncoder.encode(xmlString2, "UTF-8");
			String out = urlSend.concat(encode);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(out);
			//post.setEntity(new StringEntity(xmlString));
			
			BasicHttpResponse response = (BasicHttpResponse) client.execute(post);
			
			System.out.println("customfrom requester SEnd XML : "+response.getStatusLine().toString());
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
