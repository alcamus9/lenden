package com.example.srinivas.lenden.dbrequests;

import java.util.HashMap;
import java.util.List;

/**
 * Created by srinivas on 3/18/2016.
 */
public interface BaseDBRequest {
    public List<String> getKeys();
    public String getUrl();
    public void sendRequest(HashMap<String, Object> data);

    public void handleResponse();
    public void handleError();

}
