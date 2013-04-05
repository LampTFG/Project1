package controller;

import java.util.ArrayList;
import java.util.List;

import dataBase.DBConn;

import model.User;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CtrUser {
	String TABLE_NAME = "users";
	String FIELD_LOGIN = "login";
	String FIELD_PASS = "pass";
	
	public List<User> getAllUsers(Context contex) {
		List<User> result = new ArrayList<User>();
		DBConn conn = new DBConn(contex);
		SQLiteDatabase db = conn.getDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_LOGIN, FIELD_PASS }, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			result.add(new User(cursor.getString(0),cursor.getString(1)));
			cursor.moveToNext();
		}
		cursor.close();
		conn.close();
		return result;
	}
}