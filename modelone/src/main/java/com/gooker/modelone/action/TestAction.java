package com.gooker.modelone.action;

import com.gooker.router.annotation.Action;

/**
 * desc :
 * Created by : mz on 2017/7/1 11:01.
 */

@Action(value = "action/testOne")
public class TestAction {

    public String test() {
        return "com.gooker.modelone.action.TestAction";
    }

}
