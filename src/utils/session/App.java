package utils.session;

import model.Cart;

import android.app.Application;

public class App extends Application {

	private static String username;
	private static String password;
	private static Cart cart;

	@Override
	public void onCreate() {
		super.onCreate();
		username = "";
		password = "";
		cart = new Cart();
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		App.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		App.password = password;
	}

	public static Cart getCart() {
		return cart;
	}

	public static void setCart(Cart cart) {
		App.cart = cart;
	}
	
	public static boolean isLoged(){
		return (username!=null && !username.equals(""));
	}
}