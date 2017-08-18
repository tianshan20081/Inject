package com.gooker.stat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.gooker.stat.aidl.IStatServcie;
import com.gooker.stat.utils.LogAdapter;
import com.gooker.stat.utils.Logger;

public class RemoteStatService extends Service {

    private static final String _INIT_DEVICE = "_init_device";


    private final IStatServcie.Stub mBinder = new IStatServcie.Stub() {


        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            Logger.e(Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[0]);

            Logger.e("basicTypes------------:");
        }

        @Override
        public void upload(String content) throws RemoteException {
            Logger.e(Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[0]);

            Logger.e("basicTypes------------:");
        }

        @Override
        public void saveData(String content) throws RemoteException {
            Logger.e(Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[0]);
            Logger.e("saveData------------:\t" + content);
        }

        @Override
        public void init() throws RemoteException {
            Logger.e("---------init--------");
        }
    };

    public RemoteStatService() {
        Logger.e("RemoteStatService()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.e("RemoteStatService()\t:onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Logger.e("RemoteStatService()\t:onRebind");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Logger.e("RemoteStatService()\t:onStartCommand");
        return START_REDELIVER_INTENT;
    }
}
