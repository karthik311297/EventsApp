package com.example.karthik.messagenwishes.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Eventhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventDb";
    private static final String TABLE_EVENTS = "eventsData";
    private static final String KEY_DATE = "DATE";
    private static final String KEY_EVENT = "EVENT";
    public Eventhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATOR="CREATE TABLE "+TABLE_EVENTS+"("+KEY_DATE+" TEXT,"+KEY_EVENT+" TEXT)";
        db.execSQL(TABLE_CREATOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS);
        onCreate(db);
    }
    public void addevent(String event,String i){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues p=new ContentValues();
        p.put(KEY_DATE,i);
        p.put(KEY_EVENT,event);
        db.insert(TABLE_EVENTS,null,p);
        db.close();
    }
    public ArrayList<String> eventtlist(String i){
        ArrayList<String>es=new ArrayList<>();
        String showallquery="SELECT * FROM "+TABLE_EVENTS+" WHERE "+KEY_DATE+"=?";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(showallquery,new String[]{i});
        if (cursor.moveToFirst()) {
            do {String e=cursor.getString(1);
                es.add(e);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return es;
    }
    public void updateContact(String i,String ev) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE,i);
        values.put(KEY_EVENT, ev);
        // updating row
        db.update(TABLE_EVENTS, values, KEY_EVENT+ " = ?",
                new String[] { ev });
    }
    public void deleteevents(String i)
    {SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS,KEY_DATE+"=?",new String[]{i});
        db.close();
    }

}
