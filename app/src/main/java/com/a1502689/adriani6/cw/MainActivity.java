package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private MainActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        System.out.println("Initializing");
        File dbFile = new File(getApplicationContext().getDatabasePath("sandwich.db").toString());
        //dbFile.delete();
        System.out.println(dbFile.exists());

        if(!dbFile.exists())
        {
            SQLiteDatabase db = openOrCreateDatabase("sandwich.db", MODE_PRIVATE, null);
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