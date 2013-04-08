package com.example.project01;

import utils.Views;
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
    	String edtUser = ((EditText) findViewById(R.id.edtUser)).getText().toString();
		String edtPass = ((EditText) findViewById(R.id.edtPass)).getText().toString();
		User u = new User(edtUser, edtPass);
		CtrLogin ctr = new CtrLogin();
		Intent i = new Intent(Views.welcomeIntent);
		if(ctr.validaUser(getApplicationContext(), u)){
			i.putExtra("confirmation", true);
			i.putExtra("userName", edtUser);
		}else{
			i.putExtra("confirmation", false);
		}
		LogInScreen.this.startActivity(i);
		LogInScreen.this.finish();
    }
}
