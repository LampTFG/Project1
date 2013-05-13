package com.example.project01;

import java.math.BigDecimal;

import utils.DialogManager;
import utils.Views;
import utils.session.App;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

import controller.CtrShop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Payment extends Activity {
	PayPal pp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		initLibrary();
		addButton();
		addProductList();
		addTotalPrice();
	}

	private void addProductList() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayoutList);
		TextView tv = new TextView(this);
		String message = "IdUser: "+(App.getUser()!=null?App.getUser().getId():"User test")+"  \n"+ App.getCart().toString();
		tv.setText(message);
		ll.addView(tv);
	}

	private void initLibrary() {
		pp = PayPal.getInstance();
		if (pp == null) { // Test to see if the library is already initialized
			pp = PayPal.initWithAppID(this, "APP-80W284485P519543T",PayPal.ENV_NONE);
			pp.setLanguage("pt_BR");
			pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
			pp.setShippingEnabled(true);
		}
	}

	private void addButton() {
		CheckoutButton launchPayPalButton = pp.getCheckoutButton(this, PayPal.BUTTON_278x43, CheckoutButton.TEXT_PAY);
		launchPayPalButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				confirmPayment(new BigDecimal(App.getCart().getTotalPrice()));
			}
		}); 
		LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayoutPage); 
		ll.addView(launchPayPalButton);
	}
	
	private void addTotalPrice(){
		TextView tv = (TextView) findViewById(R.id.textViewTotal);
		tv.setText("Total: "+String.valueOf(App.getCart().getTotalPrice()));
	}

	private void confirmPayment(BigDecimal value) {
		PayPalPayment newPayment = new PayPalPayment();
		newPayment.setSubtotal(value);
		newPayment.setCurrencyType("USD");
		newPayment.setRecipient("sjk.thiago@gmail.com");
		newPayment.setMerchantName("Lamp");
		Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this.getBaseContext());
		this.startActivityForResult(paypalIntent, 1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
			DialogManager.showToastMessage(this,"Success! Your paykey is: "+payKey);
			new CtrShop().finalizeShop();
			break;
		case Activity.RESULT_CANCELED:
			DialogManager.showToastMessage(this,"Payment canceled");
			break;
		case PayPalActivity.RESULT_FAILURE:
			String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
			String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
			DialogManager.showToastMessage(this,"Erro "+errorID+" was found: "+errorMessage);
		}
		
		Intent i = new Intent(Views.welcomeIntent);
		Payment.this.startActivity(i);
		Payment.this.finish();
		
	}

}