package com.example.intolerancetrafficlight;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RestDataRequestor extends AsyncTask<String, Void, FoodInfo> {
    ProgressDialog progressDialog;
    Context context;


    public RestDataRequestor(Context context) {
        this.context = context;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // display a progress dialog to show the user something is happening
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("processing results");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(FoodInfo info) {
        progressDialog.dismiss(); // disable progress dialog
        System.out.println("postexecute");
        ((MainActivity)context).handleDataReturn(info);
    }

    @Override
    protected FoodInfo doInBackground(String... strings) {
        System.out.println(strings);
        for(String barcode:strings){
            //prepare url request
            String urlString = "https://world.openfoodfacts.org/api/v3/product/" + barcode + ".json";
            System.out.println(urlString);
            try {
                // open url connection
                URL url = new URL(urlString);
                System.out.println(url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                System.out.println(con.getResponseCode());
                if (con.getResponseCode() == 200) {
                    // Success
                    FoodInfo info = new FoodInfo();
                    // create Json String from input and convert it to JSonObject
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    JSONObject jObject = new JSONObject(content.toString());
                    info.setBrand(jObject.getJSONObject ("product").getString("brands"));
                    info.setName(jObject.getJSONObject ("product").getString("product_name"));
                    con.disconnect();
                    return info;
                }
            } catch (MalformedURLException | ProtocolException e) {
                System.out.println(e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        return null;
    }
}
