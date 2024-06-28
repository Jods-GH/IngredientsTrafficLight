package com.example.intolerancetrafficlight;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class FoodIntoleranceRequestor extends AsyncTask<IntoleranceEnum, Void, IntoleranceInfo> {
    ProgressDialog progressDialog;
    Context context;


    public FoodIntoleranceRequestor(Context context) {
        this.context = context;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // display a progress dialog to show the user something is happening
    }

    @Override
    protected void onPostExecute(IntoleranceInfo info) {
        ((MainActivity)context).handleDataReturn(info);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss(); // disable progress dialog
    }

    @Override
    protected IntoleranceInfo doInBackground(IntoleranceEnum... intolerances) {
        Map<IntoleranceEnum, Intolerance> intInfoMap = new HashMap<IntoleranceEnum, Intolerance>();
        Map<IntoleranceEnum, Map<Locale,String>> localizedNamesMap= new HashMap<>();
        Map<IntoleranceEnum, String[]> supportedLocales = new HashMap<>();
        for(IntoleranceEnum intolerance:intolerances){
            //prepare url request
            String urlString = "https://raw.githubusercontent.com/Jods-GH/IngredientsTrafficLight/master/ServerData/" + intolerance + ".json";

            try {
                // open url connection
                URL url = new URL(urlString);

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

                    JSONArray ingredients = jObject.getJSONArray ("badIngredients");
                    List<ToleratedIngredient> intolernacesList = new ArrayList<>();
                    for (int i= 0 ; i<ingredients.length();i++){
                        JSONObject thisObject = ingredients.getJSONObject(i);
                        ToleratedIngredient intoleranceIngredient = new ToleratedIngredient(thisObject.getString("nameString"),thisObject.getInt("ciqualFoodCode"),false);
                        intolernacesList.add(intoleranceIngredient);
                    }
                    JSONArray toleratedIngredients = jObject.getJSONArray ("goodIngredients");
                    List<ToleratedIngredient> tolernacesList = new ArrayList<>();
                    for (int i= 0 ; i<toleratedIngredients.length();i++){
                        JSONObject thisObject = toleratedIngredients.getJSONObject(i);
                        ToleratedIngredient intoleranceIngredient = new ToleratedIngredient(thisObject.getString("nameString"),thisObject.getInt("ciqualFoodCode"),false);
                        tolernacesList.add(intoleranceIngredient);
                    }
                    Intolerance intol = new Intolerance(intolerance,intolernacesList,tolernacesList);

                    JSONObject localizedNames = jObject.getJSONObject("localizedNames");
                    String[] locales = new String[localizedNames.length()];
                    Map<Locale,String> localizedMap = new HashMap<>();
                    int i = 0;
                    for (Iterator<String> it = localizedNames.keys(); it.hasNext(); ) {
                        String key = it.next();
                        String translation = localizedNames.getString(key);
                        localizedMap.put(Locale.forLanguageTag(key),translation);
                        locales[i] = key;
                        i++;
                    }
                    localizedNamesMap.put(intolerance,localizedMap);
                    supportedLocales.put(intolerance,locales);
                    intInfoMap.put(intolerance,intol);
                    con.disconnect();
                }
            } catch (MalformedURLException | ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(!intInfoMap.isEmpty())
            return new IntoleranceInfo(intInfoMap,localizedNamesMap,supportedLocales);
        else
            return null;
    }
}
