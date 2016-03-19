package com.example.srinivas.lenden.dbrequests;

import android.content.Context;
import android.net.TrafficStats;

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
public class TransactionsRequest implements BaseDBRequest {


    AsyncRequestListener listener;
    Context appContext;
    String users_data_string;
    JSONArray users_data_json;
    HashMap<String, Transaction> transactionsMap;
    ArrayList<Transaction> transactions;
    ArrayList<Transaction> user_transactions;
    User user;

    public TransactionsRequest(AsyncRequestListener listener, Context context) {
        this.listener = listener;
        this.appContext = context;
        this.init_request();
        this.user_transactions = new ArrayList<>();
    }

    @Override
    public List<String> getKeys() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    public Transaction makeTranFromJSON(JSONObject json) {
        Transaction t = null;
        try {
            Long id = json.getLong("id");
            String description = json.getString("description");
            Long sourceId = json.getLong("sourceId");
            Long destId = json.getLong("destID");
            Double amount = json.getDouble("amount");
            String status = json.getString("status");

            t = new Transaction(id, description, sourceId, destId, amount, status);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return t;
    }


    public void init_request() {
        this.transactionsMap = new HashMap<>();
        this.transactions = new ArrayList<Transaction>();

        try{
            InputStream is =  this.appContext.getAssets().open("transactions_data.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            users_data_string = new String(buffer, "utf-8");
            JSONObject data_json = new JSONObject(users_data_string);
            users_data_json = data_json.getJSONArray("transactions");

            for (int i=0; i < users_data_json.length(); i++) {
                JSONObject j = users_data_json.getJSONObject(i);
                Transaction t = this.makeTranFromJSON(j);
                this.transactions.add(t);
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetch_user_transactions(Long user_id) {
        for(int i=0; i<this.transactions.size(); i++) {
            Transaction t = this.transactions.get(i);
            if(t.getSourceId().equals(user_id) || t.getDestId().equals(user_id)) {
                this.user_transactions.add(t);
            }
        }
    }

    @Override
    public void sendRequest(HashMap<String, Object> data) {
        // form json object with username and password or facebook id
        Long user_id = (Long) data.get("user_id");
        this.fetch_user_transactions(user_id);
        //send to appropriate URL : to be done
        this.handleResponse();
    }

    @Override
    public void handleResponse() {
        // on success take to home page.
        // on failure show password mismatch
        // text.
        HashMap<String, ArrayList> resp = new HashMap<>();
        resp.put("transactions", this.user_transactions);
        this.listener.onResponseReceived("TransactionsRequest", resp);
    }

    @Override
    public void handleError() {
        //common error handler and

    }
}


