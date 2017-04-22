package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class Edit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        this.retrieve();
    }

    public void retrieve()
    {
        Sandwich sandwich = new Sandwich(this.getBaseContext());

        sandwich.loadSandwich();

        TextView breadTypeLabel = ((TextView) findViewById(R.id.edit_breadType));
        TextView meatTypeLabel = ((TextView) findViewById(R.id.edit_meatType));
        TextView saladsLabel = ((TextView) findViewById(R.id.edit_salads));
        TextView saucesLabel = ((TextView) findViewById(R.id.edit_sauces));

        breadTypeLabel.setText(sandwich.getBread());
        meatTypeLabel.setText(sandwich.getSandwichType());

        StringBuilder sb = new StringBuilder();

        for(String salad : sandwich.getSalads())
        {
            sb.append(salad+"\n");
        }

        saladsLabel.setText(sb.toString());

        sb = new StringBuilder();

        for(String sauce : sandwich.getSauces())
        {
            sb.append(sauce + "\n");
        }

        saucesLabel.setText(sb.toString());

        Button deleteBtn = ((Button) findViewById(R.id.edit_save_button));

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dbFile = new File(getApplicationContext().getDatabasePath("sandwich.db").toString());
                File dbMatchesFile = new File(getApplicationContext().getDatabasePath("sandwichMatches.db").toString());

                dbFile.delete();
                dbMatchesFile.delete();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}