package com.mgr.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	    public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

		private int oldSchemaVersion = -1;
	    private int schemaUpgradedVersion = -1;
	    private static DBHelper dbHelper = null;

	   
	/*    private DbHelper(Context context)
	    {
	        super(context, DocStoreDbSchema.DB_NAME, null, DocStoreDbSchema.DB_VERSION);
	    }*/
	    
	    private DBHelper(Context context)
	    {
	    	super(context , DbSchema.DB_NAME, null , DbSchema.DB_VERSION);
	    }

	    public static synchronized final DBHelper getDbHelperInstance(Context context)
	    {
	        if (dbHelper == null) {
	            dbHelper = new DBHelper(context);
	        }

	        return dbHelper;
	    }

	    @Override
	    public void onOpen(SQLiteDatabase db)
	    {
	        super.onOpen(db);
	        if (!db.isReadOnly()) {
	            // Enable foreign key constraints
	            db.execSQL("PRAGMA foreign_keys=ON;");
	        }
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db)
	    {
	        try {
	            db.beginTransaction();

	            for (int i = 1; i <= DbSchema.DB_VERSION; i++) {
	                switch (i)
	                {

	                case 1:
	                    /*
	                     * enable foreign key cascade constraints
	                     */
	                    if (db.isOpen()) {
	                        db.execSQL(DbSchema.CREATE_IMAGE_HASHING_TABLE);
	                        
	                    }
	                    break;
	               
	                }
	            }
	            db.setTransactionSuccessful();

	        }
	        catch (Exception e) {
	            Log.e("DocStoreDbHelper", ""+e);
	        }
	        finally {
	            db.endTransaction();
	        }

	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	    {
	        
	    }

	    public int getUpgradedSchemaVersion()
	    {
	        return schemaUpgradedVersion;
	    }

	    public int getOldSchemaVersion()
	    {
	        return oldSchemaVersion;
	    }

	
}
