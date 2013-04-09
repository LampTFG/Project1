package utils;

import com.example.project01.ShoppingCart;

import utils.session.App;

public class Functions {

	public static String urlConcat(String ... strings ){
		String resp = "";
		for(int i=0;i<strings.length-1;i++){
			resp += strings[i] + ((strings[i].endsWith("/"))?"":"/");
		}
		resp += strings[strings.length-1];
		return resp;
	}

	public static void logOut() {
		App.setPassword("");
		App.setUsername("");
		App.setCart(new ShoppingCart());
	}
}
