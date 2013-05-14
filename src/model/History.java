package model;

import java.sql.Date;
import java.util.ArrayList;

public class History{
	private ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();
	private int idUser;
	private Date dateShop;
	
	
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
	public Date getDateShop() {
		return dateShop;
	}
	public void setDateShop(Date dateShop) {
		this.dateShop = dateShop;
	}
	
	public void addShopItem(ShopItem shopItem){
		this.shopItems.add(shopItem);
	}
	
}
