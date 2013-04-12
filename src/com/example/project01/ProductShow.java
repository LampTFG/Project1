package com.example.project01;

import utils.ProductRequester;
import model.Product;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ProductShow extends Activity {
	Product product;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_show);
		
		String prodID = getIntent().getStringExtra("product_id");
		System.out.println("prod id "+prodID);
		ProductRequester pr = new ProductRequester(Integer.parseInt(prodID));
		product = pr.getProduct(); 
		
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

}
