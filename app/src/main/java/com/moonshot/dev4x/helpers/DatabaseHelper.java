package com.moonshot.dev4x.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.moonshot.dev4x.models.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;//Initial database version
    private static final String DATABASE_NAME = "dev4xDb"; //Name of database
    String DEV4X_NODES_TABLE = "dev4x_nodes";
    String ID = "id";
    String NODE_NAME = "name";
    String NODE_ICON = "icon";
    String VIEW_COUNT = "view_count";
    String NODE_CONTENT = "content";
    String COUNT = "count";

    String DEV4X_CONTENT_CONSUMPTIONS = "dev4x_content_consumptions";
    String CONSUMPTION_ID = "cid";//Kind of session id for each video play
    String EVENT = "event";//Can be start, pause(stop), resume, complete, error
    String TIME = "time";//Time of event

    String DEV4X_ASSESSMENTS = "dev4x_assessments";
    String AID = "aid";
    String INCORRECT_SELECTIONS = "incorrect_selections";
    String IS_COMPLETED = "is_completed";
    String START_TIME = "start_time";
    String END_TIME = "end_time";

    private Context context;

    //Constructor function to create or connecte to database.
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + DEV4X_NODES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DEV4X_CONTENT_CONSUMPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + DEV4X_ASSESSMENTS);
        this.onCreate(db);
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
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
                nodeList.add(node);
            } while (cursor.moveToNext());
        }
        return nodeList;
    }

    public Node getNode(int nodeId) {
        String selectQuery = "SELECT  * FROM " + DEV4X_NODES_TABLE + " WHERE id = " + nodeId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Node node = new Node(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
                return node;
            } while (cursor.moveToNext());
        }
        return null;
    }

    public void increaseViewCountofContent(int nodeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DEV4X_NODES_TABLE + " WHERE " + ID + " = " + nodeId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int currentCount = cursor.getInt(3);
            currentCount++;
            ContentValues values = new ContentValues();
            values.put(VIEW_COUNT, currentCount);
            db.update(DEV4X_NODES_TABLE, values, ID + " = ?",
                    new String[]{String.valueOf(nodeId)});
            Log.v("VideoCount", "VideoCount " + currentCount);
        }
    }

    public void createVideoConsumptionSessionEvent(int nodeId, String event) {
        Log.v("VideoStatus", "VideoStatus-" + event);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, nodeId);
        values.put(EVENT, event);
        values.put(TIME, System.currentTimeMillis());
        db.insert(DEV4X_CONTENT_CONSUMPTIONS, null, values);
    }

    public long startAssessment(int contentId, long startTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, contentId);
        values.put(START_TIME, startTime);
        values.put(IS_COMPLETED, false);
        long aid = db.insert(DEV4X_ASSESSMENTS, null, values);
        return aid;
    }

    public void endAssessment(long aid, long endTime, int incorrectAnswers){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(END_TIME, endTime);
        values.put(INCORRECT_SELECTIONS, incorrectAnswers);
        values.put(IS_COMPLETED, true);
        db.update(DEV4X_ASSESSMENTS, values, AID + " = ?",
                new String[]{String.valueOf(aid)});
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
