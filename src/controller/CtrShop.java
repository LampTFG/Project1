package controller;

import java.util.ArrayList;

import android.content.Context;

import com.example.project01.Welcome;

import model.Cart;
import model.ShopItem;
import utils.session.App;

public class CtrShop {
	public void finalizeShop(Context context){
		closeCart();
		addHistory(context);
		App.getCart().cleanCart();
	}
	
	private void closeCart(){
		ArrayList<ShopItem> items = App.getCart().getCart();
		for(int i=0; i<items.size();i++){
			items.get(i).setDateShop();
		}
	}
	
	private void addHistory(Context context) {
		ArrayList<ShopItem> items = App.getCart().getCart();
		CtrHistory.addHistoryItems(items, context);
	}

}
