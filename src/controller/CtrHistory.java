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

	    Log.d("CtrHistory laodlocalHistory", "reading stuff Cart sieze"+ App.getCart().getCart().size());
	    Cursor cursor = db.query("History", new String[] { "_id", "idProd", "price","dateShop"}, "idUser = ?",
	            new String[] { user.getId()}, null, null, null, null);
	    if (cursor != null){
	    	cursor.moveToFirst();
		    if(cursor.getCount()>0){
		    	Log.d("Ctr History", "cursorCount: "+cursor.getCount());
		    	do{
			    	int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
			    	int idProd = cursor.getInt(cursor.getColumnIndexOrThrow("idProd"));
			    	float price  = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
			    	Date date = new Date(cursor.getShort(cursor.getColumnIndexOrThrow("dateShop")));
			    	Log.d("Ctr History", "reading from db _id:" +id+" idProd"+idProd);
			    	App.getHistory().addShopItem(new ShopItem(idProd,1,price,date));
			    			
		    	}while(cursor.moveToNext());
		    
		    }
	    }
	    cursor.close();
	    db.close();
	}
	
	public static void addHistoryItems(ArrayList<ShopItem> items, Context context){
		Log.d("CtrHistory additems", "Adding a list to history, size: "+items.size());
		for(int i=0;i<items.size();i++){
			addHistoryItem(items.get(i), context);
		}
		App.getHistory().clear();
	}
	
	public static void addHistoryItem(ShopItem item,Context context){
		Log.d("CtrHistory additem", "adding row to history: "+item.getIdProd());
		App.getHistory().addShopItem(item);
		DBConn2 conn = new DBConn2(context);
	    SQLiteDatabase db = conn.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put("idProd", item.getIdProd());
	    values.put("idUser", App.getUser().getId());
	    values.put("price", item.getPrice());
	    values.put("dateShop", item.getDateShop().toString());
	    
	    db.insert("History", null, values);
	    
	    db.close();
	}
}
