package com.gooker.inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gooker.inject.bi.BiHomeActivity;
import com.gooker.modelone.action.TestAction;

import butterknife.ButterKnife;
import butterknife.OnClick;

//@SetContentViewId(R.layout.activity_main)
public class MainActivity extends Activity {

//    @InjectView(R.id.tv_hello)
//    TextView tv_hello;


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

    @OnClick(R.id.tv_test)
    void testClick() {
        Toast.makeText(MainActivity.this, "hello:\ttestLong" + System.nanoTime() + ":\tViewId", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, BiHomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TestAction ta = new TestAction();
        System.err.println("====================================================================================================");
        System.err.println(ta.test());
        System.err.println("====================================================================================================");
//        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
//
//            }
//        });

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, BiHomeActivity.class));
            }
        }, 1000);
    }

}

