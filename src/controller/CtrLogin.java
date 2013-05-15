package controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import dataBase.DBConn2;

import model.User;
import utils.CustomerRequester;
import utils.Functions;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*
 * User enter email and pass
 * CustomerRequester makes an call searching for customer_by_email
 * XMLParser2 parses the response to find the customer id
 * CustomerRequester call for customer_by_id
 * XMLParser2 parses all user information needed to build a user
 * CtrlLogin check if the entered password is equal the requested user.
 */

public class CtrLogin {
	
	public User getUser(Context context, String username, String pass){
		User regUser = null;
		try {
			if(Functions.isConnected(context)){
				//checking in the remote base
				regUser = new CustomerRequester().execute(username, pass).get();
				if(regUser != null)
				{
					int _id = getUserId(regUser.getLogin(), context);
					if(_id==-1)
						addUserLocalBase(context, regUser);
					else
						updateUserLocalBase(_id, regUser, context);
				}else{
					//checking in the local base because the user wasn't found in the remote base
					regUser = getLocalUser(username, pass, context);
				}
			}else{
				//checking in the local base because the device isn't online
				regUser = getLocalUser(username, pass, context);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return regUser;
	}

	private void addUserLocalBase(Context context, User u) {
		SQLiteDatabase db2 =null;
		try {
			DBConn2 conn = new DBConn2(context);
			db2 = conn.getWritableDatabase() ;
			ContentValues initialValues = new ContentValues();
			initialValues.put("username", u.getLogin());
			initialValues.put("password", u.getPass());
			db2.insert("user", null, initialValues);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db2.close();
		}
	}
	
	public int getUserId(String username, Context context){
		DBConn2 conn = new DBConn2(context);
	    SQLiteDatabase db = conn.getReadableDatabase();
	    int id = -1;
	 
	    Cursor cursor = db.query("User", new String[] { "_id"}, "username = ?",
	            new String[] { username }, null, null, null, null);
	    if (cursor != null){
	    	cursor.moveToFirst();
		    if(cursor.getCount()>0)
		    	id = cursor.getInt(0);
	    }
	    cursor.close();
	    db.close();
	    return id;
	}
	
	public User getLocalUser(String username, String password, Context context){
		DBConn2 conn = new DBConn2(context);
	    SQLiteDatabase db = conn.getReadableDatabase();
	    User u = null;
	 
	    Cursor cursor = db.query("user", new String[] { "_id"}, "username = ? and password = ?",
	            new String[] { username , password }, null, null, null, null);
	    if (cursor != null){
	    	cursor.moveToFirst();
		    if(cursor.getCount()>0){
		    	int id =cursor.getShort(cursor.getColumnIndexOrThrow("_id"));
		    	u = new User(username,password, String.valueOf(id));
		    }
		    cursor.close();
	    }
	    db.close();
	    return u;
	}
	
	public boolean updateUserLocalBase(int _id, User u, Context context)
	{
		DBConn2 conn = new DBConn2(context);
		SQLiteDatabase db2 =null;
		boolean resp = false ;
		try{
			db2 = conn.getWritableDatabase() ;
			ContentValues args = new ContentValues();
			args.put("password", u.getPass());
			resp = db2.update("user", args, "_id=" + _id, null) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db2.close();
		}
		return resp;
	}
}