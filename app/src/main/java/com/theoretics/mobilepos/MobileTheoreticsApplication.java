package com.theoretics.mobilepos;

import android.app.Application;
import android.content.Context;

import com.theoretics.mobilepos.util.GLOBALS;

/**
 * Created by Administrator on 2018/2/1.
 */

public class MobileTheoreticsApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    public static Context getContext() {
        return context;
    }
}
