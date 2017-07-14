package com.gooker.tagview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * desc :
 * Created by : mz on 2017/7/14 14:27.
 */

public class TagLayout extends ViewGroup {

    public static final String TAG = TagLayout.class.getSimpleName();
    private int maxLine = 10;


    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //父控件传进来的宽度和高度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //如果当前ViewGroup的宽高为wrap_content的情况
        int width = 0;//自己测量的 宽度
        int height = 0;//自己测量的高度
        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        int line = 1;
        //获取子view的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
//            int childWidth = lp.width + lp.leftMargin + lp.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
//            int childHeight = lp.height + lp.topMargin + lp.bottomMargin;
            //换行时候
            if (lineWidth + childWidth > sizeWidth) {
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
                line++;
            } else {//不换行情况
                //叠加行宽
                lineWidth += childWidth;
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (line > maxLine) {
                break;
            } else if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        //wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int lineMaxWidth = getWidth();
        int maxHeight = getHeight();
        int lineWidth = 0;
        int lineCount = 0;
        int lineHeight = 0;
        int viewHeight;
        int viewWidth;
        for (int i = 0; i < childCount; i++) {
            View tagView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) tagView.getLayoutParams();
            viewHeight = lineHeight = tagView.getMeasuredHeight();
            viewWidth = tagView.getMeasuredWidth();
            if (lineWidth + viewWidth > lineMaxWidth) {
                //换行
                lineCount += 1;
                lineWidth = 0;
            }
            int left = lineWidth + lp.leftMargin;
            int top = lineCount * lineHeight + lp.topMargin;
            int right = lineWidth + viewWidth + lp.rightMargin;
            int bottom = lineCount * lineHeight + viewHeight + lp.bottomMargin;
            tagView.layout(left, top, right, bottom);
            lineWidth += viewWidth;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    public void addTags() {
        String tag = "我们教育孩子，往往不是因为爱，而是出于害怕";
        for (int i = 0; i < 1; i++) {
            String t = tag.substring(0, new Random().nextInt(tag.length()));
            final TextView tv = new TextView(getContext());
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.GREEN);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            float measureText = tv.getPaint().measureText(t) + 0.5f;
            MarginLayoutParams layoutParams = new MarginLayoutParams((int) measureText, LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 20;
            layoutParams.topMargin = 20;
            layoutParams.bottomMargin = 20;
            tv.setText(t);
            tv.setPadding(10, 10, 10, 10);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), tv.getText(), Toast.LENGTH_LONG).show();
                }
            });
            tv.setMaxLines(1);
            tv.setMaxEms(5);
            addView(tv, layoutParams);
            Log.e(TAG, "addTags" + t);
        }
    }

    public void clearTags() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
    }
}
