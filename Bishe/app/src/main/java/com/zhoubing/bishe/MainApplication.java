package com.zhoubing.bishe;

import android.app.Application;

import com.fengmap.android.FMMapSDK;

/**
 * Created by dell on 2018/5/3.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        FMMapSDK.init(this);
        super.onCreate();
    }
}
