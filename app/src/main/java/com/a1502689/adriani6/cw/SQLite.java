package com.a1502689.adriani6.cw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by Adriani6 on 3/9/2017.
 */

public class SQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sandwich.db";

    private String createTable = "CREATE TABLE sandwich (id INTEGER PRIMARY KEY, bread TEXT, meat TEXT, salads TEXT, sauces TEXT, sandwichID TEXT, matches INTEGER)";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
