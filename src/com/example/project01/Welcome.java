package com.example.project01;

import utils.DialogManager; 
import utils.Functions;
import utils.Views;
import utils.session.App;
import zxingHelpers.IntentIntegrator;
import zxingHelpers.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Welcome extends Activity  {
	String respText;
	String barcord;

	@Override
	protected synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaboasvindas);

		TextView greeting = (TextView) findViewById(R.id.greeting);
		if (App.getUsername() != null) 
			greeting.setText("Welcome " + " " + App.getUsername());

		String webText = "erro na leitura";
		webText = respText;
		TextView text = (TextView) findViewById(R.id.textURL);
		text.setText(webText);
	}

	public void backMainScreen(View v) {
		Functions.logOut();
		Intent i = new Intent(Views.loginIntent);
		Welcome.this.startActivity(i);
		Welcome.this.finish();
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
			barcord = scanResult.getContents();
			System.out.println("Welcome - Value: " + barcord);
			if(Functions.isCodeValid(barcord)){
				System.out.println("Welcome -Codigo Valido: "+Functions.productDecrypt(barcord));
				int prodID = Functions.productDecrypt(barcord);
				Intent i = new Intent(Views.productShowIntent);
				i.putExtra("Welcome -product_id", String.valueOf(prodID));
				startActivity(i);
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