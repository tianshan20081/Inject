package com.gooker.inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gooker.injectutils.InjectUtils;
import com.gooker.injectutils.InjectView;
import com.gooker.injectutils.OnClick;
import com.gooker.injectutils.SetContentViewId;

@SetContentViewId(R.layout.activity_main)
public class MainActivity extends Activity {

    @InjectView(R.id.tv_hello)
    TextView tv_hello;


    @OnClick(R.id.tv_hello)
    void test(View view) {
        if (null != tv_hello) {
            tv_hello.setText("hello" + System.nanoTime());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);

//        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
//
//            }
//        });
    }
}
