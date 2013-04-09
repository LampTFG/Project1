package utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

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
}
