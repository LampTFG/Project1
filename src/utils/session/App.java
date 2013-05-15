package utils.session;

import model.Cart;
import model.History;
import model.User;

import android.app.Application;

public class App extends Application {

	private static User logedUser;
	private static Cart cart = new Cart();
	private static History history = new History();

	public static Cart getCart() {
		return cart;
	}

	public static void setCart(Cart cart) {
		App.cart = cart;
	}
	
	public static boolean isLoged(){
		return logedUser!=null;
	}

	public static void setUser(User u) {
		App.logedUser = u;		
	}
	
	public static User getUser() {
		return App.logedUser;	
	}

	public static History getHistory() {
		return history;
	}

	public static void setHistory(History history) {
		App.history = history;
	}
	
}