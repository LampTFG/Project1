package com.example.project01;

import java.util.ArrayList;

import model.Cart;
import model.ShopItem;

import utils.Views;
import utils.session.App;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ShoppingCart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_cart);
		populateTable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_cart, menu);
		populateTable();
		return true;
	}
	
	private void populateTable() {
		TableLayout tl = (TableLayout) findViewById(R.id.tableShoppingCart);
        TableRow tr;
        TextView tv;
        
        Cart cart = App.getCart();
        
        ArrayList<ShopItem> list = cart.getCart();
        //ArrayList<ShopItem> list = new ArrayList<ShopItem>();
        //list.add(new ShopItem(7, 3));
        //list.add(new ShopItem(2, 6));
        for (int i=0;i< list.size(); i++) {
        	ShopItem si = list.get(i);
        	tr = new TableRow(this);
        	tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	//
        	tv = new TextView(this);
        	tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        	tv.setText(Integer.toString(i+1));
        	tr.addView(tv);
        	tv = new TextView(this);
        	tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        	tv.setText(Integer.toString(si.getIdProd()));
            tr.addView(tv);
            tv = new TextView(this);
        	tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        	tv.setText(Integer.toString(si.getQtd()));
            tr.addView(tv);
            //
            tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
	}
	

	public void back(View v){
		Intent i = new Intent(Views.welcomeIntent);
		ShoppingCart.this.startActivity(i);
		ShoppingCart.this.finish();
	}

}
