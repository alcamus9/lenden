package com.example.srinivas.lenden.requests;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by srinivas on 3/19/2016.
 */
public interface AsyncRequestListener {
    public void onResponseReceived(String name, HashMap response);
}
