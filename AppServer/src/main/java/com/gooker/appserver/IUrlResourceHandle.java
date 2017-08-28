package com.gooker.appserver;

import java.io.IOException;

/**
 * Created by sczhang on 2017/8/28. 下午8:57
 * Package Name com.gooker.appserver
 * Project Name Inject
 */

public interface IUrlResourceHandle {

    public boolean accept(String url);

    public void handle(String resourceUrl, HttpContext httpContext) throws IOException;
}
