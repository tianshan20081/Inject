package com.gooker.inject.views.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gooker.inject.R;
import com.gooker.libs.views.bezier.BezierLineView;

/**
 * Created by sczhang on 2017/8/26. 下午4:29
 * Package Name com.gooker.inject.views.ui
 * Project Name Inject
 */

public class BezierLineActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_line_view);


        BezierLineView bezierLineView = (BezierLineView) findViewById(R.id.blv_bezier_line);

    }
}
