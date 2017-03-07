package com.a1502689.adriani6.cw;

import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this.initializeTabs();
        System.out.println("Initializing");
        File dbFile = new File(getApplicationContext().getDatabasePath("sandwich.db").toString());
        //dbFile.delete();
        System.out.println(dbFile.exists());
        setContentView(R.layout.main_screen);
        //setContentView(R.layout.bread_screeen);

        if(!dbFile.exists())
        {
            SQLiteDatabase db = openOrCreateDatabase("sandwich.db", MODE_PRIVATE, null);


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
            //setContentView(R.layout.main_screen);
            //this.initializeTabs();
        }
    }

    private void initializeTabs() {
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        spec = tabHost.newTabSpec("home"); // Create a new TabSpec using tab host
        spec.setIndicator("HOME"); // set the “HOME” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, Home.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs

        spec = tabHost.newTabSpec("Messages"); // Create a new TabSpec using tab host
        spec.setIndicator("MESSAGES"); // set the “CONTACT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, Messages.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Edit"); // Create a new TabSpec using tab host
        spec.setIndicator("EDIT"); // set the “ABOUT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, Edit.class);
        spec.setContent(intent);
        tabHost.addTab(spec);
        //set tab which one you want to open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // display the name of the tab whenever a tab is changed
                Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }


}