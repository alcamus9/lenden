package com.example.srinivas.lenden.database;

import android.content.Context;
import android.widget.Toast;

import com.example.srinivas.lenden.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by srinivas on 3/20/2016.
 */
public class UserDBHelper {
    DBAdapter dbAdapter;
    Context context;
    private ArrayList<User> users_list;
    private HashMap<Long, User> users_map;

    public UserDBHelper(DBAdapter adapter, Context context) {
        this.dbAdapter = adapter;
        this.context = context;
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

    private String extractFromArray(JSONObject j, String key) {
        ArrayList<Long> returnArray = new ArrayList<>();
        String returnString = "";
        JSONArray jsonArray = null;
        try {
            jsonArray = j.getJSONArray(key);

            for(int i=0; i < jsonArray.length() - 1; i++) {
                returnArray.add(jsonArray.getLong(i));
                returnString += Long.toString(jsonArray.getLong(i));
                returnString += ", ";
            }
            returnString += Long.toString(jsonArray.getLong(jsonArray.length()-1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnString;
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

    public void initUsersDataBase() {
        boolean noUsers = true;

        String users_data_string;
        JSONArray users_data_json;
        this.users_list = this.dbAdapter.getAllUsers();
        if (this.users_list.size() != 0) {
            noUsers = false;
        } else {
            noUsers = true;

            try {
                InputStream is = this.context.getAssets().open("users_data.json");

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
                    this.users_list.add(u);
                }
            }catch(IOException e){
                System.out.println("error");
                e.printStackTrace(System.err);
            }catch(JSONException e){
                e.printStackTrace(System.err);
            }
            this.dbAdapter.insertUsers(this.users_list);
        }
        this.populateMap();
    }

    public void refreshUsers() {
        this.users_list = dbAdapter.getAllUsers();
        this.populateMap();
    }

    private void populateMap() {
        this.users_map = new HashMap<Long, User>();
        for (int i=0; i < this.users_list.size(); i++) {
            User u = this.users_list.get(i);
            this.users_map.put(u.getId(), u);
        }
    }

    public User fetchUser(String user_name, String password) {
        for(int i=0; i<this.users_list.size(); i++) {
            User u = this.users_list.get(i);
                if(u.get_user_name().equals(user_name)) {
                    if(u.getPassword().equals(password)) {
                        return u;
                    } else {
                        return null;
                    }
                } else {
                    continue;
                }
            }
        return null;
    }

    public User fetchUser(String fb_profile_id) {
        for(int i=0; i<this.users_list.size(); i++) {
            User u = this.users_list.get(i);
                if(u.getFb_profile_id().equals(fb_profile_id)) {
                    return u;
                } else {
                    continue;
                }
        }
        return null;
    }

    public ArrayList<User> getOtherUsers() {
        //Returns users with a reduced set of details
        ArrayList<User> reducedUsers = new ArrayList<User>();
        for(int i=0; i < this.users_list.size(); i++) {
            User u = this.users_list.get(i);
            reducedUsers.add(new User(u.getId(), u.get_user_name(), u.getName(),
                     u.getEmail(), u.getPhone_number()));
        }
        return reducedUsers;
    }

    public User fetchUser(Long id) {
        return this.users_map.get(id);
    }
}
