package com.example.project01;

import java.util.ArrayList;

import model.User;
import utils.CallWebService;
import utils.Response;
import utils.URLOpenner;
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
		int cont=0;
		while (itemList == null && cont<100){
			try {
				synchronized (this) {
					  this.wait(100);
					}
				cont++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public void backMainScreen(View v){
		Intent i = new Intent("com.example.project01.LogInScreen");
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}

	@Override
	public void run() {
		Response res = new Response("http://192.168.1.107/store/api/customers/3?ws_key=4OZ72NAAOCD3ZYWYCOENYIBGU3HU3F39");
        String response = res.getResponse();
        XMLParser xml = new XMLParser(response);
		itemList = xml.getItemList();
		xml.parse();
		//
		String webText = "erro na leitura" ; 
		if(itemList.size()>0)
			webText = itemList.get(0).getLogin();
		TextView text = (TextView) findViewById(R.id.textURL);
		text.setText(webText);
		
		/*
		URLOpenner u = new URLOpenner();
        webText = u.OpenURL("http://192.168.1.107/store/api/customers/1?ws_key=4OZ72NAAOCD3ZYWYCOENYIBGU3HU3F39");
        */
		
		progressDialog.dismiss();
	}
}
