package com.example.project01;

import java.util.ArrayList;

import model.Cart;
import model.ShopItem;
import utils.FunctionsView;
import utils.Views;
import utils.session.App;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class HistoryScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_screen);
		populateTable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history_screen, menu);
		return true;
	}
	
	private void populateTable() {
		TableLayout tl = (TableLayout) findViewById(R.id.tableHistory);
        TableRow tr;
        TextView tv;
        LayoutParams tableParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        
        //
        tr = new TableRow(this);
    	tv = FunctionsView.makeTableViewHeader(this,getString(R.string.headerName));
    	tr.addView(tv);
    	tv = FunctionsView.makeTableViewHeader(this,getString(R.string.headerPrice));
        tr.addView(tv);
        tv = FunctionsView.makeTableViewHeader(this,getString(R.string.headerDate));
    	tr.addView(tv);
        tl.addView(tr,tableParams);
        //
        
        tr = new TableRow(this);
    	tr.setLayoutParams(tableParams);
        
        ArrayList<ShopItem> list = getHistory();
        //descomente as linhas abaixo para teste
        //list = new ArrayList<ShopItem>();
        //list.add(new ShopItem(7, 3));
        //list.add(new ShopItem(2, 6));
        //
        
        if(list!=null){
	        for (int i=0;i< list.size(); i++) {
	        	tr = new TableRow(this);
	        	tr.setLayoutParams(tableParams);
	        	//
	        	tv = FunctionsView.makeTableView(this,"aaaa",i);
	        	tr.addView(tv);
	        	tv = FunctionsView.makeTableView(this,"bbbb",i);
	        	tr.addView(tv);
	        	tv = FunctionsView.makeTableView(this,"xx/xx/xx",i);
	            tr.addView(tv);
	            //
	            tl.addView(tr,tableParams);
	        }
        }else{
        	tr = FunctionsView.getEmptyRow(this); 
        	tl.addView(tr, tableParams);
        }
	}
	
	private ArrayList<ShopItem> getHistory() {
		return new ArrayList<ShopItem>();
	}

	public void back(View v){
		Intent i = new Intent(Views.welcomeIntent);
		HistoryScreen.this.startActivity(i);
		HistoryScreen.this.finish();
	}

}
