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
public class ReqPayRequest implements BaseRequest {

    Context appContext;

    public ReqPayRequest(Context context) {
        this.appContext = context;
    }
    private static List<String> keys = Arrays.asList("txnId", "txnNote", "refId", "custRefID",
            "payerAddress", "payerName", "mobileNumber", "geoCode", "location", "ip", "type", "id",
            "os", "app", "capability", "payerAcAddressType", "detailsJson", "credType", "credSubType",
            "credDataValue", "credDataCode", "credDataKi", "txnAmount", "payeeAdress", "payeeName",
            "payeeType", "payeeCode", "IdentityId");

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public String getUrl() {
        return "http://103.14.161.149:12001/UpiService/upi/payTransService";
    }

    public void read_json() {
        String req_pay_json_string = null;
        try{
            InputStream is =  this.appContext.getAssets().open("pay_request.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            req_pay_json_string = new String(buffer, "utf-8");

            JSONObject req_pay_json = new JSONObject(req_pay_json_string);

            Toast.makeText(this.appContext, req_pay_json.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace(System.err);
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
    }
}
