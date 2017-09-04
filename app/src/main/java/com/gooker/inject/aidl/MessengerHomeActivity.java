package com.gooker.inject.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gooker.aidl.MessengerService;
import com.gooker.inject.R;
import com.gooker.stat.utils.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessengerHomeActivity extends AppCompatActivity {


    private volatile boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_home);
        ButterKnife.bind(this);
    }


    private Handler mClientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle msgData = msg.getData();
            if (null != msgData) {
                Logger.e(msgData.toString());
                Logger.e(msgData.get("info").toString());
                toast("MessengerHomeActivity" + msgData.get("info").toString());
            }


        }
    };

    private Messenger mClientMessenger = new Messenger(mClientHandler);

    private Messenger mServiceMessenger;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected = true;
            toast("onServiceConnected !!! ");

            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
            toast("onServiceDisconnected !!! ");

            mServiceMessenger = null;
        }
    };


    @OnClick(R.id.tv_bind_service)
    void bindService() {

        if (isConnected) {
            toast("Service Have ");
            return;

        }
        Intent intent = new Intent(MessengerHomeActivity.this, MessengerService.class);

        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);

    }

    private void toast(String messege) {
        Toast.makeText(MessengerHomeActivity.this, messege, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.tv_send_messeng)
    void sendMsg() {

        if (null != mServiceMessenger && isConnected) {

            Message message = mClientHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("info", "Hello this message is send from Client !!!");
            message.setData(bundle);
//            message.obj = bundle;
//            message.obj = "Hello this message is send from Client !!!";


            String name = "";
            try {
                message.replyTo = mClientMessenger;

                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
