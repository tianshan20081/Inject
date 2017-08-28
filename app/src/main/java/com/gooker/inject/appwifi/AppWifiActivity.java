package com.gooker.inject.appwifi;

import android.app.Activity;
import android.os.Bundle;

import com.gooker.appserver.ServerConfig;
import com.gooker.appserver.SimpleAppServer;
import com.gooker.inject.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppWifiActivity extends Activity {

    private SimpleAppServer mSimpleAppServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_app_wifi);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_start_wifi)
    void startAppWifi() {
        ServerConfig serverConfig = new ServerConfig(8024, 50);
        mSimpleAppServer = new SimpleAppServer(serverConfig);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mSimpleAppServer.startServer();
            }
        }).start();
    }

    @OnClick(R.id.tv_stop_wifi)
    void stopWifi() {
        mSimpleAppServer.stopServer();
    }

}
