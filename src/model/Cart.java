package model;

import java.util.HashMap;

public class Cart {

	private int idUser;
	private HashMap<Integer, Integer> cart = new HashMap<Integer, Integer>();
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public HashMap<Integer, Integer> getCart() {
		return cart;
	}
	public void setCart(HashMap<Integer, Integer> cart) {
		this.cart = cart;
	}
	
	public void addItem(int idProd, int qtd){
		cart.put(idProd, qtd);
	}
	public void editItem(int idProd, int qtd){
		cart.remove(idProd);
		cart.put(idProd, qtd);
	}
	public void cleanCart(){
		cart.clear();
	}
	
}
