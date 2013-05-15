package dataBase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBConn2 extends SQLiteOpenHelper {

	private static String DB_PATH;
	private static String DB_NAME = "lampDB";
	private SQLiteDatabase myDataBase;
    private final Context myContext;

	/**
	 * O construtor necessita do contexto da aplicação
	 */
	public DBConn2(Context context) {
		/*
		 * O primeiro argumento é o contexto da aplicacao O segundo argumento é
		 * o nome do banco de dados O terceiro é um pondeiro para manipulação de
		 * dados, não precisaremos dele. O quarto é a versão do banco de dados
		 */
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		DB_PATH = context.getDatabasePath(DB_NAME).getPath();
		Log.d("DBConn2", "DB PATH: "+DB_PATH);
	}

	 /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
    	
    	boolean dbExist = checkDataBase();
    	Log.d("DBConn2", "CreatingDB, existis? "+dbExist);
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	
        	try {
        		Log.d("DBConn2", "Trying to copry database from assests");
    			copyDataBase();
    		} catch (IOException e) {
    			System.out.println("ddd "+e.getMessage());
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	//Open the database
        String myPath = DB_PATH;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("DBConn2", "Migration Tables");
		db.execSQL(TableScripts.CREATE_TABLE_USER);
		db.execSQL(TableScripts.CREATE_TABLE_PRODUCT);
		db.execSQL(TableScripts.CREATE_HISTORY_TABLE);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}