package com.example.intolerancetrafficlight;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


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
        ((MainActivity)context).handleDataReturn(info);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss(); // disable progress dialog
    }

    private List<Ingredient> createIngredient(List<Ingredient> list, JSONObject thisObject) throws JSONException {
        Ingredient ingredient = new Ingredient(thisObject);
        list.add(ingredient);
        if(thisObject.has("ingredients")){
            JSONArray ingredients = thisObject.getJSONArray ("ingredients");
            for (int i= 0 ; i<ingredients.length();i++){
                JSONObject obj = ingredients.getJSONObject(i);
                createIngredient(list, obj);
            }
        }
        return list;
    }

    @Override
    protected FoodInfo doInBackground(String... strings) {
        for(String barcode:strings){
            //prepare url request
            String urlString = "https://world.openfoodfacts.org/api/v3/product/" + barcode + ".json";
            FoodInfo info = new FoodInfo();
            try {
                // open url connection
                URL url = new URL(urlString);
                System.out.println(url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                if (con.getResponseCode() == 200) {
                    // Success
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
                    if(jObject.getJSONObject ("product").has("ingredients")){
                        JSONArray ingredients = jObject.getJSONObject ("product").getJSONArray ("ingredients");
                        List<Ingredient> ingredientList = new ArrayList<>();
                        for (int i= 0 ; i<ingredients.length();i++){
                            JSONObject ingredient = ingredients.getJSONObject(i);
                            createIngredient(ingredientList,ingredient);
                        }
                        info.setIngredients(ingredientList);
                    }
                    if(jObject.getJSONObject ("product").has("additives_tags")){
                        List<String> additiveList = new ArrayList<>();
                        JSONArray additives = jObject.getJSONObject ("product").getJSONArray ("additives_tags");
                        for (int i= 0 ; i<additives.length();i++){
                            additiveList.add(additives.getString(i));
                        }
                        info.setAdditives(additiveList);
                    }

                    con.disconnect();
                }
            } catch (MalformedURLException | ProtocolException e) {
                System.out.println(e);
                e.printStackTrace();
                info.setException(e);

            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
                info.setException(e);
            } catch (JSONException e) {
                System.out.println(e);
                e.printStackTrace();
                info.setException(e);
            }
            return info;
        }
        return null;
    }
}
