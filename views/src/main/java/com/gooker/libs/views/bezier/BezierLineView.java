package com.gooker.libs.views.bezier;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sczhang on 2017/8/26. 下午4:22
 * Package Name com.gooker.libs.views.bezier
 * Project Name Inject
 */

public class BezierLineView extends View {

    private Paint mBezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path mBezierPath = new Path();

    public BezierLineView(Context context) {
        super(context);
        init();
    }

    public BezierLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public BezierLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BezierLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        Path path = mBezierPath;
        path.moveTo(100, 100);

        path.lineTo(300, 300);

        path.quadTo(500, 0, 700, 300);


//        path.rQuadTo();


        Paint paint = mBezierPaint;

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mBezierPath, mBezierPaint);
    }
}
