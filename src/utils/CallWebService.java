package utils;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class CallWebService {
	private static final String ip = "http://192.168.1.108";
	private static final String URL = ip+"/store/";
	private static final String OPERATION = "GET";
	private static final String NAMESPACE = ip+"/ws/index.php";

	public static String productData(String cep) {
		String ur = URL+"/api/customers/1";
		String resp = "";
		HttpPost httppost = new HttpPost(ur);
		httppost.setHeader("Content-Type","application/soap+xml;charset=UTF-8");
		//httppost.setEntity(se); 
		HttpClient httpclient = new DefaultHttpClient();
		try {
			BasicHttpResponse httpResponse = (BasicHttpResponse) httpclient.execute(httppost);
			resp = "httpresponse: "+ httpResponse.getStatusLine().toString();
		}catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
		return resp;
	}
}