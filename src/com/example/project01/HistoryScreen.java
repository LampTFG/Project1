package com.example.project01;

import java.util.ArrayList;

import model.History;
import model.ShopItem;
import utils.FunctionsView;
import utils.Views;
import utils.session.App;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
        
    	History history = App.getHistory();
    	ArrayList<ShopItem> list = history.getShopItems();
    	Log.d("Hisotry Screen", "adding row to table: "+list.size());
        if(list!=null && list.size()>0){
	        for (int i=0;i< list.size(); i++) {
	        	Log.d("Hisotry Screen", "adding row to table"+ list.get(i).getIdProd());
	        	tr = new TableRow(this);
	        	tr.setLayoutParams(tableParams);
	        	//
	        	tv = FunctionsView.makeTableView(this,String.valueOf(list.get(i).getIdProd()),i);
	        	tr.addView(tv);
	        	tv = FunctionsView.makeTableView(this,String.valueOf(list.get(i).getPrice()),i);
	        	tr.addView(tv);
	        	tv = FunctionsView.makeTableView(this,String.valueOf(list.get(i).getDateShop()),i);
	            tr.addView(tv);
	            //
	            tl.addView(tr,tableParams);
	        }
        }else{
        	tr = FunctionsView.getEmptyRow(this); 
        	tl.addView(tr, tableParams);
        }
	}

	public void back(View v){
		Intent i = new Intent(Views.welcomeIntent);
		HistoryScreen.this.startActivity(i);
		HistoryScreen.this.finish();
	}

}
