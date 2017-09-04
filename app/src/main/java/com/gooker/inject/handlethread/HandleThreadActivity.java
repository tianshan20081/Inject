package com.gooker.inject.handlethread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.gooker.inject.R;
import com.gooker.inject.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandleThreadActivity extends Activity {


    private static final int _MSG_UPDATE_INFO = 0X111;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    private HandlerThread mHandlerThread;
    private Handler mCheckMsgHandler;

    /**
     * 用于更新 UI 界面数据显示
     */
    private Handler mHandler = new Handler();
    private boolean isUpdateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_thread);
        ButterKnife.bind(this);

        initBackThread();

    }

    private void initBackThread() {

        mHandlerThread = new HandlerThread("HandleThreadActivity");
        mHandlerThread.start();

        mCheckMsgHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                checkForUpdate();
                if (isUpdateInfo) {
                    mCheckMsgHandler.sendEmptyMessageDelayed(_MSG_UPDATE_INFO, 1000);
                }
            }
        };


    }


    @OnClick(R.id.tv_start)
    void start() {

        isUpdateInfo = true;
        mCheckMsgHandler.sendEmptyMessage(_MSG_UPDATE_INFO);
    }


    @OnClick(R.id.tv_stop)
    void stop() {

        isUpdateInfo = false;
        mCheckMsgHandler.removeCallbacksAndMessages(null);
    }


    @Override
    protected void onPause() {
        super.onPause();
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(_MSG_UPDATE_INFO);
    }

    /**
     * 执行耗时操作，在子线程中
     */
    private void checkForUpdate() {
        try {
            Thread.sleep(1000);
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    tvMsg.setText("update info \t" + System.nanoTime());
                }
            });
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
