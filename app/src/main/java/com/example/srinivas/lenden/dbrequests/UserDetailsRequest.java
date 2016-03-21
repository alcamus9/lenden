package com.example.srinivas.lenden.dbrequests;

import android.content.Context;

import com.example.srinivas.lenden.database.DBAdapter;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.lenden.requests.AsyncRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by srinivas on 3/19/2016.
 */
public class UserDetailsRequest implements BaseDBRequest {

    AsyncRequestListener listener;
    Context appContext;
    String users_data_string;
    JSONArray users_data_json;
    HashMap<String, User> user_details_map;
    User user;
    DBAdapter dbAdapter;

    public UserDetailsRequest() {

    }

    public  UserDetailsRequest(AsyncRequestListener listener, Context context) {
        this.listener = listener;
        this.appContext = context;
        this.dbAdapter = new DBAdapter(context);
    }

    @Override
    public List<String> getKeys() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void sendRequest(HashMap<String, Object> data) {
        // form json object with username and password or facebook id
        Long user_id = (Long) data.get("user_id");
        this.user = this.dbAdapter.userHelper.fetchUser(user_id);
        //send to appropriate URL : to be done
        this.handleResponse();
    }

    @Override
    public void handleResponse() {
        // on success take to home page.
        // on failure show password mismatch
        // text.
        HashMap<String, User> resp = new HashMap<>();
        resp.put("user", this.user);
        this.listener.onResponseReceived("UserDetailsRequest", resp);
    }

    @Override
    public void handleError() {
        //common error handler and

    }
}