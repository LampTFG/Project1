package com.example.project01;

import java.util.concurrent.ExecutionException;

import utils.DialogManager;
import utils.ProductRequester;
import utils.session.App;
import utils.urlimageviewhelper.MyAdapter;
import utils.urlimageviewhelper.MyGridAdapter;
import model.Product;
import model.ShopItem;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ProductShow extends Activity {
	Product product;
	private MyAdapter mAdapter;
	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_show);
		
		String prodID = getIntent().getStringExtra("product_id");
		setProduct(prodID);
	}

	private void setProduct(String prodID){
		try {
			product = new ProductRequester().execute(prodID).get();
			setProductInfos();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
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
		
		//product Image
		mListView = (ListView)findViewById(R.id.productImage);
        mAdapter = new MyAdapter(this);
        MyGridAdapter a = new MyGridAdapter(mAdapter, this);
        mListView.setAdapter(a);
        mAdapter.add(product.getImagePath());
        System.out.println("Image: "+product.getImagePath());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_product_show, menu);
		return true;
	}
	
	public void onClick(View v){
		
		ShopItem si = new ShopItem(product.getId(),1, 1);
		
		App.getCart().addItem(si);
		
		DialogManager.showToastMessage(this, product.getName()+" adicionado ao carrinho.");
		
		ProductShow.this.finish();
	}

}
