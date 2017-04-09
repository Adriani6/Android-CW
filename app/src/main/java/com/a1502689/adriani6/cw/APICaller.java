package com.a1502689.adriani6.cw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Adriani6 on 3/13/2017.
 */

public class APICaller extends Sandwich{

    private Context c;

    public APICaller(Context c)
    {
        super(c);
        this.c = c;
    }

    public APICaller()
    {}

    public ArrayList<String> getConversations()
    {
        System.out.printf(super.getSandwichID());
        try {
            URL url = new URL("http://193.70.114.144/getMessages?me="+ super.getSandwichID());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
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

            JSONArray jo = new JSONArray(response.toString());
            ArrayList<String> convos = new ArrayList<String>();

            for(int i = 0; i < jo.length(); i++)
            {
                convos.add(new JSONObject(jo.get(i).toString()).get("_id").toString());
            }

            return convos;

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public JSONArray getMessages(String id)
    {
        System.out.println("HELLO");
        try {
            URL url = new URL("http://193.70.114.144/getMessages?me="+ super.getSandwichID());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
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

            JSONArray jo = new JSONArray(response.toString());
            System.out.println(jo);

            for(int i = 0; i < jo.length(); i++)
            {
                System.out.println(new JSONObject(jo.get(i).toString()).get("_id").toString().equals(id));
                if(id.equals(new JSONObject(jo.get(i).toString()).get("_id").toString()))
                {
                    return new JSONArray(new JSONObject(jo.get(i).toString()).get("messages").toString());
                }
            }

            return new JSONArray();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return null;

    }

    public String getMatch()
    {
        try {
            URL url = new URL("http://193.70.114.144/match?me="+ super.getSandwichID());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
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

            JSONObject matchedProfile = new JSONObject(response.toString());
            System.out.println(matchedProfile.get("id"));
            boolean matched = false;
            String message = "New Match";

            if(matchedProfile != null) {
                if (matchedProfile.getString("id") == "-1") {
                    message = "No new Matches!";
                }
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setMessage(message)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create().show();

                    return matchedProfile.getString("id");
            }

            if(!matched)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage("No Matches.")
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create().show();

                return null;
            }



        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch(JSONException je)
        {
            je.printStackTrace();
        }

        return null;

    }

    public void sendMessage(String sender, String receiver, String message)
    {
        String s = sender;
        String r = receiver;
        String m = message;

        try {
            URL url = new URL("http://193.70.114.144/sendMessage?me="+ sender + "&receiver=" + receiver + "&message=" + message);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
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

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
