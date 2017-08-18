package com.gooker.stat.event;

import android.app.Activity;
import android.content.Context;

import com.gooker.stat.utils.LogAdapter;
import com.gooker.stat.utils.Logger;

/**
 * desc : Inject com.gooker.stat.event
 * Created by : mz on 2017/8/3 10:28.
 */

public class AppEvent {
    private static Context mContext;
    private static Logger mLogger;
    private static ClickEventHelper mClickEventHelper;

    public static void init(Context context) {
        mContext = context;
        mClickEventHelper = ClickEventHelper.getInstance(mContext);
    }

    public static void setLogAdapter(LogAdapter logAdapter) {
        if (null != logAdapter) {
            mLogger = new Logger(logAdapter);
        }
    }


    public static void onResume(Activity activity) {
        if (null != activity) {

        }
    }

    public static void onStart(Activity activity) {

    }

    public static void onStop(Activity activity) {

    }

    public static void onEvent(String data) {
        Logger.e(data);
        mClickEventHelper.sendEventData(data);
    }
}
