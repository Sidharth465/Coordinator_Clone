package io.siddharth.myapplication.util;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public  void ActivityIndicator(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

    }
}
