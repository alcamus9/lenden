package com.example.srinivas.lenden;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.srinivas.lenden.objects.Bill;

import java.util.ArrayList;

public class Utilities {

    public static long randBetween(long min, long max) {
        return min + (long) (Math.random()*(max-min));
    }

    public static boolean isValidEmail(CharSequence target) {
        if(TextUtils.isEmpty(target)) {
            return false;
        } else  {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
