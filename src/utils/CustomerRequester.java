package utils;

import java.io.InputStream;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

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
		Serializer serializer = new Persister();
		user = serializer.read(User.class, i);
		Log.d("Customer Requester",": requested customer: "+user.toString());
	}

	private void find(String email, String passwd) throws Exception{
		findUser(email, passwd);
	}
	
}
