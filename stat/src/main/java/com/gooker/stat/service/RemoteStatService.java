package com.gooker.stat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.gooker.stat.aidl.IStatServcie;

public class RemoteStatService extends Service {


    private final IStatServcie.Stub mBinder = new IStatServcie.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void upload(String content) throws RemoteException {

        }
    };

    public RemoteStatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
