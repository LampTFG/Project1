package utils;

import android.os.AsyncTask;
import model.User;

public class CustomerRequester extends AsyncTask<String, Void, User>{
	User user;
	
	@Override
	protected User doInBackground(String... params) {
		find(params[0]);
		return user;
	}
	
	private int findId(String email){
		System.out.println("Customer Requester: email"+email);
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/1"));
		String response = res.getResponse();
		System.out.println("Customer Requester: "+response);
		return 0;
	}
	
	private void findUser(int id){
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsCustomersPath + "/"+id+"?ws_key="+ Vars.wsKey));
		String response = res.getResponse();
		
		user = parseAttributes(response);
		
	}

	private void find(String email){
		int id = findId(email);
		findUser(id);
	}
	
	private User parseAttributes(String response){
		User user = new User();
		
		XMLParser xml = new XMLParser(response, "email");
		xml.parse();
		user.setLogin(xml.getResp());
		
		xml = new XMLParser(response, "password");
		xml.parse();
		user.setPass(xml.getResp());

		return user;
	}
	
	
	
}
