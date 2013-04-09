package com.example.project01;


import utils.Response;
import utils.Vars;
import utils.Views;
import utils.XMLParser;
import utils.session.App;
import utils.session.SessionManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Welcome extends Activity implements Runnable{
	ProgressDialog progressDialog ;
	String respText;
	@Override
	protected synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SessionManager session = new SessionManager(getApplicationContext());
		setContentView(R.layout.telaboasvindas);
		
		try {
			TextView resp = (TextView) findViewById(R.id.txtResp);
			TextView greeting = (TextView) findViewById(R.id.greeting);
			if(session.getUserDetails().get(SessionManager.KEY_USERNAME) != null){
				resp.setText("PARABENSSSSSSSSSSS");
				greeting.setText("Welcome "+ " "+session.getUserDetails().get(SessionManager.KEY_USERNAME)+" "+App.getUsername());
			}else
				resp.setText("ERROUUUUUUUUUU");

			progressDialog = ProgressDialog.show(this, "WebService","Por favor, Aguarde...",true,false);
			Thread t =new Thread(this);
			t.start();
			wait();
			String webText = "erro na leitura" ; 
			webText = respText;
			TextView text = (TextView) findViewById(R.id.textURL);
			text.setText(webText);
			progressDialog.dismiss();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void backMainScreen(View v){
		Intent i = new Intent(Views.loginIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}

	@Override
	public synchronized void run() {
		Response res = new Response("http://"+Vars.wsServer+"/"+Vars.wsProductPath+"/3?ws_key="+Vars.wsKey);
        String response = res.getResponse();
        XMLParser xml = new XMLParser(response, "name");
        xml.parse();
		respText = xml.getResp();
		notifyAll();
	}
	
	public void checkHistory(View v){
		Intent i = new Intent(Views.historyIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}
	
	public void checkCart(View v){
		Intent i = new Intent(Views.shoppingCartIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}
	
	public void scanQRCode(View v){
		Intent i = new Intent(Views.scanIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}
}