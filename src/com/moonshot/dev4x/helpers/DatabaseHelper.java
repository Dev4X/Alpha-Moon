package com.moonshot.dev4x.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.moonshot.dev4x.models.*;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;//Initial database version
	private static final String DATABASE_NAME = "dev4xDb"; //Name of database
	String DEV4X_NODES_TABLE = "dev4x_nodes";
	String ID = "dev4x_nodes";
	String NODE_NAME = "name";
	String NODE_ICON = "icon";
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//Create tables.
		
		String metaDataSql = "CREATE TABLE "+DEV4X_NODES_TABLE+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+NODE_NAME+" TEXT, "+NODE_ICON+" TEXT)";
		db.execSQL(metaDataSql);
		
		//Inserting dummy nodes values.
		
		ContentValues values = new ContentValues();
		values.put(NODE_NAME, "VerbelSkills");
		values.put(NODE_ICON, "abc120");
		db.insert(DEV4X_NODES_TABLE, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	//Constructor function to create or connecte to database.
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//Function to get all the skill nodes
	public List<Node> getAllNodes(){
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DEV4X_NODES_TABLE;
		List<Node> nodeList = new ArrayList<Node>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Node node = new Node(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
				nodeList.add(node);
			} while (cursor.moveToNext());
		}
		
		return nodeList;
		
	}
}
