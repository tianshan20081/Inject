package com.gooker.stat.event;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.gooker.stat.aidl.IStatServcie;
import com.gooker.stat.service.RemoteStatService;
import com.gooker.stat.utils.Logger;

/**
 * desc : Inject com.gooker.stat.event
 * Created by : mz on 2017/8/18 10:28.
 */

public class ClickEventHelper {

    private static ClickEventHelper _INSTANCE;
    private final Context mContext;

    private IStatServcie mIStatServcie;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIStatServcie = IStatServcie.Stub.asInterface(service);
            Logger.e("ClickEventHelper:\t" + "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIStatServcie = null;
            Logger.e("ClickEventHelper:\t" + "onServiceDisconnected");
        }
    };


    public ClickEventHelper(Context context) {
        this.mContext = context;
        Intent intent = new Intent(mContext, RemoteStatService.class);
//        intent.setAction("");
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public static synchronized ClickEventHelper getInstance(Context context) {
        if (_INSTANCE == null) {
            _INSTANCE = new ClickEventHelper(context);
        }
        return _INSTANCE;
    }

    public void sendEventData(String data) {
        Logger.e("sendEventData" + data);
        try {
            mIStatServcie.saveData(data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
