package com.gooker.libs.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * desc : Inject com.gooker.views
 * Created by : mz on 2017/8/25 09:20.
 */

public class TouchPullView extends View {
    private Paint mCirclePaint;
    private int mCircleRadius = 100;
    private float mProgress;

    private int mViewMaxHeight = 500;

    public TouchPullView(Context context) {
        super(context);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.BLACK);
        mCirclePaint.setDither(true);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth = width, measureHeight = height;
        int requireWidth = mCircleRadius * 2 + getPaddingLeft() + getPaddingRight();
        int requireHeight = (int) (getPaddingBottom() + getPaddingTop() + mProgress * mViewMaxHeight + 0.5);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
//                measuredWidth = Math.min(width, requireWidth);
                measuredWidth = width;
                break;
            case MeasureSpec.UNSPECIFIED:
                measuredWidth = requireWidth;
                break;

        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                measureHeight = Math.min(height, requireHeight);
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                measureHeight = height;
                break;

        }


        setMeasuredDimension(measuredWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, mCircleRadius, mCirclePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setProgress(float progress) {
        Log.e("TouchPullView\t", progress + "");
        this.mProgress = progress;

        requestLayout();
    }
}
