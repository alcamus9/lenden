package com.example.srinivas.lenden.dbrequests;

import android.content.Context;

import com.example.srinivas.lenden.objects.Group;
import com.example.srinivas.lenden.objects.Transaction;
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

/**
 * Created by srinivas on 3/19/2016.
 */
public class GroupDetailRequest implements BaseDBRequest {

    AsyncRequestListener listener;
    Context appContext;
    String users_data_string;
    JSONArray users_data_json;
    HashMap<String, Transaction> transactionsMap;
    ArrayList<Group> groups;
    ArrayList<Group> user_groups;
    User user;

    public GroupDetailRequest(AsyncRequestListener listener, Context context) {
        this.listener = listener;
        this.appContext = context;
        this.init_request();
        this.user_groups = new ArrayList<>();
    }

    @Override
    public List<String> getKeys() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    public Group makeTranFromJSON(JSONObject json) {
        Group g = null;
        try {
            Long id = json.getLong("id");
            String name = json.getString("name");
            ArrayList<Long> users = this.extractLongArray(json, "users");

            g = new Group(id, name, users);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return g;
    }


    public void init_request() {
        this.transactionsMap = new HashMap<>();
        this.groups = new ArrayList<Group>();

        try{
            InputStream is =  this.appContext.getAssets().open("groups_data.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            users_data_string = new String(buffer, "utf-8");
            JSONObject data_json = new JSONObject(users_data_string);
            users_data_json = data_json.getJSONArray("groups");

            for (int i=0; i < users_data_json.length(); i++) {
                JSONObject j = users_data_json.getJSONObject(i);
                Group g = this.makeTranFromJSON(j);
                this.groups.add(g);
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetch_user_groups(Long user_id) {
        for(int i=0; i<this.groups.size(); i++) {
            Group g = this.groups.get(i);
            ArrayList<Long> userIds = g.getUsers();
            if(userIds.contains(user_id)) {
                this.user_groups.add(g);
            }
        }
    }

    @Override
    public void sendRequest(HashMap<String, Object> data) {
        // form json object with username and password or facebook id
        Long user_id = (Long) data.get("user_id");
        this.fetch_user_groups(user_id);
        //send to appropriate URL : to be done
        this.handleResponse();
    }

    @Override
    public void handleResponse() {
        // on success take to home page.
        // on failure show password mismatch
        // text.
        HashMap<String, ArrayList> resp = new HashMap<>();
        resp.put("groups", this.user_groups);
        this.listener.onResponseReceived("GroupsRequest", resp);
    }

    @Override
    public void handleError() {
        //common error handler and

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
}
