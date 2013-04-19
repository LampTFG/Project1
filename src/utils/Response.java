package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.util.Log;

public class Response {

	String get_url, response;
	Activity activity;

	public Response(String url) {
		this.get_url = url;

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