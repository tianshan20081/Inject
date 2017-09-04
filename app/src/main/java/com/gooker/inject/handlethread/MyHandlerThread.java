package com.gooker.inject.handlethread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * desc : Inject com.gooker.inject.handlethread
 * Created by : mz on 2017/8/29 10:08.
 */

public class MyHandlerThread extends HandlerThread {
    private Handler mHandler;

    public MyHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // process incoming messages here
                // this will run in non-ui/background thread
            }
        };

    }
}
