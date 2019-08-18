package com.noorapp.noor.Utility;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import java.util.Locale;

/**
 * Created by snail on 17/2/16.
 */
public class App extends Application {
    private static Context mContext;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
