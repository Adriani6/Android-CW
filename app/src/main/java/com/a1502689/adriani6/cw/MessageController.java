package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Adriani6 on 3/13/2017.
 */

public class MessageController extends Activity {

    private ArrayList<String> messages;
    private APICaller ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_message);

        ac = new APICaller(this);

        this.initialize();
        this.responseListener();
    }

    private void initialize()
    {
        Button backButton = ((Button) findViewById(R.id.show_messsage_back_button));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();

        final ListView listview = (ListView) findViewById(R.id.messages_list);

        final JSONArray list = ac.getMessages(extras.getString("id"));
        messages = new ArrayList<String>();

        for(int i = 0; i < list.length(); i++)
        {
            try
            {
                JSONObject message = new JSONObject(list.get(i).toString());
                if(message.get("sender").equals(ac.getSandwichID()))
                {
                    messages.add("You: " + message.get("message"));
                }
                else
                {
                    messages.add("Match: " + message.get("message"));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }

        System.out.println(list);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final String item = (String) parent.getItemAtPosition(position);

            }

        });
    }

    private void responseListener()
    {
        Button sendReply = ((Button) findViewById(R.id.sendReplyBtn));
        final TextView input = ((TextView) findViewById(R.id.editText));

        sendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();

                messages.add("You: " + input.getText());
                ac.sendMessage(ac.getSandwichID(), extras.getString("id"), input.getText().toString());
                final ListView listview = (ListView) findViewById(R.id.messages_list);
                final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, messages);
                listview.setAdapter(adapter);
            }
        });
    }

}
