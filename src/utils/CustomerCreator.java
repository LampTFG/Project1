package utils;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.User;

import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
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
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return true;
	}
}
