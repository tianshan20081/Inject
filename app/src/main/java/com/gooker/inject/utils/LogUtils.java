package com.gooker.inject.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Log工具类
 */
public class LogUtils {

    static {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("app")
                .methodOffset(5)
                .methodCount(4)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static void i(Object obj) {
        if (Utils.isDebug()) {
            if (null != obj) {
                Logger.i(obj.toString());
            }
        }
    }

    public static void d(Object obj) {
        if (Utils.isDebug()) {
            if (null != obj) {
                Logger.d(obj.toString());
            }
        }
    }

    public static void e(Object obj) {
        if (Utils.isDebug()) {
            if (null != obj) {
                Logger.e(obj.toString());
            }
        }
    }

}
