package com.a1502689.adriani6.cw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class Sandwich {

    private String breadType, sandwichType;
    private String[] sauces, salads;
    private SQLite sql;
    private Context c;

    public Sandwich(Context c)
    {
        this.c = c;
    }

    public void setBread(String bread)
    {
        this.breadType = bread;
    }
    public String getBread(){return this.breadType;}

    public void setSandwichType(String type)
    {
        this.sandwichType = type;
    }
    public String getSandwichType(){return this.sandwichType;}

    public void setSalads(String[] salads)
    {

    }

    public void setSauces(String[] sauces)
    {

    }

    public void saveSandwichLocally()
    {
        this.sql = new SQLite(this.c);
        SQLiteDatabase db = this.sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bread", this.breadType);
        values.put("meat", this.sandwichType);

        db.insert("sandwich", null, values);
    }

    public void populateEditScreen()
    {

    }

    public void loadSandwich()
    {
        this.sql = new SQLite(this.c);
        SQLiteDatabase db = this.sql.getReadableDatabase();

        String[] projection = {
                "id",
                "bread",
                "meat"
        };

// Filter results WHERE "title" = 'My Title'
        String selection = "id" + " = ?";
        String[] selectionArgs = { "1" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                "id" + " DESC";

        Cursor cursor = db.query(
               "sandwich",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToFirst();

        this.setBread(cursor.getString(1));
        this.setSandwichType(cursor.getString(2));

        //System.out.println(cursor.getString(1));
        /*while(cursor.moveToNext()) {
            System.out.println(cursor.toString());
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow("id"));
            itemIds.add(itemId);
        }*/
        cursor.close();

        //System.out.println(itemIds);
    }


}
