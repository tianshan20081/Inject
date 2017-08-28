package com.gooker.appserver;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * desc : Inject com.gooker.appserver
 * Created by : mz on 2017/8/28 16:25.
 */

public class SimpleAppServer {


    private final ServerConfig mServerConfig;
    private boolean isRunning;
    private ServerSocket mServerSocket;

    private ExecutorService mThreadPoolExecutor = Executors.newCachedThreadPool();

    public SimpleAppServer(ServerConfig serverConfig) {
        this.mServerConfig = serverConfig;
    }

    public void startServer() {
        isRunning = true;
        doServerWorking();
    }

    private void doServerWorking() {

        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(mServerConfig.getPort());
            Log.e("ip", inetSocketAddress.getAddress().getHostAddress());
            Log.e("ip", inetSocketAddress.getPort() + "");
            mServerSocket = new ServerSocket();
            mServerSocket.bind(inetSocketAddress);
            while (isRunning) {
                final Socket accept = mServerSocket.accept();
                mThreadPoolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String msg = StreamUtils.readLine(accept.getInputStream());
                            Log.e("msg", msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSocketInfo(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        int data;
        byte[] b = new byte[1024];
        try {
            while ((data = inputStream.read(b)) != -1) {
                stringBuilder.append(new String(b));
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void stopServer() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
    }
}
