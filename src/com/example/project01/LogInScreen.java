package com.example.project01;


import utils.DialogManager;
import utils.Views; 
import utils.session.App;
import model.User;
import controller.CtrLogin;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
		User u = new User(edtUser, edtPass);
		CtrLogin ctr = new CtrLogin();
		Intent i = new Intent(Views.welcomeIntent);
		if(ctr.validaUser(getApplicationContext(), u)){
			App.setUsername(edtUser);
		}
		if(App.isLoged()){
			LogInScreen.this.startActivity(i);
			LogInScreen.this.finish();
		}else{
			DialogManager.showErrorMessage(this, "Erro", "Wrong username or password");
		}
    }
}
