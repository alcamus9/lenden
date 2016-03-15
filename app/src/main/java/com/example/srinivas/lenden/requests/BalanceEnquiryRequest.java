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
public class BalanceEnquiryRequest implements BaseRequest {
    Context appContext;

    public  BalanceEnquiryRequest(Context context) {
        this.appContext = context;
    }

    private static List<String> keys = Arrays.asList("txnId", "refId", "payerAddress", "payerName",
            "mobileNumber", "geoCode", "location", "ip", "type", "id", "os", "app", "capability",
            "accountAddressType", "detailsJson", "credType", "credSubType",
            "credDataValue", "credDataCode", "credDataKi");

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public String getUrl() {
        return "http://103.14.161.149:12001/UpiService/upi/balanceEnquiry";
    }

    public void read_json() {
        String balance_enquiry_string = null;
        try{
            InputStream is =  this.appContext.getAssets().open("balance_enquiry.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            balance_enquiry_string = new String(buffer, "utf-8");

            JSONObject balance_enquiry_json = new JSONObject(balance_enquiry_string);

            Toast.makeText(this.appContext, balance_enquiry_json.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace(System.err);
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
    }
}
