package com.gooker.inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gooker.injectutils.InjectUtils;
import com.gooker.injectutils.InjectView;
import com.gooker.injectutils.OnClickInject;
import com.gooker.injectutils.SetContentViewId;

@SetContentViewId(R.layout.activity_main)
public class MainActivity extends Activity {

    @InjectView(R.id.tv_hello)
    TextView tv_hello;


//    @OnClick({R.id.tv_hello, R.id.tv_test})
//    private void test(View view) {
//        if (null != tv_hello) {
//            tv_hello.setText("hello" + System.nanoTime() + ":\tViewId" + view.getId());
//        }
//        Toast.makeText(MainActivity.this, "hello" + System.nanoTime() + ":\tViewId" + view.getId(), Toast.LENGTH_LONG).show();
//    }
//
//
//    @OnLongClick({R.id.tv_hello, R.id.tv_test})
//    private void testLong(View view) {
//        if (null != tv_hello) {
//            tv_hello.setText("hello:\t testLong" + System.nanoTime() + ":\tViewId" + view.getId());
//        }
//        Toast.makeText(MainActivity.this, "hello:\ttestLong" + System.nanoTime() + ":\tViewId" + view.getId(), Toast.LENGTH_LONG).show();
//    }

    @OnClickInject({R.id.tv_hello, R.id.tv_test})
    public void testClick(View viewR) {
        if (null != tv_hello) {
            tv_hello.setText("hello:\t testLong" + System.nanoTime() + ":\tViewId");
        }
        Toast.makeText(MainActivity.this, "hello:\ttestLong" + System.nanoTime() + ":\tViewId", Toast.LENGTH_LONG).show();
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
