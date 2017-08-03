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

        }

        @Override
        public void upload(String content) throws RemoteException {

        }

        @Override
        public void saveData(String content) throws RemoteException {

        }

        @Override
        public void init() throws RemoteException {
            Logger.e("---------init--------");
        }
    };

    public RemoteStatService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            switch (intent.getAction()) {
                case _INIT_DEVICE:
                    try {
                        mBinder.init();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
