package com.gooker.appserver;

/**
 * desc : Inject com.gooker.appserver
 * Created by : mz on 2017/8/28 16:26.
 */

public class ServerConfig {
    private int port;
    private int maxConnections;

    public ServerConfig(int port, int maxConnections) {
        this.port = port;
        this.maxConnections = maxConnections;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
}
