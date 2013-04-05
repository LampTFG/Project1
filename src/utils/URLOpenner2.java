package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;



public class URLOpenner2 {

	public String Open(String endereco){
		  
 
		StringBuilder stringBuilder = new StringBuilder();  
	     try {  
	         URL url = new URL(endereco);  
	         URLConnection uc = url.openConnection();  
	         InputStream content = (InputStream) uc.getInputStream();  
	         BufferedReader in =  
	                 new BufferedReader(new InputStreamReader(content));  
	         String line;  
	         while ((line = in.readLine()) != null) {  
	             stringBuilder.append(line).append("\n");  
	         }  
	  
	  
	         in.close();  
	     } catch (Exception e) {  
	         System.out.println(e.getMessage());  
	     }  
	     return stringBuilder.toString();
	}
}
