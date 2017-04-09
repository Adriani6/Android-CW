package com.a1502689.adriani6.cw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class Messages extends AppCompatActivity {

    @Override
    protected void onResume()
    {
        super.onResume();
        final ArrayList<String> matches = new SQLiteMatches(this).getAllMatches();

        final ListView listview = (ListView) findViewById(R.id.messagesList);

        if(matches != null) {
            final ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, matches);
            listview.setAdapter(adapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);

        APICaller ac = new APICaller(this);


        final ListView listview = (ListView) findViewById(R.id.messagesList);

        final ArrayList<String> list = ac.getConversations();
        final ArrayList<String> matches = new SQLiteMatches(this).getAllMatches();

        if(list != null) {
            final ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, matches);
            listview.setAdapter(adapter);
        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final String item = (String) parent.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(), MessageController.class).putExtra("id", item.toString()));

                //setContentView(R.layout.show_message);
            }

        });
    }
}