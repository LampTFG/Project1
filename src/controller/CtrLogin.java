package controller;

import java.util.concurrent.ExecutionException;

import utils.CustomerRequester;
import utils.Functions;
import model.User;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import dataBase.DBConn2;

public class CtrLogin {
	
	public boolean validaUser(Context context, User u){
		try {
			User regUser = new CustomerRequester().execute(u.getLogin()).get();
			if(regUser.getPass().equals( Functions.md5(u.getPass()))){
				Log.d("CtrLogin", "user is valid!");
				return true;
			}
			else
				return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
