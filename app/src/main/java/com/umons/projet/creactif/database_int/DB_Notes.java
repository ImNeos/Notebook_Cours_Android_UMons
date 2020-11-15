package com.umons.projet.creactif.database_int;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.umons.projet.creactif.model.NoteListObject;

import java.util.List;

public final class DB_Notes {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DB_Notes() {}



    private static DB_NotesHelper dbHelper = null;
    private static DB_Notes instance = null; // On crée une variable static, cela veut dire qu'on ne peut instancier l'objet qu'une seule fois


    public static DB_Notes getInstance(Context context)
    {
        if (instance == null) { //Si l'objet n'est pas encore instancié, on l'instancie !
            instance = new DB_Notes();
            dbHelper = new DB_NotesHelper(context);
        }
        return instance;
    }

    public boolean checkAlreadyExist(String name)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
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


    public void addElementTodB(String text, String date, int type)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_NAME, text);
        values.put(FeedEntry.COLUMN_NAME_DATE, date);
        values.put(FeedEntry.COLUMN_NAME_TYPE, type);

        long id = db.insert(FeedEntry.TABLE_NAME, null, values);
        Log.i("Add_ID", Long.toString(id));
    }
    public void fillInlist(List<NoteListObject> Notes_list)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Notes_list.clear();
        Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            NoteListObject noteListObject = new NoteListObject(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DATE)),cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TYPE)));
            Log.i("fill_ID", cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_NAME)));
            Notes_list.add(noteListObject);
        }
        cursor.close();
    }

    public void dropDB(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }



    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TYPE = "type";

    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_NAME + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_DATE + " TEXT," +
                    FeedEntry.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static class DB_NotesHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "notes.db";

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