package com.example.srinivas.lenden;

import com.example.srinivas.lenden.objects.Bill;

import java.util.ArrayList;

/**
 * Created by sushantc on 3/19/16.
 */
public class utilities {

    public static long randBetween(long min, long max) {
        return min + (long) (Math.random()*(max-min));
    }

}
