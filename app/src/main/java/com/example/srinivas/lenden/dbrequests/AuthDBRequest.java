package com.example.srinivas.lenden.dbrequests;

import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;
import android.widget.Toast;

import com.example.srinivas.lenden.database.DBAdapter;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.lenden.requests.AsyncRequestListener;
import com.facebook.internal.BoltsMeasurementEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by srinivas on 3/18/2016.
 */
public class AuthDBRequest implements BaseDBRequest {

    AsyncRequestListener listener;
    Context appContext;
    String users_data_string;
    JSONArray users_data_json;
    HashMap<String, User> user_details_map;
    Long userId;
    DBAdapter dbhelper;

    public  AuthDBRequest(AsyncRequestListener listener, Context context) {
        this.listener = listener;
        this.appContext = context;
        this.dbhelper = new DBAdapter(this.appContext);
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
            ArrayList<Long> contacts = new ArrayList<>();
            ArrayList<Long> groups = new ArrayList<>();

            user_obj = new User(id, user_name, password, name, email_id, phone_number,
                    fb_profile_id, contacts, groups);
        } catch(JSONException e) {
            e.printStackTrace(System.err);
        }
        return  user_obj;
    }


    public void init_request() {
        this.user_details_map = new HashMap<>();
        boolean noUsers = true;
        //for now, read json file, search and authenticate

            ArrayList<User> users_list = dbhelper.getAllUsers();
            if (false) {
                Toast.makeText(this.appContext, "Empty db. Have to insert", Toast.LENGTH_LONG).show();
                noUsers = false;
                for(int i=0; i < users_list.size(); i++) {

                }
            } else {
                Toast.makeText(this.appContext, "DB Not empty.", Toast.LENGTH_LONG).show();
                noUsers = true;

                try {
                InputStream is = this.appContext.getAssets().open("users_data.json");

                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                users_data_string = new String(buffer, "utf-8");
                JSONObject data_json = new JSONObject(users_data_string);
                users_data_json = data_json.getJSONArray("users");

                for (int i = 0; i < users_data_json.length(); i++) {
                    JSONObject j = users_data_json.getJSONObject(i);
                    User u = this.makeUserFromJSON(j);
                    user_details_map.put(j.getString("id"), u);
                    users_list.add(u);
                }
            }catch(IOException e){
                System.out.println("error");
                e.printStackTrace(System.err);
            }catch(JSONException e){
                e.printStackTrace(System.err);
            }
        }
        ArrayList<User> users = new ArrayList<>();
        Collection<User> tempUsers = user_details_map.values();
        User[] users_array = new User[tempUsers.size()];
        tempUsers.toArray(users_array);
        for (int i=0; i<tempUsers.size();i++) {
            users.add(users_array[i]);
        }

        if(users.size() > 0 && noUsers) {
            dbhelper.insertUsers(users);
        }
    }

    private Long check_user(HashMap<String, Object> data, boolean user_name_check) {
        Object[] keys = this.user_details_map.keySet().toArray();
        for(int i=0; i<keys.length; i++) {
            User u = this.user_details_map.get((String)keys[i]);
            if(user_name_check) {
                if(u.get_user_name().equals(data.get("user_name"))) {
                    if(u.getPassword().equals(data.get("password"))) {
                        return u.getId();
                    }
                    else {
                        return null;
                    }
                } else {
                    continue;
                }
            } else {
                if(u.getFb_profile_id().equals(data.get("fb_profile_id"))) {
                    return u.getId();
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    @Override
    public void sendRequest(HashMap<String, Object> data) {
        // form json object with username and password or facebook id
        String user_name = (String) data.get("user_name");
        if (user_name != null) {
            this.userId = this.check_user(data, true);
        } else {
            this.userId = this.check_user(data, false);
        }
        //send to appropriate URL : to be done
        this.handleResponse();
    }

    @Override
    public void handleResponse() {
        // on success take to home page.
        // on failure show password mismatch
        // text.
        HashMap<String, Long> resp = new HashMap<>();
        resp.put("user_id", this.userId);
        this.listener.onResponseReceived("AuthDBRequest", resp);
    }

    @Override
    public void handleError() {
        //common error handler and

    }
}
