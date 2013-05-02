package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;

import android.app.Activity;
import android.util.Log;

public class Response {

	String get_url, response;
	Activity activity;

	public Response(String url) {
		this.get_url = url;

	}
	
	public static BasicHttpResponse makePostRequest(String wsPath,String xmlString) throws ClientProtocolException, IOException {
		String urlSend = Functions.urlConcat(Vars.wsServer,wsPath+"/?ws_key="+Vars.wsKey+"&xml=");
		
		String encode = URLEncoder.encode(xmlString, "UTF-8");
		String out = urlSend.concat(encode);
		
		HttpPost post = new HttpPost(out);
		post.setEntity(new StringEntity(xmlString));
		
		DefaultHttpClient client = new DefaultHttpClient();
		BasicHttpResponse response = (BasicHttpResponse) client.execute(post);
		
		return  response;
	}

	public String toString(InputStream in){
		BufferedReader rd = new BufferedReader(new InputStreamReader(in),
				in.toString().length());
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
		} catch (IOException e) {
			Log.d("CONNECTION  ERROR" + "toString()", "+++++++++++++IOException++++++++++++++");
			e.printStackTrace();
		}
		response = sb.toString();
		return response;
	}
	public InputStream getResponse() {
		InputStream in = null;
		try {
			URL url = new URL(get_url);
			System.out.println("Tentando acessar: "+get_url);
			URLConnection conn = url.openConnection();
			conn.connect();
			/* conn. */
			in = conn.getInputStream();

		} catch (IOException e1) {
			Log.d("CONNECTION  ERROR", "+++++++++++++IOException++++++++++++++");
			e1.printStackTrace();
		}
		return in;
	}
}