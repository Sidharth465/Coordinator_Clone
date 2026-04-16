package io.siddharth.myapplication.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    private static Utils instance;

    public static synchronized Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public void ActivityIndicator(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    public void saveImageFromUri(Context context, Uri uri, String imageName) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            File file = new File(context.getExternalFilesDir(null), imageName);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStudentImagePath(String imageName) {
        // This is a stub, implementation depends on where images are stored
        return "";
    }
}
