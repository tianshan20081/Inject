package com.gooker.injectutils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sczhang on 2017/4/4. 下午8:10
 * Package Name com.gooker.injectutils
 * Project Name Inject
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    int value();
}
