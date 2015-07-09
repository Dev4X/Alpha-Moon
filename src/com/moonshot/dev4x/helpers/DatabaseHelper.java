package com.moonshot.dev4x.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;//Initial database version
	private static final String DATABASE_NAME = "dev4xDb"; //Name of database
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//Create tables.
		String metaDataSql = "CREATE TABLE dev4x_metadata (id INTEGER PRIMARY KEY, name TEXT)";
		db.execSQL(metaDataSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	//Constructor function to create or connecte to database.
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
}
