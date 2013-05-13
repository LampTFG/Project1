package utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class DialogManager {
	/**
	 * Error popUp message
	 *
	 * @param act			Activity Screen
	 * @param title			Error title
	 * @param message		Error message
	 */
	public static void showErrorMessage(Activity act, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		builder.setMessage(message)
				.setTitle(title)
				.setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   dialog.dismiss();
			           }
			       });
		builder.show();
	}
	
	public static void showToastMessage(Activity act, String message) {
		Toast toast = Toast.makeText(act, message, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * popUp for not online users
	 *
	 * @param act			Activity Screen
	 */
	public static void notOnlineUser(Activity act) {
		showErrorMessage(act, "Not Online", "You must be online to reach this page");
	}
}
