package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLOpenner {

	public String OpenURL(String address){
		StringBuilder stringBuilder = new StringBuilder();
		try{  
			URL url = new URL(address);  
	        URLConnection uc = url.openConnection();  
	        InputStream content = (InputStream) uc.getInputStream();  
	        BufferedReader in = new BufferedReader(new InputStreamReader(content));  
	        String line;  
	        while ((line = in.readLine()) != null) {  
	            stringBuilder.append(line).append("\n");  
	        }  
	        in.close();
	        System.out.println(stringBuilder.toString());
		}catch (Exception e) {
			System.out.println("URL Error: "+e);
		}
        return stringBuilder.toString();
	}
}
