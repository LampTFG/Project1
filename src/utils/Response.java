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
		try {
			URL url = new URL(get_url);
			URLConnection conn = url.openConnection();
			conn.connect();
			/* conn. */
			in = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in),
					in.toString().length());
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			response = sb.toString();

		} catch (IOException e1) {
			Log.d("CONNECTION  ERROR", "+++++++++++++++++++++++++++");
			e1.printStackTrace();
		}
		return response;
	}
}