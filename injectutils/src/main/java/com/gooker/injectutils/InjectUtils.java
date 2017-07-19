package com.gooker.injectutils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


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
//        injectViewEvent2(activity);
        injectViewEvent3(activity);
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

    private static void injectViewEvent3(final Activity activity) {
        final Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (!(annotation instanceof OnClickInject)) {
                    return;
                }
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);

                String callbackMethod = eventBase.callbackMethod();
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();

                try {
                    Method declaredMethod = annotationType.getDeclaredMethod("value");
                    int[] viewIds = (int[]) declaredMethod.invoke(annotation);
                    for (int viewId : viewIds) {
                        if (viewId < 0) {
                            return;
                        }
                        View view = activity.findViewById(viewId);

                        Method setListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                        Map<String, Method> methodMap = new HashMap<>();
                        methodMap.put(callbackMethod, method);
                        InvocationHandler invocationHandler = new ListenerInvocationHandler(activity, methodMap);
                        Object newProxyInstance = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, invocationHandler);
                        setListenerMethod.invoke(view, newProxyInstance);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
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

    private static class ListenerInvocationHandler implements InvocationHandler {
        private Map<String, Method> mMethodMap = null;
        private Object mObject = null;

        public ListenerInvocationHandler(Object o, Map<String, Method> methodMap) {
            this.mObject = o;
            this.mMethodMap = methodMap;
            Log.i("castiel", "打印方法Map：" + methodMap.toString());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            Log.i("castiel", "打印方法name：" + methodName);
            Method mtd = mMethodMap.get(methodName);
            if (null != mtd) {
                Log.e(TAG, mtd.getName());
                if (null != args) {
                    Log.e(TAG, args.length + "" + args);
                } else {
                    Log.e(TAG, "args == null");
                }

                return mtd.invoke(this.mObject, args);
            }
            return method.invoke(this.mObject, args);
        }
    }
}
