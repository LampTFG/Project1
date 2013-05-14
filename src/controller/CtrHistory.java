package controller;

import java.util.ArrayList;
import java.util.Date;

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
	    Cursor cursor = db.query("History", new String[] { "_id", "idProd", "price","dateShop"}, "idUser = ?",
	            new String[] { user.getId()}, null, null, null, null);
	    if (cursor != null){
	    	cursor.moveToFirst();
		    if(cursor.getCount()>0){
		    	do{
			    	int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
			    	int idProd = cursor.getInt(cursor.getColumnIndexOrThrow("idProd"));
			    	float price  = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
			    	Date date = new Date(cursor.getShort(cursor.getColumnIndexOrThrow("dateShop")));
			    	App.getHistory().addShopItem(new ShopItem(idProd,1,price,date));
			    			
		    	}while(cursor.moveToNext());
		    
		    }
	    }
	    cursor.close();
	    db.close();
	}
	
	public static void addHistoryItems(ArrayList<ShopItem> items, Context context){
		Log.d("CtrHistory additems", "Adding a list to history");
		for(int i=0;i<items.size();i++){
			addHistoryItem(items.get(i), context);
		}
	}
	
	public static void addHistoryItem(ShopItem item,Context context){
		Log.d("CtrHistory additem", "adding row to history");
		App.getHistory().addShopItem(item);
		DBConn2 conn = new DBConn2(context);
	    SQLiteDatabase db = conn.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put("idProd", 1);
	    values.put("idUser", 2);
	    //values.put("total", 12.5);
	    values.put("dateShop", "13/05/2013");
	    
	    db.insert("History", null, values);
	    
	    db.close();
	}
}
