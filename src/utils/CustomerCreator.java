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

public class CustomerCreator extends AsyncTask<User, Void, Boolean>{
	
	@Override
	protected Boolean doInBackground(User... params) {
		User user = params[0];
	
		try {
			String xmlString = CustomerFormBuilder.build(user);
			BasicHttpResponse response = Response.makePostRequest(Vars.wsCustomersPath,xmlString);
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
}
