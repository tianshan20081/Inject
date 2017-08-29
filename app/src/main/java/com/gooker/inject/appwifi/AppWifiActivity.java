package com.gooker.inject.appwifi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gooker.appserver.HelloIUrlResourceHandle;
import com.gooker.appserver.NetUtils;
import com.gooker.appserver.ResourceInAssetsHandler;
import com.gooker.appserver.ServerConfig;
import com.gooker.appserver.SimpleAppServer;
import com.gooker.appserver.UploadIUrlResourceHandle;
import com.gooker.inject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppWifiActivity extends Activity {

    @BindView(R.id.tv_start_wifi)
    TextView tvStartWifi;
    @BindView(R.id.tv_local_ip)
    TextView tvLocalIp;
    @BindView(R.id.tv_stop_wifi)
    TextView tvStopWifi;
    private SimpleAppServer mSimpleAppServer;
    private ServerConfig mServerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_app_wifi);
        ButterKnife.bind(this);

        int port = 8088;
        mServerConfig = new ServerConfig(port, 50);
        mSimpleAppServer = new SimpleAppServer(mServerConfig);

        mSimpleAppServer.addResourceHandle(new HelloIUrlResourceHandle());
        mSimpleAppServer.addResourceHandle(new UploadIUrlResourceHandle());
        mSimpleAppServer.addResourceHandle(new ResourceInAssetsHandler(AppWifiActivity.this));

        tvLocalIp.setText(NetUtils.getWifiLocalIpAddress(AppWifiActivity.this) + ":" + port);
    }

    @OnClick(R.id.tv_start_wifi)
    void startAppWifi() {
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
