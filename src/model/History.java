package model;

import java.sql.Date;
import java.util.ArrayList;

import android.util.Log;

public class History{
	private ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();
	private int idUser;
	
	
	public ArrayList<ShopItem> getShopItems() {
		return shopItems;
	}
	public void setShopItems(ArrayList<ShopItem> shopItems) {
		this.shopItems = shopItems;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
		
	public void addShopItem(ShopItem shopItem){
		Log.d("History Model", "add item: "+shopItem.getIdProd());
		this.shopItems.add(shopItem);
	}
	
	public void clear(){
		shopItems.clear();
	}
	
}
