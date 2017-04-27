package com.gooker.injectutils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sczhang on 2017/4/4. 下午9:41
 * Package Name com.gooker.injectutils
 * Project Name Inject
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    int value()[];
}
