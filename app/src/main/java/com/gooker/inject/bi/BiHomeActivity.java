package com.gooker.inject.bi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gooker.inject.R;
import com.gooker.inject.utils.LogUtils;
import com.gooker.stat.event.AppEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BiHomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_bi_click)
    TextView tvBiClick;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_home);
        ButterKnife.bind(this);


        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppEvent.onEvent("App on click Evemt");
            }
        }, 1000);
        mCompositeDisposable = new CompositeDisposable();
    }

    @OnClick(R.id.tv_bi_click)
    void biClick() {

        AppEvent.onEvent("App on click Evemt");


        Disposable subscribe = Observable.interval(5000, 500, TimeUnit.MICROSECONDS)
                .take(5_000)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        AppEvent.onEvent("App on click Evemt\t" + aLong);
                    }
                });

        mCompositeDisposable.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("------onDestroy--------------------");
        mCompositeDisposable.clear();
    }
}