package io.siddharth.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import java.io.File;
import java.util.ArrayList;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.domain.model.TempModelAssessment;
import io.siddharth.myapplication.domain.model.TempModelImage;
import io.siddharth.myapplication.domain.model.TempModelStudent;
import io.siddharth.myapplication.util.DB_Path;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 2000;
    Context mContext = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        dbCopy();
        startActivityFun();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            boolean hasStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            boolean hasCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
            boolean hasPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;

            if (hasStorage) {
                dbCopy();
            }

            if (hasStorage && hasCamera && hasPhoneState) {
                startActivityFun();
            } else {
                Toast.makeText(this, "Required permissions denied. Opening login with limited functionality.", Toast.LENGTH_SHORT).show();
                startActivityFun();
            }
        }
    }


    public void dbCopy(){
        File dbfile = new File("/sdcard/HSG_CO/HSG_APP_DATABASE");
        if(dbfile.exists()){
            DB_Path dbp = new DB_Path();
            ArrayList<TempModelStudent> tempModelStudentArrayList = (ArrayList<TempModelStudent>) dbp.getDataStudentData(mContext);
            dbp.insertStudent(mContext, tempModelStudentArrayList);

            ArrayList<TempModelAssessment> tempModelAssessmentArrayList = dbp.getDataStudentAssessmentData(mContext);
            dbp.insertAssessment(mContext, tempModelAssessmentArrayList);

            ArrayList<TempModelImage> tempModelImageArrayList = dbp.getDataImageData(mContext);
            dbp.insertImage(mContext, tempModelImageArrayList);

            dbfile.delete();


        }
    }
    public void startActivityFun() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app Login activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}