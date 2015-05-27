package ech98.echen.stackable;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by echen on 5/26/15.
 */
public class FoodEss_GetFoodTask extends AsyncTask<String, Void, JSONObject>{
    private BufferedReader in = null;
    private OnFoodTaskCompleted callback;

    public FoodEss_GetFoodTask(OnFoodTaskCompleted callback){
        this.callback = callback;
    }

    protected void onPreExecute(){
    }
    protected JSONObject doInBackground(String... params) {
//        params[0] = url, params[1] = upc, params[2] = session id, params[3] = api_key
        params[0] += "productscore?u="+params[1]+"&sid="+params[2]+"&f=json&api_key="+params[3];

        String line = "";
        String result = "";

        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while((line = in.readLine()) != null){
                result += line;
            }
            in.close();

            return new JSONObject(result);
        } catch (Exception e){

        }


        return null;
    }
    protected void onProgressUpdate(){

    }
    protected void onPostExecute(JSONObject data){
        callback.onFoodTaskCompleted(data);
    }
}
