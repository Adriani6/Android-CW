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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Adriani6 on 3/13/2017.
 */

public class APICaller extends Sandwich{

    private Context c;
    public static String connectionURL = "http://193.70.114.144:2016";

    public APICaller(Context c)
    {
        super(c);
        this.c = c;
    }

    public APICaller()
    {}

    public ArrayList<String> getConversations()
    {

        ArrayList<String> convos = new ArrayList<String>();

        try {
            URL url = new URL(connectionURL + "/getMessages?me="+ super.getSandwichID());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();

            if(responseCode == 200) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                JSONArray jo = new JSONArray(response.toString());


                for (int i = 0; i < jo.length(); i++) {

                    convos.add(new JSONObject(jo.get(i).toString()).get("_id").toString());

                }

            }

            return convos;

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }catch(JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public JSONArray getMessages(String id, String participant)
    {
        try {
            URL url = new URL(connectionURL + "/getMessages?me="+ super.getSandwichID() +"&participant=" + participant);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();

            if(responseCode == 200) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                JSONArray jo = new JSONArray(response.toString());

                return jo;
            }

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }catch(JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return null;

    }

    public String getMatch()
    {

        boolean matched = false;

        try {
            URL url = new URL(connectionURL + "/match?me="+ super.getSandwichID());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            con.setConnectTimeout(5000);

            if(responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                JSONObject matchedProfile = new JSONObject(response.toString());
                System.out.println(matchedProfile.get("id"));

                String message = "New Match";

                if (matchedProfile != null) {
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
            return null;
        }catch(JSONException je)
        {
            je.printStackTrace();
            return null;
        }

        return null;

    }

    public void sendMessage(String conversation, String sender, String message)
    {

        String m = message;

        try {
            URL url = new URL(connectionURL + "/sendMessage?conversation=" + conversation + "&message=" + m + "&sender=" + sender);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();

            if(responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
            }

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject checkUpdates(String me) {

        try {
            URL url = new URL(connectionURL + "/getUpdates?me=" + me);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();

            if(responseCode == 200) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                try {
                    in.close();
                    return new JSONObject(response.toString());
                } catch (JSONException e) {
                    in.close();
                    return new JSONObject();
                }
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        }

        return new JSONObject();
    }
}
