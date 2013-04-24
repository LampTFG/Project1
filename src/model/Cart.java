package model;

import java.util.ArrayList;

public class Cart {

	private int idUser;
	private ArrayList<ShopItem> cart;
	
	public Cart(){
		cart = new ArrayList<ShopItem>();
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public ArrayList<ShopItem> getCart() {
		return cart;
	}
	public void setCart(ArrayList<ShopItem> cart) {
		this.cart = cart;
	}
	public void addItem(int idProd, int qtd, float price){
		cart.add(new ShopItem(idProd, qtd, price));
	}
	public void addItem(ShopItem si){
		cart.add(si);
	}
	public void editItem(int idProd, int qtd){
		for (ShopItem si : cart) {
			if(si.getIdProd()==idProd){
				si.setQtd(qtd);
				return;
			}
		}
	}
	
	public void cleanCart(){
		cart.clear();
	}
	
}
