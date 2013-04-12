package dataBase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConn2 extends SQLiteOpenHelper {

	public final static int DATABASE_VERSION = 1;
	public final static String DATABASE_NAME = "lampDB";

	// /////////
	/**
	 * Este é o endereço onde o android salva os bancos de dados criado pela
	 * aplicação, /data/data/<namespace da aplicacao>/databases/
	 */
	private static String DBPATH;

	private Context context;

	/**
	 * O construtor necessita do contexto da aplicação
	 */
	public DBConn2(Context context) {
		/*
		 * O primeiro argumento é o contexto da aplicacao O segundo argumento é
		 * o nome do banco de dados O terceiro é um pondeiro para manipulação de
		 * dados, não precisaremos dele. O quarto é a versão do banco de dados
		 */
		super(context, DATABASE_NAME, null, 1);
		this.context = context;
		DBPATH = context.getDatabasePath(DATABASE_NAME).getPath();
	}

	/**
	 * Os métodos onCreate e onUpgrade precisam ser sobreescrito
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

	public void Close(SQLiteDatabase db) {
		db.close();
		super.close();
	}

	/**
	 * Método auxiliar que verifica a existencia do banco da aplicação.
	 */
	private boolean checkDataBase() {
		SQLiteDatabase db = null;
		try {
			String path = DBPATH;
			db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
			db.close();
		} catch (SQLiteException e) {
			System.err.println("ERRO: "+e.getMessage());
			// Database not found
		}
		return db != null;
	}

	private void createDataBase() throws Exception {

		// Primeiro temos que verificar se o banco da aplicação
		// já foi criado
		boolean exists = checkDataBase();

		if (!exists) {
			// Chamaremos esse método para que o android
			// crie um banco vazio e o diretório onde iremos copiar
			// no banco que está no assets.
			this.getWritableDatabase();

			// Se o banco de dados não existir iremos copiar o nosso
			// arquivo em /assets para o local onde o android os salva
			try {
				copyDatabase();
			} catch (IOException e) {
				throw new Error("Não foi possível copiar o arquivo");
			}

		}
	}

	/**
	 * Esse método é responsável por copiar o banco do diretório assets para o
	 * diretório padrão do android.
	 */
	private void copyDatabase() throws IOException {

		String dbPath = DBPATH;

		// Abre o arquivo o destino para copiar o banco de dados
		OutputStream dbStream = new FileOutputStream(dbPath);

		// Abre Stream do nosso arquivo que esta no assets
		InputStream dbInputStream = context.getAssets().open("lampDB");

		byte[] buffer = new byte[1024];
		int length;
		while ((length = dbInputStream.read(buffer)) > 0) {
			dbStream.write(buffer, 0, length);
		}

		dbInputStream.close();

		dbStream.flush();
		dbStream.close();
	}

	public SQLiteDatabase getDatabase() {
		try {
			// Verificando se o banco já foi criado e, se não foi, o mesmo será criado.
			createDataBase();

			// Abrindo database
			String path = DBPATH;

			return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		} catch (Exception e) {
			// Se não conseguir copiar o banco um novo será retornado
			System.err.println("Exception"+e.getMessage());
			return getWritableDatabase();
		}

	}
}