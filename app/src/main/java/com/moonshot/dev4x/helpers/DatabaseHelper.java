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

    private static final int DATABASE_VERSION = 1;//Initial database version
    private static final String DATABASE_NAME = "dev4xDb"; //Name of database
    ///////////////////Variables for Skillsets tables////////////////////////
    String DEV4X_SKILLS_TABLE = "dev4x_skillsets";
    String SKILL_ID = "id";
    String SKILL_NAME = "name";
    String ICON = "icon";
    /////////////////////////////////////////////////////////////////////////
    String DEV4X_NODES_TABLE = "dev4x_nodes";
    String ID = "id";
    String NODE_SKILL_ID = "skill_id";
    String NODE_CONTENT_ID = "content_id";

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


    ////////////////////////////////Variables for content tables////////////////
    String DEV4X_CONTENTS_TABLE = "dev4x_contents";
    ////////////////////////////////////////////////////////////////////////////
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
        db.execSQL("DROP TABLE IF EXISTS " + DEV4X_SKILLS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DEV4X_CONTENTS_TABLE);
        this.onCreate(db);
    }

    //function to get all skill sets

    public List<SkillSets> getAllSkillSets(){
        String selectQuery = "SELECT  * FROM " + DEV4X_SKILLS_TABLE;
        List<SkillSets> skillSetList = new ArrayList<SkillSets>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SkillSets skillSet = new SkillSets(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3))
                        );
                skillSetList.add(skillSet);
            } while (cursor.moveToNext());
        }
        return skillSetList;
    }

    //Function to get all the skill nodes
    public List<Node> getAllNodes(int skillId) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DEV4X_NODES_TABLE + " WHERE "+NODE_SKILL_ID + " = " + skillId;
        List<Node> nodeList = new ArrayList<Node>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Node node = new Node(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)));
                nodeList.add(node);
            } while (cursor.moveToNext());
        }
        return nodeList;
    }

    //Function to get last consumed node and content of the skill set
    public ContentConsumptions getLastContentConsumptionDetailsForSkillSet(int skillId){
        String selectQuery  = "SELECT * FROM " + DEV4X_CONTENT_CONSUMPTIONS + " WHERE " + NODE_SKILL_ID + " = " + skillId + " ORDER BY cid DESC LIMIT 0, 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            ContentConsumptions consumption = new ContentConsumptions(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)));
            return consumption;
        }
        return null;
    }

    //Function to get first content for the skill set
    public Content getFirstContentForTheSkillSet(int skillId){
        String selectQuery = "SELECT * FROM " + DEV4X_NODES_TABLE  + " WHERE " + NODE_SKILL_ID + " = " + skillId + " ORDER BY id ASC LIMIT 0, 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int nodeId = Integer.parseInt(cursor.getString(0));
            int contentId = Integer.parseInt(cursor.getString(2));
            selectQuery = "SELECT * FROM "+ DEV4X_CONTENTS_TABLE + " WHERE id = " +contentId;
            cursor = db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()){
                Content con = new Content(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                        );
                con.setNodeId(nodeId);
                return con;
            }
            return null;
        }
        return null;
    }

    //Function to get next content for the skill set
    public Content getNextContentForTheSkillSet(int skillId, int id){
        String selectQuery = "SELECT * FROM " + DEV4X_NODES_TABLE  + " WHERE " + NODE_SKILL_ID + " = " + skillId + " AND id > " + id + " ORDER BY id ASC LIMIT 0, 1";
        Log.v("content_put",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int nodeId = Integer.parseInt(cursor.getString(0));
            int contentId = Integer.parseInt(cursor.getString(2));
            Log.v("content_put","got content"+contentId);
            selectQuery = "SELECT * FROM "+ DEV4X_CONTENTS_TABLE + " WHERE id = " +contentId;
            Cursor contentCursor = db.rawQuery(selectQuery, null);
            if(contentCursor.moveToFirst()){
                Log.v("content_put","got move to first");
                Log.v("content_put", contentCursor.getString(2));
                        Content con = new Content(Integer.parseInt(contentCursor.getString(0)),
                                contentCursor.getString(1),
                                contentCursor.getString(2),
                                contentCursor.getString(3)
                        );
                con.setNodeId(nodeId);
                return con;
            }
            return null;
        }
        return null;
    }

    public Node getNode(int nodeId) {
        String selectQuery = "SELECT  * FROM " + DEV4X_NODES_TABLE + " WHERE id = " + nodeId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

            } while (cursor.moveToNext());
        }
        return null;
    }

    public void increaseViewCountOfSkill(int skillId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DEV4X_SKILLS_TABLE + " WHERE " + ID + " = " + skillId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int currentCount = cursor.getInt(3);
            currentCount++;
            ContentValues values = new ContentValues();
            values.put("view_count", currentCount);
            db.update(DEV4X_SKILLS_TABLE, values, ID + " = ?",
                    new String[]{String.valueOf(skillId)});
            Log.v("VideoCount", "VideoCount " + currentCount);
        }
    }

    public void createVideoConsumptionSessionEvent(int content_id, int node_id, int skill_id, String event) {
        Log.v("VideoStatus", "VideoStatus-" + event);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content_id", content_id);
        values.put("node_id", node_id);
        values.put("skill_id", skill_id);
        values.put(EVENT, event);
        values.put(TIME, System.currentTimeMillis());
        db.insert(DEV4X_CONTENT_CONSUMPTIONS, null, values);
    }

    public long startAssessment(int contentId, int nodeId, int skillId, long startTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content_id", contentId);
        values.put("node_id", nodeId);
        values.put("skill_id", skillId);
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
