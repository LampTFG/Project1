package com.example.project01;

import java.util.ArrayList;

import model.User;
import utils.Response;
import utils.Vars;
import utils.Views;
import utils.XMLParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Welcome extends Activity implements Runnable{
	ProgressDialog progressDialog ;
	ArrayList<User> itemList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaboasvindas);
		
		//message
		Intent i = getIntent();
		TextView resp = (TextView) findViewById(R.id.txtResp);
		if(i.getExtras().getBoolean("confirmation"))
			resp.setText("PARABENSSSSSSSSSSS");
		else
			resp.setText("ERROUUUUUUUUUU");
		

		progressDialog = ProgressDialog.show(this, "WebService","Por favor, Aguarde...",true,false);
		Thread t =new Thread(this);
		t.start();
	}
	
	public void backMainScreen(View v){
		Intent i = new Intent(Views.loginIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}

	@Override
	public void run() {
		Response res = new Response(Vars.wsServer+"/"+Vars.wsCustomersPath+"/3?ws_key="+Vars.wsKey);
        String response = res.getResponse();
        XMLParser xml = new XMLParser(response);
		itemList = xml.getItemList();
		xml.parse();
		
		String webText = "erro na leitura" ; 
		if(itemList.size()>0)
			webText = itemList.get(0).getLogin();
		TextView text = (TextView) findViewById(R.id.textURL);
		text.setText(webText);
		
		progressDialog.dismiss();
	}
}
