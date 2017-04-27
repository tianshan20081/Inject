package com.gooker.injectutils;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by sczhang on 2017/4/4. 下午7:45
 * Package Name com.gooker.injectutils
 * Project Name Inject
 */

public class InjectUtils {

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
        injectViewEvent(activity);
    }

    private static void injectViewEvent(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            OnClick click = method.getAnnotation(OnClick.class);
            int[] ids = click.value();
            for (int id : ids) {
                View view = activity.findViewById(id);
//                view.setOnClickListener();
            }
        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (null != injectView) {
                int viewId = injectView.value();
                View view = activity.findViewById(viewId);
                try {
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        SetContentViewId annotation = clazz.getAnnotation(SetContentViewId.class);
        if (null != annotation) {
            int layoutId = annotation.value();
            activity.setContentView(layoutId);
        }
    }
}
