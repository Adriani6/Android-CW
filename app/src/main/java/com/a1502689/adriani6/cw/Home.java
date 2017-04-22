package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class Home extends Activity {

        private APICaller ac;
        private Context c;

        //private LocalBinder mBinder;
        private ServiceConnection mConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName className, IBinder service) {
                        //mBinder = (LocalBinder) service;
                }
                @Override
                public void onServiceDisconnected(ComponentName arg0) {
                }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.home);

                ac = new APICaller(this);
                c = this;

                this.updateMatchedCounter();

                if(this.isFinishing())
                        this.checkUpdates();

                this.homeFunctions(savedInstanceState);
        }

        private void homeFunctions(final Bundle instance)
        {
                final Activity thing = this;
                final SQLite sql = new SQLite(this);
                final SQLiteMatches msqli = new SQLiteMatches(this);

                final Button findMatch = ((Button) findViewById(R.id.button3));

                findMatch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                //Sandwich sandwich = new Sandwich(v.getContext(), instance);

                                String response = new APICaller(thing).getMatch();

                                if(response != "-1")
                                {
                                        TextView tv = ((TextView) findViewById(R.id.textView3));
                                        Sandwich sandwich = new Sandwich(thing);
                                        sandwich.loadSandwich();

                                        sql.getWritableDatabase().execSQL("UPDATE sandwich SET matches = matches + 1 WHERE sandwichID = '" + sandwich.getSandwichID() + "'");
                                        msqli.addMatchToDB(response);
                                        updateMatchedCounter();
                                }

                                System.out.println(response);
                                //sandwich.findMatch();


                                //tv.setText("1 Match");
                        }
                });
        }

        private void updateMatchedCounter()
        {
                SQLite sql = new SQLite(this);
                TextView tv = ((TextView) findViewById(R.id.textView3));

                SQLiteDatabase db = sql.getReadableDatabase();

                String[] projection = {
                        "id",
                        "matches"
                };


                String sortOrder =
                        "id" + " DESC";

                Cursor cursor = db.query(
                        "sandwich",                     // The table to query
                        projection,                               // The columns to return
                        null,                                // The columns for the WHERE clause
                        null,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        sortOrder                                 // The sort order
                );

                try {
                        cursor.moveToFirst();

                        tv.setText("You have " + Integer.toString(cursor.getInt(1)) + " matches.");

                }catch(Exception e) {
                        //Try catch to temporary remove app crashing when querying an empty db at the start of the app.
                }

                cursor.close();
                //tv.setText("0");
        }

        private void checkUpdates()
        {
                JSONObject ja = ac.checkUpdates(ac.getSandwichID());
                StringBuilder message = new StringBuilder();

                try {
                        if (ja.length() > 0) {
                                if(ja.getInt("Messages") > 0 && ja.getInt("Matches") > 0)
                                {
                                        message.append("You have " + ja.getInt("Messages") + " new messages and " + ja.getInt("Matches") + " new matches since your last visit.");
                                }
                                else
                                {
                                        if(ja.getInt("Messages") > 0)
                                        {
                                                message.append("You have " + ja.getInt("Messages") + " new messages since your last visit.");
                                        }
                                        else if(ja.getInt("Matches") > 0)
                                        {
                                                message.append("You have " +ja.getInt("Matches") + " new matches since your last visit.");
                                        }

                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                                builder.setMessage(message)
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                        // User cancelled the dialog
                                                }
                                        });
                                // Create the AlertDialog object and return it
                                builder.create().show();
                        }
                }catch (JSONException e)
                {
                        System.out.println(e);
                }
        }

}
