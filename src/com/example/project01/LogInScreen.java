package com.example.project01;


import model.User;
import utils.DialogManager;
import utils.Functions;
import utils.Views;
import utils.session.App;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import controller.CtrLogin;

public class LogInScreen extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
	}
	
	//user validation
	public void checkLogin(View v){
		// Session Manager
        // Edits
    	String edtUser = ((EditText) findViewById(R.id.edtUser)).getText().toString();
		String edtPass = ((EditText) findViewById(R.id.edtPass)).getText().toString();
		User u = new User(edtUser, Functions.md5(edtPass));
		CtrLogin ctr = new CtrLogin();
		Intent i = new Intent(Views.welcomeIntent);
		User regUser = ctr.validateUser(getApplicationContext(), u); 
		if(regUser!=null){
			App.setUser(regUser);
		}
		if(App.isLoged()){
			LogInScreen.this.startActivity(i);
			LogInScreen.this.finish();
		}else{
			DialogManager.showErrorMessage(this, "Erro", "Wrong username or password");
		}
    }
	
	//Sign up listener
	public void sendToRegistration(View v){
		if(Functions.isConnected(this)){
			Intent i = new Intent(Views.customerRegistrationIntent);
			startActivity(i);
		}else
			DialogManager.notOnlineUser(this);
			
	}
}
