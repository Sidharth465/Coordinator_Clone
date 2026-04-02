package io.siddharth.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.domain.model.TempModelAssessment;
import io.siddharth.myapplication.domain.model.TempModelImage;
import io.siddharth.myapplication.domain.model.TempModelStudent;
import io.siddharth.myapplication.util.DB_Path;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int SPLASH_TIME_OUT = 1500;
    Context mContext = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }

        // Storage permission logic for different Android versions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // For Android 13+, check READ_MEDIA_IMAGES correctly
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
            }
        }

        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        } else {
            proceedToApp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allGranted = false;
                        break;
                    }
                }
            } else {
                allGranted = false;
            }

            if (allGranted) {
                proceedToApp();
            } else {
                // If it's a modern device, we'll proceed anyway but with a less alarming toast
                Toast.makeText(this, "Permissions handled. Starting app...", Toast.LENGTH_SHORT).show();
                proceedToApp();
            }
        }
    }

    private void proceedToApp() {
        dbCopy();
        startActivityFun();
    }

    public void dbCopy() {
        // Note: Direct access to /sdcard/ is restricted on Android 11+ (API 30+)
        File sdcard = Environment.getExternalStorageDirectory();
        File dbfile = new File(sdcard, "HSG_CO/HSG_APP_DATABASE");
        
        if (dbfile.exists()) {
            try {
                DB_Path dbp = new DB_Path();
                ArrayList<TempModelStudent> tempModelStudentArrayList = (ArrayList<TempModelStudent>) dbp.getDataStudentData(mContext);
                if (tempModelStudentArrayList != null) dbp.insertStudent(mContext, tempModelStudentArrayList);

                ArrayList<TempModelAssessment> tempModelAssessmentArrayList = dbp.getDataStudentAssessmentData(mContext);
                if (tempModelAssessmentArrayList != null) dbp.insertAssessment(mContext, tempModelAssessmentArrayList);

                ArrayList<TempModelImage> tempModelImageArrayList = dbp.getDataImageData(mContext);
                if (tempModelImageArrayList != null) dbp.insertImage(mContext, tempModelImageArrayList);

                dbfile.delete();
            } catch (Exception e) {
                Log.e(TAG, "Database copy failed: " + e.getMessage());
            }
        }
    }

    public void startActivityFun() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
    }
}