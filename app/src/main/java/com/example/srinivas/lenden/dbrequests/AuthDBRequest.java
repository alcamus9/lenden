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
    Long userId;
    DBAdapter dbhelper;

    public  AuthDBRequest(AsyncRequestListener listener, Context context) {
        this.listener = listener;
        this.appContext = context;
        this.dbhelper = new DBAdapter(this.appContext);
    }

    @Override
    public List<String> getKeys() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    private Long check_user(HashMap<String, Object> data, boolean user_name_check) {
        User u = null;
        if(user_name_check) {
            u = dbhelper.userHelper.fetchUser((String) data.get("user_name"),
                                                   (String) data.get("password"));
        } else {
            u = dbhelper.userHelper.fetchUser((String) data.get("fb_profile_id"));
        }
        return (u == null) ? null : u.getId();
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
