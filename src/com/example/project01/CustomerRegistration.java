package com.example.project01;

import java.util.concurrent.ExecutionException;

import utils.CustomerFormRequester;
import utils.DialogManager;

import model.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CustomerRegistration extends Activity {
	
	private EditText emailtv;
	private EditText passwdtv;
	private EditText nametv;
	private EditText lastnametv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_registration);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_customer_registration, menu);
		return true;
	}
	
	public void RegisterUser(View v){
		emailtv = (EditText) findViewById(R.id.email_cutomer_registration);
		String email = emailtv.getText().toString();
		
		passwdtv = (EditText) findViewById(R.id.passwd_customer_registration);
		String passwd= passwdtv.getText().toString();
		
		nametv= (EditText) findViewById(R.id.first_name_customer_registration);
		String firstname = nametv.getText().toString();
		
		lastnametv = (EditText) findViewById(R.id.last_name_customer_registration);
		String lastname = lastnametv.getText().toString();
		
		User u = new User();
		u.setId("1");
		u.setLogin(email);
		u.setPass(passwd);
		u.setFirstname(firstname);
		u.setLastname(lastname);
		
		try {
			Boolean resp = new CustomerFormRequester().execute(u).get();
			if(resp){
				this.finish();
			}else{
				DialogManager.showErrorMessage(this, "Erro", "Cant create your account, please try again");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
