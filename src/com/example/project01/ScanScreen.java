package com.example.project01;

import utils.Functions;
import utils.Response;
import utils.Vars;
import utils.Views;
import utils.XMLParser;
import zxingHelpers.IntentIntegrator;
import zxingHelpers.IntentResult;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ScanScreen extends Activity implements Runnable{
	
	ProgressDialog progressDialog ;
	String respText;
	String prodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_screen);
    }

    public void onClick(View v){
    	IntentIntegrator integrator = new IntentIntegrator(this);
    	integrator.initiateScan();
    	
    }
    public void onClickEncode(View v){
    	IntentIntegrator integrator = new IntentIntegrator(this);
    	EditText encT = (EditText) findViewById(R.id.encodeEdit);
    	integrator.shareText(encT.getText().toString());
    	
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  if (scanResult != null) {
    		  String barcord = scanResult.getContents();
    		  String typ = scanResult.getFormatName();
	    	  EditText ebarcode = (EditText) findViewById(R.id.barCodeEdit);
	    	  EditText etyp = (EditText) findViewById(R.id.typEdit);
	    	  ebarcode.setText(barcord);
	    	  etyp.setText(typ);
	    	  prodID = barcord.substring(4);
	    	  try {
	    		  Thread t = new Thread();
		    	  t.start();
		    	  wait();
	    	  } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
    	  }
    	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scan_screen, menu);
        return true;
    }

	@Override
	public void run() {
		Response res = new Response(Functions.urlConcat(Vars.wsServer,Vars.wsProductPath,prodID+"?ws_key="+Vars.wsKey));
        String response = res.getResponse();
        XMLParser xml = new XMLParser(response, "name");
        xml.parse();
		respText = xml.getResp();
		notifyAll();
	}
	
	public void back(View v){
		Intent i = new Intent(Views.welcomeIntent);
		ScanScreen.this.startActivity(i);
		ScanScreen.this.finish();
	}
    
}