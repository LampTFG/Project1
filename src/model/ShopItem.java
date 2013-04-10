package model;

public class ShopItem {
	private int idProd;
	private int qtd;
	
	public ShopItem(int idProd, int qtd) {
		super();
		this.idProd = idProd;
		this.qtd = qtd;
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
