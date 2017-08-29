package com.gooker.appserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by sczhang on 2017/8/28. 下午9:07
 * Package Name com.gooker.appserver
 * Project Name Inject
 */

public class HelloIUrlResourceHandle implements IUrlResourceHandle {
    private String match = "/hello/";

    @Override
    public boolean accept(String url) {
        return url.startsWith(match);
    }

    @Override
    public void handle(String resourceUrl, HttpContext httpContext) throws IOException {

        OutputStream os = httpContext.getUndelySocket().getOutputStream();
        PrintStream printWriter = new PrintStream(os);
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println();

        printWriter.println("from resource in assets handler ");

        printWriter.flush();

        printWriter.close();
    }
}
