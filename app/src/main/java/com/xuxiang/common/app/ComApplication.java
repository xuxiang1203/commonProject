package com.xuxiang.common.app;

import android.app.Application;
import android.content.Context;

public class ComApplication extends Application {
    private static Context context;
    private static ComApplication application;

    public static Context getContext() {
        return context;
    }

    public static ComApplication instance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        application = this;
        MyCrashHandler.getInstance().init(this);
        AppConfig.getInstance().init(this);
    }
}
