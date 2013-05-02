package com.example.project01;


import java.util.ArrayList;

import utils.DialogManager; 
import utils.Functions;
import utils.Vars;
import utils.Views;
import utils.session.App;
import utils.urlimageviewhelper.UrlImageViewCallback;
import utils.urlimageviewhelper.UrlImageViewHelper;
import zxingHelpers.IntentIntegrator;
import zxingHelpers.IntentResult;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.ScaleAnimation;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
		fillProfileInfo();
	}
	
	private void fillProfileInfo() {
		TextView fullName = (TextView)findViewById(R.id.fullName);
		fullName.setText(App.getUser().getFirstname()+" "+App.getUser().getLastname());
		TextView email = (TextView)findViewById(R.id.emailUser);
		email.setText(App.getUser().getEmail());
		//profile Image
		mListView = (ListView)findViewById(R.id.results);
        mAdapter = new MyAdapter(this);
        MyGridAdapter a = new MyGridAdapter(mAdapter);
        mListView.setAdapter(a);
        mAdapter.add("https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-ash3/549445_455717007829988_666626422_n.jpg");
        //mAdapter.add(App.getUser().getImagePath());
	}

	public void onResume(){
		super.onResume();
		populateGallery();
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
		Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(Vars.editProfile));
		Welcome.this.startActivity(i);
	}

	public void checkCart(View v) {
		Intent i = new Intent(Views.shoppingCartIntent);
		Welcome.this.startActivity(i);
	}

	public void scanQRCode(View v) {
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
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

	
	//Profile Image
	private class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv;
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.image, null);

            iv = (ImageView)convertView.findViewById(R.id.image);
            
            iv.setAnimation(null);
            // yep, that's it. it handles the downloading and showing an interstitial image automagically.
            UrlImageViewHelper.setUrlDrawable(iv, getItem(position), R.drawable.transparent, new UrlImageViewCallback() {
                @Override
                public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                    if (!loadedFromCache) {
                        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                        scale.setDuration(140);
                        imageView.startAnimation(scale);
                    }
                }
            });

            return convertView;
        }
    }
	private class MyGridAdapter extends BaseAdapter {
        public MyGridAdapter(Adapter adapter) {
            mAdapter = adapter;
            mAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    notifyDataSetChanged();
                }
                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                    notifyDataSetInvalidated();
                }
            });
        }
        Adapter mAdapter;
        
        @Override
        public int getCount() {
            return (int)Math.ceil((double)mAdapter.getCount() / 4d);
        }

        @Override
        public Row getItem(int position) {
            Row row = new Row();
            for (int i = position * 1; i < 1; i++) {
                if (mAdapter.getCount() < i)
                    row.add(mAdapter.getItem(i));
                else
                    row.add(null);
            }
            return row;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.row, null);
            LinearLayout row = (LinearLayout)convertView;
            LinearLayout l = (LinearLayout)row.getChildAt(0);
            for (int child = 0; child < 1; child++) {
                int i = position * 1 + child;
                LinearLayout c = (LinearLayout)l.getChildAt(child);
                c.removeAllViews();
                if (i < mAdapter.getCount()) {
                    c.addView(mAdapter.getView(i, null, null));
                }
            }
            
            return convertView;
        }
        
    }
	private class Row extends ArrayList {
        
    }
	
}