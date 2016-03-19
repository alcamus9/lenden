package com.example.srinivas.lenden.dbrequests;

import android.content.Context;

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

    public UserDetailsRequest() {

    }

    public  UserDetailsRequest(AsyncRequestListener listener, Context context) {
        this.listener = listener;
        this.appContext = context;
        this.init_request();
    }

    @Override
    public List<String> getKeys() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    public User makeUserFromJSON(JSONObject json) {
        User user_obj = null;
        try {
            long id = json.getLong("id");
            String user_name = json.getString("user_name");
            String password = json.getString("password");
            String name = json.getString("name");
            String email_id = json.getString("email_id");
            String phone_number = json.getString("phone_number");
            String fb_profile_id = json.getString("fb_profile_id");
            ArrayList<Long> contacts = this.extractLongArray(json, "contacts");
            ArrayList<Long> groups = this.extractLongArray(json, "groups");

            user_obj = new User(id, user_name, password, name, email_id, phone_number,
                    fb_profile_id, contacts, groups);
        } catch(JSONException e) {
            e.printStackTrace(System.err);
        }
        return  user_obj;
    }


    public void init_request() {
        this.user_details_map = new HashMap<>();
        //for now, read json file, search and authenticate
        try{
            InputStream is =  this.appContext.getAssets().open("users_data.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            users_data_string = new String(buffer, "utf-8");
            JSONObject data_json = new JSONObject(users_data_string);
            users_data_json = data_json.getJSONArray("users");

            for (int i=0; i < users_data_json.length(); i++) {
                JSONObject j = users_data_json.getJSONObject(i);
                User u = this.makeUserFromJSON(j);
                user_details_map.put(j.getString("id"), u);
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace(System.err);
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
    }

    private ArrayList<Long> extractLongArray(JSONObject j, String key) {
        ArrayList<Long> returnArray = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = j.getJSONArray(key);

            for(int i=0; i < jsonArray.length(); i++) {
                returnArray.add(jsonArray.getLong(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    private void fetch_user_details(Long user_id) {
        Object[] keys = this.user_details_map.keySet().toArray();
        for(int i=0; i<keys.length; i++) {
            User u = this.user_details_map.get((String)keys[i]);
            if (u.getId() == user_id) {
                this.user = u;
                return;
            }
        }
        this.user=null;
    }

    @Override
    public void sendRequest(HashMap<String, Object> data) {
        // form json object with username and password or facebook id
        Long user_id = (Long) data.get("user_id");
        this.fetch_user_details(user_id);
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