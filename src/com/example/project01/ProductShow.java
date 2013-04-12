package com.example.project01;

import utils.ProductRequester;
import utils.session.App;
import model.Product;
import model.ShopItem;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProductShow extends Activity {
	Product product;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_show);
		
		String prodID = getIntent().getStringExtra("product_id");
		System.out.println("prod id "+prodID);
		new ProductRequester().execute(prodID);
		//product = pr.getProduct(); 
		
		setProductInfos();
		
		
	}

	public void setProductInfos(){
		//Need to fix layout so all infos can fit correctly
		//Need to find a better XML parser.
		TextView nameTv = (TextView) findViewById(R.id.name_tv);
		nameTv.setText(product.getName());
		
		TextView shortDescTv = (TextView) findViewById(R.id.short_description_tv);
		shortDescTv.setText(product.getShortDesc());
		
		TextView priceTv = (TextView) findViewById(R.id.price_tv);
		priceTv.setText(String.valueOf(product.getPrice()));
		
		TextView longDescTv = (TextView) findViewById(R.id.long_description_tv);
		//longDescTv.setText(product.getLongDesc());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_product_show, menu);
		return true;
	}
	
	public void onClick(View v){
		EditText qtyed = (EditText) findViewById(R.id.product_quantity_ed);
		Log.d("ProductShow", qtyed.getText().toString());
		System.out.println("ProductSow "+qtyed.getText().toString());
		ShopItem si = new ShopItem(product.getId(), Integer.parseInt(qtyed.getText().toString()));
		
		System.out.println("Product Show" + si.getIdProd() + App.getCart());
		App.getCart().addItem(si);
		Toast toast = Toast.makeText(this, product.getName()+" adicionado ao carrinho.",Toast.LENGTH_SHORT);
		toast.show();
	}

}
