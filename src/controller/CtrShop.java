package controller;

import model.Cart;
import utils.session.App;

public class CtrShop {
	public void finalizeShop(){
		addHistory(App.getCart());
		App.getCart().cleanCart();
	}

	private void addHistory(Cart cart) {
		
	}

}
