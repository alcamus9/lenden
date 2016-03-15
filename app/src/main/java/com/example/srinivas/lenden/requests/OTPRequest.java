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
public class OTPRequest implements BaseRequest {

    Context appContext;

    public OTPRequest(Context context) {
        this.appContext = context;

    }

    private static List<String> keys = Arrays.asList("txnId", "payerAddress","payerName", "mobileNumber",
            "geoCode", "location", "ip", "type", "id", "os", "app", "capability",
            "accountAddressType", "detailsJson");

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public String getUrl() {
        return "http://103.14.161.149:12001/UpiService/upi/otpService";
    }

    public void read_json() {
        String opt_request_string = null;
        try{
            InputStream is =  this.appContext.getAssets().open("otp_request.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            opt_request_string = new String(buffer, "utf-8");

            JSONObject opt_request_json = new JSONObject(opt_request_string);

            Toast.makeText(this.appContext, opt_request_json.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace(System.err);
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
    }


}
