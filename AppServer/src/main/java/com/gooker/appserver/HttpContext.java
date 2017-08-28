package com.gooker.appserver;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by sczhang on 2017/8/28. 下午8:59
 * Package Name com.gooker.appserver
 * Project Name Inject
 */

public class HttpContext {

    private HashMap<String, String> mHeader = new HashMap<>();
    private Socket mSocket;


    public void addHeader(String name, String value) {
        if (value.contains("\r\n")) {
            value = value.replace("\r\n", "");
        }
        mHeader.put(name, value);
    }

    public String getHeaderValue(String name) {
        return mHeader.get(name);
    }

    public void setContext(Socket socket) {
        this.mSocket = socket;
    }

    public Socket getUndelySocket() {
        return mSocket;
    }
}
