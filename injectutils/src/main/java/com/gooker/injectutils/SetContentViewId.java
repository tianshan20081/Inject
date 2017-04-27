package com.gooker.injectutils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sczhang on 2017/4/4. 下午7:46
 * Package Name com.gooker.injectutils
 * Project Name Inject
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SetContentViewId {
    public int value();
}
