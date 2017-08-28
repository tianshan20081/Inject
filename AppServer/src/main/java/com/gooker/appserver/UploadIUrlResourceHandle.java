package com.gooker.appserver;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by sczhang on 2017/8/28. 下午9:08
 * Package Name com.gooker.appserver
 * Project Name Inject
 */

public class UploadIUrlResourceHandle implements IUrlResourceHandle {
    private String match = "/upload/";

    @Override
    public boolean accept(String url) {
        return url.startsWith(match);
    }

    @Override
    public void handle(String resourceUrl, HttpContext httpContext) {

        try {
            String tempPath = "/mnt/sdcard/Download/test_jpg.png";
            File file = new File(tempPath);
            if (file.exists()) {
                file.delete();
            }
            String contentLength = httpContext.getHeaderValue("Content-Length");
            long totalLength = Long.parseLong(contentLength);

            InputStream nis = httpContext.getUndelySocket().getInputStream();
            FileOutputStream fos = new FileOutputStream(tempPath);

            byte[] buffer = new byte[1024 * 100];
            int n = 0;
            long nLength = totalLength;

            Log.e("upload", "start upload---------- !!!");
            while (nLength > 0 && (n = nis.read(buffer)) > 0) {
                fos.write(buffer, 0, n);
                nLength = nLength - n;
            }
            fos.flush();
            fos.close();

            Log.e("upload", "upload finished !!!");


            OutputStream os = httpContext.getUndelySocket().getOutputStream();
            PrintStream printWriter = new PrintStream(os);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println();

            printWriter.println("upload file success !!! ");

            printWriter.flush();

            printWriter.close();

        } catch (Exception e) {
            Log.e("error", e.getMessage());

        }

    }
}
