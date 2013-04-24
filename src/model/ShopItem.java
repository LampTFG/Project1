package model;

public class ShopItem {
	private int idProd;
	private int qtd;
	private float price;
	
	public ShopItem(int idProd, int qtd, float price) {
		super();
		this.idProd = idProd;
		this.qtd = qtd;
		this.price = price;
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
