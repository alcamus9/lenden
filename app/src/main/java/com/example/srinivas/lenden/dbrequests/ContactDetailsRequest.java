package com.example.srinivas.lenden.dbrequests;

/**
 * Created by srinivas on 3/19/2016.
 */

import android.content.Context;

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
public class ContactDetailsRequest extends UserDetailsRequest implements BaseDBRequest {

    ArrayList<User> contacts;
    User user;

    public ContactDetailsRequest(AsyncRequestListener listener, Context context) {
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


    private void fetch_contacts(ArrayList<Long> contactsList) {
        this.contacts = new ArrayList<User>();
        Object[] keys = this.user_details_map.keySet().toArray();
        for (int j=0; j<contactsList.size(); j++) {
            for (int i = 0; i < keys.length; i++) {
                User u = this.user_details_map.get((String) keys[i]);
                if (u.getId() == contactsList.get(j) ) {
                    this.contacts.add(new User(u.getId(), u.getName(),
                             u.get_user_name(), u.getEmail(), u.getPhone_number()));
                }
            }
        }
    }

    @Override
    public void sendRequest(HashMap<String, Object> data) {
        // form json object with username and password or facebook id
        ArrayList<Long> contact_ids = (ArrayList<Long>) data.get("contact_ids");
        this.fetch_contacts(contact_ids);
        //send to appropriate URL : to be done
        this.handleResponse();
    }

    @Override
    public void handleResponse() {
        // on success take to home page.
        // on failure show password mismatch
        // text.
        HashMap<String, ArrayList> resp = new HashMap<>();
        resp.put("contacts", this.contacts);
        this.listener.onResponseReceived("ContactsDetailsRequest", resp);
    }

    @Override
    public void handleError() {
        //common error handler and

    }
}



