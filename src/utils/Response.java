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

	public String getResponse() {
		InputStream in = null;
		byte[] data = new byte[1000];
		try {
			URL url = new URL(get_url);
			URLConnection conn = url.openConnection();
			conn.connect();
			/* conn. */
			in = conn.getInputStream();
			Log.d("Buffer Size +++++++++++++", "" + in.toString().length());
			BufferedReader rd = new BufferedReader(new InputStreamReader(in),
					in.toString().length());
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			response = sb.toString();

			in.read(data);
			Log.d("INPUT STREAM PROFILE RESPONSE", response);
			in.close();
		} catch (IOException e1) {
			Log.d("CONNECTION  ERROR", "+++++++++++++++++++++++++++");
			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
		return response;
	}
}