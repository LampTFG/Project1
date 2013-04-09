package dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConn extends SQLiteOpenHelper {

	public final static int DATABASE_VERSION = 1;
	public final static String DATABASE_NAME = "ProjectDemo1";

	public DBConn(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	public void populateDataBase(SQLiteDatabase db) {
		String TABLE_NAME = "users";
		String TABLE_DROP = "DROP TABLE IF EXISTS "+TABLE_NAME;
		String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (login varchar(100) not null primary key, pass varchar(100) not null);";
		String FIELD_LOGIN = "login";
		String FIELD_PASS = "pass";
		
		
		db.execSQL(TABLE_DROP);
		db.execSQL(TABLE_CREATE);
		ContentValues values = new ContentValues();
		//felipe
		values.put(FIELD_LOGIN, "felipe");
		values.put(FIELD_PASS, "felipe");
		db.insert(TABLE_NAME, null, values);
		//thiago
		values.put(FIELD_LOGIN, "thiago");
		values.put(FIELD_PASS, "thiago");
		db.insert(TABLE_NAME, null, values);
		//t
		values.put(FIELD_LOGIN, "t");
		values.put(FIELD_PASS, "");
		db.insert(TABLE_NAME, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	public SQLiteDatabase getDatabase() {
		return this.getReadableDatabase();
	}
	
	public void Close(SQLiteDatabase db){
		db.close();
		super.close();
	}
}