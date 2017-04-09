package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.id.selectAll;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class SandwichBuilder {

    private Sandwich sandwich;
    private Activity a;
    private Button nextButton;
    private SandwichBuilder instance;


    public SandwichBuilder(Activity a)
    {
        this.a = a;
        this.instance = this;
        a.setContentView(R.layout.bread_screeen);
        this.nextButton = (Button) this.a.findViewById(R.id.button);
        this.sandwich = new Sandwich(this.a.getBaseContext());

        this.breadChoice();
    }

    public void breadChoice()
    {
        nextButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) instance.a.findViewById(R.id.breadGroup);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton breadSelection = ((RadioButton)instance.a.findViewById(radioButtonID));

                if(breadSelection != null) {
                    instance.sandwich.setBread(
                            breadSelection.getText().toString()
                    );

                    instance.a.setContentView(R.layout.meat_screen);
                    instance.meatChoice();
                }
            }
        });
    }

    private void meatChoice()
    {
        this.nextButton = (Button) this.a.findViewById(R.id.meat_next_button);

        nextButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) instance.a.findViewById(R.id.meatGroup);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton meatSelection = ((RadioButton)instance.a.findViewById(radioButtonID));

                if(meatSelection != null) {
                    System.out.println(meatSelection.getText().toString());
                    instance.sandwich.setSandwichType(
                            meatSelection.getText().toString()
                    );


                    instance.a.setContentView(R.layout.salad_screen);
                    instance.saladChoice();
                }
            }
        });
    }

    private void saladChoice()
    {
        this.nextButton = (Button) this.a.findViewById(R.id.salads_nextButton);

        nextButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox cbb = (CheckBox) instance.a.findViewById(R.id.checkbox_lettuce);

                CheckBox[] vegetables = new CheckBox[]
                        {
                                (CheckBox) instance.a.findViewById(R.id.checkbox_lettuce),
                                (CheckBox) instance.a.findViewById(R.id.checkbox_pickles),
                                (CheckBox) instance.a.findViewById(R.id.checkbox_onion),
                                (CheckBox) instance.a.findViewById(R.id.checkbox_pepper),
                                (CheckBox) instance.a.findViewById(R.id.checkbox_tomato),
                                (CheckBox) instance.a.findViewById(R.id.checkbox_cucumber),
                                (CheckBox) instance.a.findViewById(R.id.checkbox_olives),
                                (CheckBox) instance.a.findViewById(R.id.checkbox_carrot)
                        };
                ArrayList<String> saladsSelected = new ArrayList<String>();

                for(CheckBox cb : vegetables)
                {
                    if(cb.isChecked())
                        saladsSelected.add(cb.getText().toString());
                }

                instance.sandwich.setSalads(saladsSelected);
                instance.a.setContentView(R.layout.sauce_screen);
                instance.sauceChoice();
            }
        });
    }

    private void sauceChoice()
    {
        this.nextButton = (Button) instance.a.findViewById(R.id.showMatchesBtn);

        this.nextButton.setOnClickListener(new View.OnClickListener() {

            CheckBox[] sauces = new CheckBox[]
                    {
                            (CheckBox) instance.a.findViewById(R.id.checkbox_mayo),
                            (CheckBox) instance.a.findViewById(R.id.checkbox_ketchup),
                            (CheckBox) instance.a.findViewById(R.id.checkbox_barbecue),
                            (CheckBox) instance.a.findViewById(R.id.checkbox_cesar),
                            (CheckBox) instance.a.findViewById(R.id.checkbox_chilli),
                            (CheckBox) instance.a.findViewById(R.id.checkbox_brown),
                    };

            @Override
            public void onClick(View v) {
                ArrayList<String> sauceSelected = new ArrayList<String>();

                for(CheckBox cb : sauces)
                {
                    if(cb.isChecked())
                        sauceSelected.add(cb.getText().toString());
                }

                instance.sandwich.setSauces(sauceSelected);
                instance.sandwich.registerSandwich();

                instance.sandwich.saveSandwichLocally();

                instance.a.startActivity(new Intent(instance.a, Main.class));
            }
        });
    }
}
