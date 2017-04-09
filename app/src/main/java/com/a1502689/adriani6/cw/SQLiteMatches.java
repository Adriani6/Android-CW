package com.a1502689.adriani6.cw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Adriani6 on 4/8/2017.
 */

public class SQLiteMatches extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sandwichMatches.db";

    private String createMatchesTable = "CREATE TABLE matches (id INTEGER PRIMARY KEY, matchid TEXT)";

    public SQLiteMatches(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.createMatchesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMatchToDB(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("matchid", id);

        db.insert("matches", null, values);
    }

    public ArrayList<String> getAllMatches()
    {
        ArrayList<String> matches = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder =
                "id" + " DESC";

        Cursor cursor = db.query(
                "matches",                     // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        try {
            while(cursor.moveToNext())
            {
                matches.add(cursor.getString(1));
            }

        }catch(Exception e)
        {

        }

        return matches;
    }

}

