package io.siddharth.myapplication.util;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ankitchaudhary on 27/10/17.
 */

public class SharedPref {

    private static SharedPref sharedPref = null;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String PREFS_IP = "SERVER_IP";


    public SharedPref() {
    }

    public static synchronized SharedPref getInstance() {
        if (sharedPref == null) {
            sharedPref = new SharedPref();
        }
        return sharedPref;
    }

    public boolean setIpAddress() {

        return true;
    }

    public String getIpAddress() {
        sharedpreferences = MyApplication.getContext().getSharedPreferences(PREFS_IP, Context.MODE_PRIVATE); //1

        return null;
    }
}
