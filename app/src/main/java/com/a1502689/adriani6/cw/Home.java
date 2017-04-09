package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class Home extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.home);

                this.updateMatchedCounter();
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

                        tv.setText(Integer.toString(cursor.getInt(1)));

                }catch(Exception e) {
                        //Try catch to temporary remove app crashing when querying an empty db at the start of the app.
                }

                cursor.close();
                //tv.setText("0");
        }

}
