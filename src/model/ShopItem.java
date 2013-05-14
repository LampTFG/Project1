package model;

import java.util.Date;


public class ShopItem {
	private int idProd;
	private int qtd;
	private float price;
	private  Date dateShop;
	
	public ShopItem(int idProd, int qtd, float price) {
		super();
		this.idProd = idProd;
		this.qtd = qtd;
		this.price = price;
	}
	public ShopItem(int idProd, int qtd, float price, Date date) {
		super();
		this.idProd = idProd;
		this.qtd = qtd;
		this.price = price;
		this.dateShop = date;
	}


	public Date getDateShop() {
		return dateShop;
	}


	public void setDateShop() {
		this.dateShop = new Date();
	}
	
	public void setDateShop(Date date) {
		this.dateShop = date;
	}

	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getIdProd() {
		return idProd;
	}
	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
}
