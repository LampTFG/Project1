package controller;

import android.content.Context;
import dataBase.DBConn;

public class CtrStartUp {
	DBConn conn ;
	public CtrStartUp(Context context) {
		conn = new DBConn(context);
	}
	public void close(){
		conn.Close(conn.getDatabase());
	}
}
