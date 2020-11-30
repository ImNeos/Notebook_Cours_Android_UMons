package com.umons.projet.creactif.database_int;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.umons.projet.creactif.model.CheckBoxNotesModel;
import com.umons.projet.creactif.model.NoteListObject;

import java.util.List;

public class DB_NotesCheckBox {
    private DB_NotesCheckBox() {
    }

    private static DB_NotesCheckHelper db_helper = null;
    private static DB_NotesCheckBox instance = null;


    public static DB_NotesCheckBox getInstance(Context context) {
        if (instance == null) {
            instance = new DB_NotesCheckBox();
            db_helper = new DB_NotesCheckHelper(context);
        }
        return instance;
    }

    public void addElementTodB(String from_name, String itemname, Boolean isCheck, long date) {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ID, date);
        values.put(FeedEntry.COLUMN_NAME_FROM, from_name);
        values.put(FeedEntry.COLUMN_NAME_ITEM, itemname);
        values.put(FeedEntry.COLUMN_NAME_DATE, date);
        values.put(FeedEntry.COLUMN_IS_CHECK, isCheck);


        values.put(FeedEntry.COLUMN_NAME_ID, System.currentTimeMillis());
        long id = db.insert(FeedEntry.TABLE_NAME, null, values);
        Log.i("Add_ID", Long.toString(id));


      /*  long x = checkAlreadyExistFromNameReturnID(from_name, itemname);
        if (x != -1)
        {
            values.put(FeedEntry.COLUMN_NAME_ID, x);
            db.update(FeedEntry.TABLE_NAME, values, FeedEntry.COLUMN_NAME_ID + "=?", new String[]{Long.toString(x)});
        } else
            {
                values.put(FeedEntry.COLUMN_NAME_ID, System.currentTimeMillis());
                long id = db.insert(FeedEntry.TABLE_NAME, null, values);
                Log.i("Add_ID", Long.toString(id));
        }*/
    }

    public void fillInlist(List<CheckBoxNotesModel> Notes_list, String fromname)
    {
        SQLiteDatabase db = db_helper.getReadableDatabase();
        Notes_list.clear();
        Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_NAME_FROM + "=?", new String[]{fromname});

        while (cursor.moveToNext()) {
            CheckBoxNotesModel checkBoxNotesModel = new CheckBoxNotesModel(fromname,
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ITEM)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_IS_CHECK))>0,
                    cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DATE)));

            Log.i("fill_ID", cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ITEM)));
            Notes_list.add(checkBoxNotesModel);
        }
        cursor.close();
    }



   /* public String getNote(String name) {
        SQLiteDatabase db = db_helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + DB_WriteNotes.FeedEntry.TABLE_NAME + " where " + DB_WriteNotes.FeedEntry.COLUMN_NAME_NAME + "=?", new String[]{name});

        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TEXT));
        }
        cursor.close();
        return "";
    }*/

    public long checkAlreadyExistFromNameReturnID(String name, String item) {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        String Query = "Select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_NAME_FROM + " =?";
        Cursor cursor = db.rawQuery(Query, new String[]{name});
        if (cursor.getCount() > 0) {
            if (cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ITEM)).equals(item))
            {
                return cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ID));
            }
        } else
            cursor.close();
        return -1;
    }

    public boolean checkAlreadyExistFromName(String name) {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        String Query = "Select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_NAME_FROM + " =?";
        Cursor cursor = db.rawQuery(Query, new String[]{name});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else
            cursor.close();
        return false;
    }
    public boolean checkAlreadyExistFromITEM(String item) {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        String Query = "Select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_NAME_ITEM + " =?";
        Cursor cursor = db.rawQuery(Query, new String[]{item});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else
            cursor.close();
        return false;
    }


    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes_test";
        public static final String COLUMN_NAME_ID= "ID";
        public static final String COLUMN_NAME_FROM = "from_name";
        public static final String COLUMN_NAME_DATE = "last_update";
        public static final String COLUMN_NAME_ITEM = "item";
        public static final String COLUMN_IS_CHECK = "check_name";


    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ITEM + " TEXT," +
                    FeedEntry.COLUMN_NAME_FROM + " TEXT," +
                    FeedEntry.COLUMN_NAME_DATE + " TEXT," +
                    FeedEntry.COLUMN_IS_CHECK + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static class DB_NotesCheckHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "notes_w_check.db";

        public DB_NotesCheckHelper(Context context) {
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
