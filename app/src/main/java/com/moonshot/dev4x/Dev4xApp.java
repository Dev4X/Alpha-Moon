package com.moonshot.dev4x;

import android.app.Application;
import android.content.Context;

/**
 * Created by adrian on 27/11/15.
 */
public class Dev4xApp extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

}
