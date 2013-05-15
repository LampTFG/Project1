package model;

import java.util.ArrayList;

import android.util.Log;

import utils.Vars;

public class Cart {

	private ArrayList<ShopItem> cart = new ArrayList<ShopItem>();
	
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
		Log.d("Cart Model", "Adding SI to cart: "+si.getIdProd());
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
	
	public float getTotalPrice(){
		float total = 0;
		for (int i=0;i<cart.size();i++)
			total += cart.get(i).getPrice()*cart.get(i).getQtd();
		return total + Vars.extraFee;
	}
	
	@Override
	public String toString() {
		String resp = "";
		resp += "id   qtd   price\n";
		for (int i=0;i<cart.size();i++)
			resp += cart.get(i).getIdProd() +"    "+ cart.get(i).getQtd()+"      "+cart.get(i).getPrice()+"\n";
		resp += "\n\nExtra fee: "+Vars.extraFee+"\n";
		return resp;
	}	
}
