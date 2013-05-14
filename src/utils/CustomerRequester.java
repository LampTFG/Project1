package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;
import model.User;

public class CustomerRequester extends AsyncTask<String, Void, User>{
	User user;
	
	@Override
	protected User doInBackground(String... params) {
		try {
			find(params[0],params[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	private void findUser(String email, String passwd) throws Exception{
		//Make a Functions.makeFilter for simplefying
		String filters = "filter[email]="+email+"&filter[passwd]="+passwd;
		//Make a Functions.makeDisplay for simplefying
		String display = "display=[id,firstname,lastname,email,passwd,imagePath]";
		
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsCustomersPath + "/?ws_key="+ Vars.wsKey+"&"+filters+"&"+display));
		//String response = res.getResponse().toString();
		InputStream i = res.getResponse();
		XMLParser2 parser = new XMLParser2();
		try {
			Log.d("Customer Requester", "trying to parse");
			List<Object> entries = parser.parse(i, XMLParser2.GET_CUSTOMER_BY_ID);
			Log.d("Customer Requester", "parsed");
			//user = entries.size()>0 ? (User) entries.get(0):null;
			user = (User) entries.get(0);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void find(String email, String passwd) throws Exception{
		findUser(email, passwd);
	}
	
}
