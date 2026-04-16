package io.siddharth.myapplication.util;

import android.app.Application;
import android.content.Context;
import com.bosphere.filelogger.FL;
import com.bosphere.filelogger.FLConfig;
import com.bosphere.filelogger.FLConst;
import java.io.File;

public class MyApplication extends Application {

    private static MyApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;

        // Initialize Logger using Scoped Storage (Internal Files Dir)
        // Environment.getExternalStorageDirectory() is deprecated and restricted in 2026
        File logDir = new File(getExternalFilesDir(null), "logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        FL.init(new FLConfig.Builder(this)
                .logToFile(true)
                .dir(logDir)
                .retentionPolicy(FLConst.RetentionPolicy.FILE_COUNT)
                .build());
        FL.setEnabled(true);
    }

    public static synchronized MyApplication getAppInstance() {
        return appInstance;
    }

    public static Context getContext() {
        return appInstance.getApplicationContext();
    }
}