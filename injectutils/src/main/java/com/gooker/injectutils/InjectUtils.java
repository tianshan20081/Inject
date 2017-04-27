package com.gooker.injectutils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by sczhang on 2017/4/4. 下午7:45
 * Package Name com.gooker.injectutils
 * Project Name Inject
 */

public class InjectUtils {


    public static final String TAG = InjectUtils.class.getSimpleName();

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
//        injectViewEvent(activity);
        injectViewEvent2(activity);
    }

    private static void injectViewEvent(final Activity activity) {
        final Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick click = method.getAnnotation(OnClick.class);
                method.setAccessible(true);
                int[] ids = click.value();
                for (int id : ids) {
                    if (id < 0) {
                        return;
                    }
                    final View view = activity.findViewById(id);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(activity, view);
                            } catch (IllegalAccessException e) {
                                Log.e(TAG, e.getMessage());
                            } catch (InvocationTargetException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    });
//                view.setOnClickListener();
                }
            }

        }
    }

    private static void injectViewEvent2(final Activity activity) {
        final Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick click = method.getAnnotation(OnClick.class);
                method.setAccessible(true);
                int[] ids = click.value();
                for (int id : ids) {
                    if (id < 0) {
                        return;
                    }
                    try {
                        Object proxy = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(),
                                new Class<?>[]{View.OnClickListener.class}, new MyInvocationHandler(activity, method)
                        );
                        View view = (View) clazz.getMethod("findViewById", int.class).invoke(activity, id);
                        Method onClickListener = view.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                        onClickListener.invoke(view, proxy);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
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
                if (viewId < 0) {
                    return;
                }
                try {
//                    View view = activity.findViewById(viewId);
                    View view = (View) clazz.getMethod("findViewById", Integer.TYPE).invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
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
            if (layoutId < 0) {
                return;
            }
//            activity.setContentView(layoutId);
            try {
                clazz.getMethod("setContentView", Integer.TYPE).invoke(activity, layoutId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MyInvocationHandler implements InvocationHandler {
        private Object target = null;
        private Method method = null;

        public MyInvocationHandler(Object target, Method method) {
            this.target = target;
            this.method = method;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.method.invoke(target, args);
        }
    }
}
