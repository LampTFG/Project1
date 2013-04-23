package com.example.project01;

import java.util.concurrent.ExecutionException;

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
		setProduct(prodID);
	}

	private void setProduct(String prodID){
		try {
			product = new ProductRequester().execute(prodID).get();
			Log.d("Product Show", product.toString());
			setProductInfos();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setProductInfos(){
		//Need to fix layout so all infos can fit correctly
		//Need to find a better XML parser.
		TextView nameTv = (TextView) findViewById(R.id.name_tv);
		nameTv.setText(product.getName());
		
		TextView shortDescTv = (TextView) findViewById(R.id.short_description_tv);
		shortDescTv.setText(android.text.Html.fromHtml(product.getShortDesc()).toString());
		
		TextView priceTv = (TextView) findViewById(R.id.price_tv);
		priceTv.setText(String.valueOf(product.getPrice()));
		
		TextView longDescTv = (TextView) findViewById(R.id.long_description_tv);
		longDescTv.setText(android.text.Html.fromHtml(product.getLongDesc()));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_product_show, menu);
		return true;
	}
	
	public void onClick(View v){
		EditText qtyed = (EditText) findViewById(R.id.product_quantity_ed);
		
		ShopItem si = new ShopItem(product.getId(),
				Integer.parseInt(qtyed.getText().toString()), product.getPrice());
		
		App.getCart().addItem(si);
		
		Toast toast = Toast.makeText(this, product.getName()+" adicionado ao carrinho.",Toast.LENGTH_SHORT);
		toast.show();
		
		ProductShow.this.finish();
	}

}
