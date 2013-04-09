package utils.session;

import com.example.project01.ShoppingCart;

import android.app.Application;

public class App extends Application {

	private static String username;
	private static String password;
	private static ShoppingCart cart;

	@Override
	public void onCreate() {
		super.onCreate();
		username = "";
		password = "";
		cart = new ShoppingCart();
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

	public static ShoppingCart getCart() {
		return cart;
	}

	public static void setCart(ShoppingCart cart) {
		App.cart = cart;
	}
	
	public static boolean isLoged(){
		return (username!=null && !username.equals(""));
	}
}