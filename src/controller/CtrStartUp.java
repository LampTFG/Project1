package controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import dataBase.DBConn;

public class CtrStartUp {
	DBConn conn ;
	public CtrStartUp(Context context) {
		conn = new DBConn(context);
	}
	public void populate(){
		conn.populateDataBase(conn.getDatabase());
	}
	public void close(){
		conn.Close(conn.getDatabase());
	}
}
