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

/*
 * User enter email and pass
 * CustomerRequester makes an call searching for customer_by_email
 * XMLParser2 parses the response to find the customer id
 * CustomerRequester call for customer_by_id
 * XMLParser2 parses all user information needed to build a user
 * CtrlLogin check if the entered password is equal the requested user.
 */

public class CtrLogin {
	
	public boolean validaUser(Context context, User u){
		try {
			//It request an user by its email/login
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
