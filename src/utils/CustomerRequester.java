package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import model.User;

public class CustomerRequester extends AsyncTask<String, Void, User>{
	User user;
	
	@Override
	protected User doInBackground(String... params) {
		find(params[0],params[1]);
		return user;
	}
	
	private void findUser(String email, String passwd){
		//Make a Functions.makeFilter for simplefying
		String filters = "filter[email]="+email+"&filter[passwd]="+passwd;
		//Make a Functions.makeDisplay for simplefying
		String display = "display=[id,firstname,lastname,email,passwd]";
		
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsCustomersPath + "/?ws_key="+ Vars.wsKey+"&"+filters+"&"+display));
		//String response = res.getResponse().toString();
		InputStream i = res.getResponse();
		XMLParser2 parser = new XMLParser2();
		try {
			List entries = parser.parse(i, XMLParser2.GET_CUSTOMER_BY_ID);
			user = (User) entries.get(0);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void find(String email, String passwd){
		findUser(email, passwd);
		System.out.println("Customer Requester: findUser id "+ user.getId()+ "Pass: "+user.getPass());
	}
	
}
