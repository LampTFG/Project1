package dataBase;

import android.provider.BaseColumns;

public abstract class TableScripts implements BaseColumns{
	
	public static String CREATE_TABLE_USER = "create table User ( _id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT);" ;
	public static String CREATE_TABLE_PRODUCT = "create table Product (  _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, price NUMERIC);";
	public static String CREATE_HISTORY_TABLE = "create table History ( _id INTEGER PRIMARY KEY AUTOINCREMENT, idUser INTEGER, idProd INTEGER, price NUMERIC, dateShop TEXT );";
}
