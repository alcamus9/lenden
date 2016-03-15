package com.example.srinivas.lenden.requests;

import java.util.HashMap;

/**
 * Created by srinivas on 3/12/2016.
 */
public class DetailsObject {
    //details object which is used in some com.example.srinivas.lenden.requests
    private String ifsc, acType, acNum, iin, uIDNum, mmId, mobNum, cardNum;

    public HashMap<String, String> getDetails(){
        HashMap<String, String> returnDict = new HashMap<String, String>();
        returnDict.put("ifsc", this.ifsc);
        returnDict.put("acType", this.acType);
        returnDict.put("acNum", this.acNum);
        returnDict.put("iin", this.iin);
        returnDict.put("uIdNum", this.uIDNum);
        returnDict.put("mmId", this.mmId);
        returnDict.put("mobNum", this.mobNum);
        returnDict.put("cardNum", this.cardNum);

        return returnDict;
    }


}
