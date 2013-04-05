package controller;

import model.User;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dataBase.DBConn;

public class CtrLogin {
	public boolean validaUser(Context context, User u){
		DBConn conn = new DBConn(context);
		String[] allColumns = { "login", "pass"};
		SQLiteDatabase db = conn.getDatabase();
		Cursor cursor = db.query("users", allColumns, "login = '"+u.getLogin()+"' and pass='"+u.getPass()+"'", null, null, null, null);
		boolean answer = false;
    	if (cursor.moveToFirst())
    		answer = true;
    	cursor.close();
    	conn.Close(db);
    	return answer;
    }
}
