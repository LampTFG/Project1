package com.example.project01;

import utils.DialogManager;
import utils.Functions;
import utils.Response;
import utils.Vars;
import utils.Views;
import utils.XMLParser;
import utils.session.App;
import zxingHelpers.IntentIntegrator;
import zxingHelpers.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Welcome extends Activity implements Runnable {
	String respText;
	String prodID;

	@Override
	protected synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaboasvindas);

		try {
			TextView greeting = (TextView) findViewById(R.id.greeting);
			if (App.getUsername() != null) 
				greeting.setText("Welcome " + " " + App.getUsername());

			Thread t = new Thread(this);
			t.start();
			wait();
			String webText = "erro na leitura";
			webText = respText;
			TextView text = (TextView) findViewById(R.id.textURL);
			text.setText(webText);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void backMainScreen(View v) {
		Functions.logOut();
		Intent i = new Intent(Views.loginIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}

	@Override
	public synchronized void run() {
		Response res = new Response(
				Functions.urlConcat("http://" + Vars.wsServer,
						Vars.wsProductPath + "/3?ws_key=" + Vars.wsKey));
		String response = res.getResponse();
		XMLParser xml = new XMLParser(response, "name");
		xml.parse();
		respText = xml.getResp();
		notifyAll();
	}

	public void checkHistory(View v) {
		Intent i = new Intent(Views.historyIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}

	public void checkCart(View v) {
		Intent i = new Intent(Views.shoppingCartIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
	}

	public void scanQRCode(View v) {
		/*
		 * Intent i = new Intent(Views.scanIntent);
		 * Welcome.this.startActivity(i); Welcome.this.finish();
		 */
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null && scanResult.getContents() != null) {
			String barcord = scanResult.getContents();
			String type = scanResult.getFormatName();
			System.out.println("Type: " + type);
			prodID = barcord;
			System.out.println("Value: " + prodID);
			if(Functions.isCodeValid(prodID)){
				System.out.println("Codigo Valido: "+Functions.productDecrypt(prodID));
				//redirects to Product Screen
				//please, implement it
			}else{
				DialogManager.showErrorMessage(this, "Erro", "QR Invalid");
			}
		} else {
			Intent i = new Intent(Views.welcomeIntent);
			Welcome.this.startActivity(i);
			Welcome.this.finish();
		}
	}
}