package controller;

import utils.session.App;
import model.History;
import model.ShopItem;
import model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import dataBase.DBConn2;

public class CtrHistory {
	static public void loadLocalHistory(User user,Context context){
		DBConn2 conn = new DBConn2(context);
	    SQLiteDatabase db = conn.getReadableDatabase();
	    //History history = null;
	    Log.d("CtrHistory laodlocalHistory", "reading stuff");
	    Cursor cursor = db.query("History", new String[] { "_id", "idProd", "dateShop"}, "idUser = ?",
	            new String[] { user.getId()}, null, null, null, null);
	    if (cursor != null){
	    	cursor.moveToFirst();
		    if(cursor.getCount()>0){
		    	do{
			    	int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
			    	int idProd = cursor.getInt(cursor.getColumnIndexOrThrow("idProd"));
			    	//long total  = cursor.getLong(cursor.getColumnIndexOrThrow("total"));
			    	App.getHistory().addShopItem(new ShopItem(idProd,1,Float.parseFloat("12.5")));
			    			
		    	}while(cursor.moveToNext());
		    	cursor.close();
		    }
	    }
	    db.close();
	}
	
	public static void addHistoryItem(Context context){
		Log.d("CtrHistory additem", "adding row to history");
		DBConn2 conn = new DBConn2(context);
	    SQLiteDatabase db = conn.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put("idProd", 1);
	    values.put("idUser", 2);
	    //values.put("total", 12.5);
	    values.put("dateShop", "13/05/2013");
	    
	    long id = db.insert("History", null, values);
	    
	    db.close();
	}
}
