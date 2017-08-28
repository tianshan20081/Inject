package com.gooker.appserver;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
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

    private Set<IUrlResourceHandle> urlResourceHandles;

    private ExecutorService mThreadPoolExecutor;

    public SimpleAppServer(ServerConfig serverConfig) {
        this.mServerConfig = serverConfig;
        mThreadPoolExecutor = Executors.newCachedThreadPool();
        urlResourceHandles = new HashSet<>();

        urlResourceHandles.add(new StaticIUrlResourceHandle());
        urlResourceHandles.add(new UploadIUrlResourceHandle());
    }

    public void startServer() {
        isRunning = true;
        doServerWorking();
    }

    private void doServerWorking() {

        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(mServerConfig.getPort());
            mServerSocket = new ServerSocket();
            mServerSocket.bind(inetSocketAddress);
            while (isRunning) {
                final Socket remotePeer = mServerSocket.accept();
                mThreadPoolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("msg", remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemoteSocket(remotePeer);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAcceptRemoteSocket(Socket remotePeer) {
        try {
            HttpContext httpContext = new HttpContext();
            httpContext.setContext(remotePeer);
            InputStream nis = remotePeer.getInputStream();

            String resourceUrl = StreamUtils.readLine(nis).split(" ")[1];
            Log.e("url", resourceUrl);

            String headLine = null;
            while ((headLine = StreamUtils.readLine(nis)) != null) {
                if (headLine.equals("\r\n")) {
                    break;
                }
                Log.e("msg", headLine);
                String[] split = headLine.split(": ");
                if (split.length > 1) {
                    httpContext.addHeader(split[0], split[1]);
                }
            }


            for (IUrlResourceHandle urlResourceHandle : urlResourceHandles) {
                if (urlResourceHandle.accept(resourceUrl)) {
                    urlResourceHandle.handle(resourceUrl, httpContext);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopServer() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
    }
}
