package com.tasklistdemo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by upenp on 7/23/2017.
 */

public class TaskApplication extends Application {
    public static final String TAG = TaskApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
