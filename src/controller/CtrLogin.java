package controller;

import model.User;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dataBase.DBConn2;

public class CtrLogin {
	public boolean validaUser(Context context, User u){
		DBConn2 conn = new DBConn2(context);
		String[] allColumns = { "username", "password"};
		SQLiteDatabase db = conn.getDatabase();
		Cursor cursor = db.query("User", allColumns, "username = '"+u.getLogin()+"' and password='"+u.getPass()+"'", null, null, null, null);
		boolean answer = false;
    	if (cursor.moveToFirst())
    		answer = true;
    	cursor.close();
    	conn.Close(db);
    	return answer;
    }
}
