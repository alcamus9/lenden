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
public class ListAccountRequest implements BaseRequest {
    Context appContext;

    public ListAccountRequest(Context context) {
        this.appContext = context;
    }

    private static List<String> keys = Arrays.asList("txnId", "payerAddress", "payerName", "linkType",
            "accountAddressType", "linkValue", "detailsJson", "credType", "credSubType", "credDataValue",
            "credDataCode", "credDataKi");

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public String getUrl() {
        return "http://103.14.161.149:12001/UpiService/upi/listAccountBankService";
    }

    public void read_json() {
        String list_account_string = null;
        try{
            InputStream is =  this.appContext.getAssets().open("list_account.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            list_account_string = new String(buffer, "utf-8");

            JSONObject list_accounts_json = new JSONObject(list_account_string);

            Toast.makeText(this.appContext, list_accounts_json.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace(System.err);
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
    }
}
