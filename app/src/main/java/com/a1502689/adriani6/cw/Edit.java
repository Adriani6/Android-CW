package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class Edit extends AppCompatActivity {

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

        breadTypeLabel.setText(sandwich.getBread());
        meatTypeLabel.setText(sandwich.getSandwichType());
    }
}