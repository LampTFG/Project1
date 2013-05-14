package com.example.project01;

import java.io.IOException;

import controller.CtrHistory;

import dataBase.DBConn2;
import utils.DialogManager; 
import utils.Functions;
import utils.Vars;
import utils.Views;
import utils.session.App;
import utils.urlimageviewhelper.MyAdapter;
import utils.urlimageviewhelper.MyGridAdapter;
import zxingHelpers.IntentIntegrator;
import zxingHelpers.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Welcome extends Activity  {
	String respText;
	String barcord;
	private MyAdapter mAdapter;
	private ListView mListView;

	@Override
	protected synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaboasvindas);
		//creating database
		try {
			DBConn2 conn = new DBConn2(this);
			conn.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//filling profile
		fillProfileInfo();
		//load History from local DB
		fillHistory();
	}
	
	private void fillHistory() {
		Log.d("Welcome: fillhistory", "user: "+App.getUser().toString());
		//This line to populate History
		//CtrHistory.addHistoryItem(this);
		CtrHistory.loadLocalHistory(App.getUser(), this);
	}

	@Override
	public void onResume(){
		super.onResume();
		populateGallery();
	}
	
	private void fillProfileInfo() {
		Log.d("Welcome: ", "filling profile info");
		TextView fullName = (TextView)findViewById(R.id.fullName);
		fullName.setText(App.getUser().getFirstname()+" "+App.getUser().getLastname());
		TextView email = (TextView)findViewById(R.id.emailUser);
		email.setText(App.getUser().getEmail());
		//profile Image
		mListView = (ListView)findViewById(R.id.results);
        mAdapter = new MyAdapter(this);
        MyGridAdapter a = new MyGridAdapter(mAdapter, this);
        mListView.setAdapter(a);
        mAdapter.add("https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-ash3/549445_455717007829988_666626422_n.jpg");
        //mAdapter.add(App.getUser().getImagePath());
	}

	private void populateGallery() {
		LinearLayout ll = (LinearLayout)findViewById(R.id.linearList);
		for(int i=0;i<5;i++){
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(new LayoutParams(135, 135));
			iv.setBackgroundResource(R.drawable.round_button);
			iv.setImageResource(R.drawable.lampada_quebrada);
			ll.addView(iv);
		}
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
	}
	
	public void editProfile(View v) {
		if(Functions.isConnected(this)){
			Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(Vars.editProfile));
			Welcome.this.startActivity(i);
		}else
			DialogManager.notOnlineUser(this);
	}

	public void checkCart(View v) {
		Intent i = new Intent(Views.shoppingCartIntent);
		Welcome.this.startActivity(i);
	}
	
	public void scanQRCode(View v) {
		if(Functions.isConnected(this)){
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
		}else
			DialogManager.notOnlineUser(this);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		
		if (scanResult != null && scanResult.getContents() != null) {
			barcord = scanResult.getContents();
			if(Functions.isCodeValid(barcord)){
				System.out.println("Welcome -Codigo Valido: "+Functions.productDecrypt(barcord));
				int prodID = Functions.productDecrypt(barcord);
				Intent i = new Intent(Views.productShowIntent);
				i.putExtra("product_id", String.valueOf(prodID));
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