package com.a1502689.adriani6.cw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TabHost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Adriani6 on 3/7/2017.
 */

public class Sandwich extends Thread{

    private String sandwichID;
    private String breadType, sandwichType;
    private ArrayList<String> sauces, salads;
    private SQLite sql;
    private Context c;
    private Bundle b;

    public Sandwich(){}

    public Sandwich(Context c)
    {
        this.c = c;
        this.loadSandwich();
    }

    public Sandwich(Context c, Bundle instance)
    {
        this.c = c;
        this.b = instance;
        this.loadSandwich();
    }

    public String getSandwichID()
    { return this.sandwichID; }

    public void setBread(String bread)
    {
        this.breadType = bread;
    }
    public String getBread(){return this.breadType;}

    public void setSandwichType(String type)
    {
        this.sandwichType = type;
    }
    public String getSandwichType(){return this.sandwichType;}

    public void setSalads(ArrayList<String> salads)
    {
        this.salads = salads;
    }

    public ArrayList<String> getSalads()
    {
        return this.salads;
    }

    private String getSaladsAsString()
    {
        StringBuilder saladsBuilder = new StringBuilder();

        if(this.getSalads() != null) {
            for (String salad : this.getSalads()) {
                saladsBuilder.append(salad + "_");
            }
        }
        return saladsBuilder.toString();
    }

    private void setSaladsFromString(String saladsString)
    {
        ArrayList<String> newSalads = new ArrayList<String>();

        if(saladsString.length() > 0) {
            for (String salad : saladsString.split("_")) {
                newSalads.add(salad);
            }
        }
        this.setSalads(newSalads);
    }

    public void setSauces(ArrayList<String> sauces)
    {
        this.sauces = sauces;
    }

    private void setSaucesFromString(String saucesString)
    {
        ArrayList<String> newSauces = new ArrayList<String>();

        if(saucesString.length() > 0) {
            for (String sauce : saucesString.split("_")) {
                newSauces.add(sauce);
            }
        }

        this.setSauces(newSauces);
    }

    private  String getSauceAsString()
    {
        StringBuilder sauceBuilder = new StringBuilder();

        if(this.getSauces() != null) {
            for (String sauce : this.getSauces()) {
                sauceBuilder.append(sauce + "_");
            }
        }

        return sauceBuilder.toString();
    }

    public void saveSandwichLocally()
    {
        this.sql = new SQLite(this.c);
        SQLiteDatabase db = this.sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bread", this.breadType);
        values.put("meat", this.sandwichType);
        values.put("salads", this.getSaladsAsString());
        values.put("sauces", this.getSauceAsString());
        values.put("sandwichID", this.sandwichID);
        values.put("matches", 0);

        db.insert("sandwich", null, values);
    }

    public ArrayList<String> getSauces()
    {
        return this.sauces;
    }

    public void loadSandwich()
    {
        this.sql = new SQLite(this.c);
        SQLiteDatabase db = this.sql.getReadableDatabase();

        String[] projection = {
                "id",
                "bread",
                "meat",
                "salads",
                "sauces",
                "sandwichID"
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

            this.setBread(cursor.getString(1));
            this.setSandwichType(cursor.getString(2));
            this.setSaladsFromString(cursor.getString(3));
            this.setSaucesFromString(cursor.getString(4));
            this.sandwichID = cursor.getString(5);

        }catch(Exception e) {
            //Try catch to temporary remove app crashing when querying an empty db at the start of the app.
        }

        cursor.close();

    }

    private final String USER_AGENT = "Mozilla/5.0";


    //To - Remove
    public String findMatch()
    {
        String resp = null;
        JSONObject obj = new JSONObject();
        this.loadSandwich();
        try {
            obj.put("bread", this.getBread());
            obj.put("type", this.getSandwichType());
            obj.put("salads", this.getSaladsAsString());
            obj.put("sauces", this.getSauceAsString());

            System.out.println(this.getBread());

                            try {
                                System.out.println(obj.toString());
                                URL url = new URL("http://192.168.0.23/match?id="+ obj.toString());

                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestProperty("User-Agent", USER_AGENT);
                                int responseCode = con.getResponseCode();
                                System.out.println("\nSending 'GET' request to URL : " + url);
                                System.out.println("Response Code : " + responseCode);

                                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }

                                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                                        builder.setMessage("New Match")
                                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // User cancelled the dialog
                                                    }
                                                });
                                        // Create the AlertDialog object and return it
                                        builder.create().show();


                                in.close();

                                //print result
                                resp = response.toString();



                            }catch (MalformedURLException e) {
                                e.printStackTrace();
                            }catch (IOException e) {
                                e.printStackTrace();
                            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resp;
    }

    public String registerSandwich()
    {
        String resp = null;
        JSONObject obj = new JSONObject();
        this.loadSandwich();
        try {
            obj.put("bread", this.getBread());
            obj.put("type", this.getSandwichType());
            obj.put("salads", this.getSaladsAsString());
            obj.put("sauces", this.getSauceAsString());

            System.out.println(this.getBread());

            try {
                System.out.println(obj.toString());
                URL url = new URL("http://193.70.114.144/register?sandwich="+ obj.toString());

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                //print result
                resp = response.toString();
                JSONObject jObject = new JSONObject(resp);

                resp = jObject.getString("reg");
                this.sandwichID = resp;

                System.out.println(jObject.getString("reg"));


            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resp;
    }

}
