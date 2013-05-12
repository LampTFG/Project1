package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import model.Cart;

import utils.session.App;

public class Functions {

	/**
	 * Concatenates the string list, adding a "/" between then in case it's not
	 * present.
	 * 
	 * @param strings
	 *            list of strings
	 * @return concatenated URL
	 */
	public static String urlConcat(String... strings) {
		String resp = "";
		for (int i = 0; i < strings.length - 1; i++) {
			resp += strings[i] + ((strings[i].endsWith("/")) ? "" : "/");
		}
		resp += strings[strings.length - 1];
		return resp;
	}

	/**
	 * Logout... cleans the user parameters and reset the shopping cart
	 * 
	 * @return
	 */
	public static void logOut() {
		App.setUser(null);
		App.setCart(new Cart());
	}

	/**
	 * Encrypt productID
	 * 
	 * @param int product ID
	 * @return Encrypted Product CODE
	 */
	public static String productEncrypt(int productId) {
		return "Lamp_" + productId;
	}

	/**
	 * Checks if the QRCode contains a valid product code
	 * 
	 * @param strings
	 *            QR value
	 * @return true - valid <br/>
	 *         false - invalid
	 */
	public static boolean isCodeValid(String code) {
		boolean resp = true;
		resp = code.startsWith("Lamp_");
		try {
			Integer.parseInt(code.replaceFirst("Lamp_", ""));
		} catch (Exception e) {
			resp = false;
		}
		return resp;
	}

	/**
	 * Dencrypt productID
	 * 
	 * @param String
	 *            product CODE
	 * @return ProductId
	 */
	public static int productDecrypt(String code) {
		return Integer.parseInt(code.replace("Lamp_", ""));
	}

	public static String getProductsUrl(int prodID) {
		return urlConcat(Vars.wsServer, Vars.wsProductPath + "/" + prodID
				+ "?ws_key=" + Vars.wsKey);
	}

	/**
	 * Encrypt string into MD5 hash
	 * 
	 * @param String
	 *            customer_passowrd pass
	 * @return md5 string
	 */
	public static String md5(String in) {
		MessageDigest digest;
		in = Vars.encryptKey + in;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Boolean function who says if the user is connect to Internet, by 3G or Wifi
	 * 
	 * @param Contex
	 *            current activity
	 * @return true or false / connected or not
	 */
	public static boolean isConnected(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			System.out.println("Connection? "+ni.isConnected());
			if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
				Log.d("Connection",
						"Status de conexão 3G: "
								+ cm.getNetworkInfo(
										ConnectivityManager.TYPE_MOBILE)
										.isConnected());
				return true;
			} else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.isConnected()) {
				Log.d("Connection",
						"Status de conexão Wifi: "
								+ cm.getNetworkInfo(
										ConnectivityManager.TYPE_WIFI)
										.isConnected());
				return true;
			} else {
				Log.e("Connection",
						"Status de conexão Wifi: "
								+ cm.getNetworkInfo(
										ConnectivityManager.TYPE_WIFI)
										.isConnected());
				Log.e("Connection",
						"Status de conexão 3G: "
								+ cm.getNetworkInfo(
										ConnectivityManager.TYPE_MOBILE)
										.isConnected());
				return false;
			}
		} catch (Exception e) {
			Log.e("Connection", e.getMessage());
			return false;
		}
	}

}