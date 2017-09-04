package com.gooker.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {

    private static final String TAG = MessengerService.class.getSimpleName();

    public MessengerService() {
    }


    private Handler mServerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle msgData = msg.getData();
            if (null != msgData) {
                Log.e(TAG, msgData.toString());
                Log.e(TAG, msgData.get("info").toString());
            }


            Message message = mServerHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("info", "Hello This messege is from Server !!");
//            message.obj = bundle;
            message.setData(bundle);

//            message.obj = "Hello This messege is from Server !!";

            try {
                Messenger client = msg.replyTo;
                client.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            ;
        }
    };

    private void toast(String s) {
        Toast.makeText(MessengerService.this, s, Toast.LENGTH_LONG).show();
    }


    private Messenger mServerMessenger = new Messenger(mServerHandler);

    @Override
    public IBinder onBind(Intent intent) {
//         TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");

        return mServerMessenger.getBinder();
    }
}
