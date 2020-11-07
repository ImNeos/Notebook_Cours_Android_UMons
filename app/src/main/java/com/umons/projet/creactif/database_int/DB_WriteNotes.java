package com.umons.projet.creactif.database_int;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public final class DB_WriteNotes {


    private DB_WriteNotes(){}

    private static DB_NotesHelper db_helper = null;
    private  static DB_WriteNotes instance = null;


    public static DB_WriteNotes getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DB_WriteNotes();
            db_helper = new DB_NotesHelper(context);
        }
        return instance;
    }

    public void addElementTodB(String name, String text)
    {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_NAME, name);
        values.put(FeedEntry.COLUMN_NAME_TEXT, text);



        if (checkAlreadyExist(name))
        {
            db.update(FeedEntry.TABLE_NAME, values, FeedEntry.COLUMN_NAME_NAME + "=?", new String[]{name});
        }
        else
        {
            long id = db.insert(FeedEntry.TABLE_NAME, null, values);
            Log.i("Add_ID", Long.toString(id));
        }
    }
    public String getNote (String name)
    {
        SQLiteDatabase db = db_helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_NAME_NAME + "=?", new String[]{name});

        while (cursor.moveToNext())
        {
            return cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TEXT));
        }
        cursor.close();
        return "";
    }
    public boolean checkAlreadyExist(String name)
    {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        String Query = "Select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_NAME_NAME + " =?";
        Cursor cursor = db.rawQuery(Query, new String[]{name});
        if (cursor.getCount() > 0)
        {
            cursor.close();
            return true;
        }
        else
            cursor.close();
        return false;
    }


    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes_test";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TEXT = "text";

    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_NAME + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TEXT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static class DB_NotesHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "notes_w.db";

        public DB_NotesHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
