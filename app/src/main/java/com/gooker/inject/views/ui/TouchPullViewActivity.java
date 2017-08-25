package com.gooker.inject.views.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.gooker.inject.R;
import com.gooker.libs.views.TouchPullView;

/**
 * desc : Inject com.gooker.inject.views.ui
 * Created by : mz on 2017/8/25 09:26.
 */

public class TouchPullViewActivity extends Activity {
    private TouchPullView mTouchPullView;
    private float mTouchStartX;
    private float mTouchStartY;
    private float mTouchMoveMaxY = 600;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_pull_view);

        findViewById(R.id.ly_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartX = event.getY();
                        mTouchStartY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float touchMoveY = event.getY();
                        float progress = (touchMoveY - mTouchStartY) / mTouchMoveMaxY;
                        mTouchPullView.setProgress(progress > 1 ? 1 : progress);
                        break;
                    case MotionEvent.ACTION_UP:
                        mTouchPullView.setProgress(0);
                        break;

                }
                return false;
            }
        });

        mTouchPullView = (TouchPullView) findViewById(R.id.tpl_pull_view);

    }
}
