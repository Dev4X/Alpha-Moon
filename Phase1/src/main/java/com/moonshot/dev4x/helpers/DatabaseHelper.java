package com.moonshot.dev4x.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moonshot.dev4x.models.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;//Initial database version
    private static final String DATABASE_NAME = "dev4xDb"; //Name of database
    String DEV4X_NODES_TABLE = "dev4x_nodes";
    String NODE_NAME = "name";
    String NODE_ICON = "icon";

    private Context context;

    //Constructor function to create or connecte to database.
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setContext(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables.

        // Open the resource
        String uri = "@raw/nodedb";
        int rawResourceId = context.getResources().getIdentifier(uri, null, context.getPackageName());
        InputStream insertsStream = context.getResources().openRawResource(rawResourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        // Load the sql into a String
        String fullSql = null;
        try {
            StringBuilder sb = new StringBuilder("");
            while (insertReader.ready()) {
                String sqlLine = insertReader.readLine();
                sb.append(sqlLine);
            }
            insertReader.close();
            fullSql = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // execute the sql one statement at a time
        int line = 0;
        try {
            String[] sqlStatements = fullSql.split(";");
            for (String sqlStatement : sqlStatements) {
                db.execSQL(sqlStatement);
                line++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errored at line " + line);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    //Function to get all the skill nodes
    public List<Node> getAllNodes() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DEV4X_NODES_TABLE;
        List<Node> nodeList = new ArrayList<Node>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Node node = new Node(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4)
                );
                nodeList.add(node);
            } while (cursor.moveToNext());
        }

        return nodeList;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
