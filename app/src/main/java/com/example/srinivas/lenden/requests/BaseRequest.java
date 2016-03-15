package com.example.srinivas.lenden.requests;

import java.util.List;

/**
 * Created by srinivas on 3/12/2016.
 */
public interface BaseRequest {

    // method to get the list of keys to be set in the
    // request
    public List<String> getKeys();
    public String getUrl();
}
