package com.gooker.injectutils;

/**
 * desc :
 * Created by : mz on 2017/4/27 16:44.
 */

public @interface CastielEventBase {

    String listenerSetter();

    Class<?> listenerType();

    String callbackMethod();
}
