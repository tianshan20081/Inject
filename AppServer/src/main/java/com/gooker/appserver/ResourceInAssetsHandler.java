package com.gooker.appserver;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ResourceInAssetsHandler implements IUrlResourceHandle {
    private static final String TAG = "ResourceInAssetsHandler";
    private String mAcceptPrefix = "/assets/";
    private Context mContext;

    public ResourceInAssetsHandler(Context context) {
        mContext = context;
    }

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(mAcceptPrefix);
    }


    @Override
    public void handle(String resourceUrl, HttpContext httpContext) throws IOException {
        int startIndex = mAcceptPrefix.length();
        String assetsPath = resourceUrl.substring(startIndex);
        Log.d(TAG, "assetsPath: " + assetsPath);

        InputStream inputStream = mContext.getAssets().open(assetsPath);
        byte[] rawData = StreamUtils.readRawFromStream(inputStream);
        inputStream.close();

        // write datas to outputstream
        OutputStream outputStream = httpContext.getUndelySocket().getOutputStream();
        PrintStream printWriter = new PrintStream(outputStream);

        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Length:" + rawData.length);
        Log.d(TAG, "Content-Length:" + rawData.length);

        if (assetsPath.endsWith(".html")) {
            printWriter.println("Content-Type:text/html");
        } else if (assetsPath.endsWith(".js")) {
            printWriter.println("Content-Type:text/js");
        } else if (assetsPath.endsWith(".css")) {
            printWriter.println("Content-Type:text/css");
        } else if (assetsPath.endsWith(".jpg")) {
            printWriter.println("Content-Type:text/jpg");
        } else if (assetsPath.endsWith(".png")) {
            printWriter.println("Content-Type:text/png");
        }

        printWriter.println();

        printWriter.write(rawData);

    }
}