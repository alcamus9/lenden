package com.example.srinivas.lenden.requests;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by srinivas on 3/12/2016.
 */
public class ListKeysRequest implements BaseRequest {

    Context appContext;

    public ListKeysRequest(Context context) {
        this.appContext = context;

    }

    private static List<String> keys = Arrays.asList("txnId", "refId", "txnType", "credType",
            "credSubType", "credDataValue", "credDataCode", "credDataKi");

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public String getUrl() {
        return "http://103.14.161.149:12001/UpiService/upi/listKeysService";
    }

    public void read_json() {
        String list_keys_json_string = null;
        try{
            InputStream is =  this.appContext.getAssets().open("list_keys.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            list_keys_json_string = new String(buffer, "utf-8");

            JSONObject list_keys_json = new JSONObject(list_keys_json_string);

            Toast.makeText(this.appContext, list_keys_json.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace(System.err);
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
    }
}
