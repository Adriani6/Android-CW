package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity instance = this;

        File dbFile = new File(getApplicationContext().getDatabasePath("sandwich.db").toString());
        File dbMatchesFile = new File(getApplicationContext().getDatabasePath("sandwichMatches.db").toString());

        //dbFile.delete();
        //dbMatchesFile.delete();

        if(!dbFile.exists())
        {
            SQLiteDatabase db = openOrCreateDatabase("sandwich.db", MODE_PRIVATE, null);
            openOrCreateDatabase("sandwichMatches.db", MODE_PRIVATE, null);
            new SandwichBuilder(instance);

            /*File anotherFile = new File(getApplicationContext().getDatabasePath("sandwich.db").toString());

            if(!anotherFile.exists())
            {
                System.out.println("Doesnt exist");
            }
            else
            {
                System.out.println("Exists");
            }*/
        }
        else
        {
            startActivity(new Intent(this, Main.class));
        }
    }

}