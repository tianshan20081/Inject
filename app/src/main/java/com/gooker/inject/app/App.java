package com.gooker.inject.app;

import android.app.Application;

import com.gooker.inject.utils.LogUtils;
import com.gooker.stat.event.AppEvent;
import com.gooker.stat.utils.LogAdapter;

/**
 * desc : Inject com.gooker.inject.app
 * Created by : mz on 2017/8/3 10:28.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppEvent.init(this);
        AppEvent.setLogAdapter(new LogAdapter() {
            @Override
            public void i(Object obj) {
                LogUtils.i(obj);
            }

            @Override
            public void d(Object obj) {
                LogUtils.i(obj);
            }

            @Override
            public void e(Object obj) {
                LogUtils.i(obj);
            }
        });
    }
}
