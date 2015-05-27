package ech98.echen.stackable;

import org.json.JSONObject;

/**
 * Created by echen on 5/26/15. This is an interface to allow a callback to the Activity that calls the Asynctask for retrieving
 * Food Essential's user session.
 */
public interface OnSessionTaskCompleted {
    void onSessionTaskCompleted(JSONObject json);
}
