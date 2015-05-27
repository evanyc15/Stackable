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
public class FoodEss_SessionTask extends AsyncTask<String, Void, JSONObject> {
    private BufferedReader in = null;
    private OnSessionTaskCompleted callback;

    public FoodEss_SessionTask(OnSessionTaskCompleted callback){
        this.callback = callback;
    }

    protected void onPreExecute(){
    }
    protected JSONObject doInBackground(String... params) {
        params[0] += "createsession?uid=user_stackable&devid="+params[1]+"&appid="+params[2]+"&f=json&v=1.00&api_key="+params[2];

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
        callback.onSessionTaskCompleted(data);
    }
}

