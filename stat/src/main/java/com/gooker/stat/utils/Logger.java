package com.gooker.stat.utils;

/**
 * desc : Inject com.gooker.stat.utils
 * Created by : mz on 2017/8/3 10:24.
 */

public class Logger {


    private static LogAdapter mLogAdapter;

    public Logger(LogAdapter logAdapter) {
        this.mLogAdapter = logAdapter;
    }

    public void setLogAdapter(LogAdapter logAdapter) {
        this.mLogAdapter = logAdapter;
    }

    public static void i(Object obj) {
        if (null != mLogAdapter) {
            mLogAdapter.i(obj);
        }
    }

    public static void d(Object obj) {
        if (null != mLogAdapter) {
            mLogAdapter.d(obj);
        }
    }

    public static void e(Object obj) {
        if (null != mLogAdapter) {
            mLogAdapter.e(obj);
        }
    }
}
