package com.foreseers.chat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/***
 *
 * @author ñ���ڲ�ס����
 *
 * @DATE 2016/5/6
 *
 */
public class MyView extends View {


    private Paint mRedPaint;
    private int mRadius, mMaxRaduis = 70;
    private int mEvnetX, mEvnetY;
    private int mAlpha;
    private boolean start = false;

    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initPaint();
    }


    /**
     * MyView��С
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mEvnetX, mEvnetY, mRadius, mRedPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (start == false) {
                    Log.i("", "------------ACTION_DOWN");
                    start = true;
                    mRadius = 0;
                    mAlpha=255;
                    mEvnetX = (int) event.getX();
                    mEvnetY = (int) event.getY();
                    mRedPaint.setAlpha(mAlpha);
                    handler.sendEmptyMessage(0);
                }

                break;
        }
        return false;
    }

    /**
     * ��ʼ��paint
     */
    private void initPaint() {

        mRedPaint = new Paint();
        //        mRedPaint.setColor(Color.parseColor("#dcdcdc"));
        mRedPaint.setColor(Color.WHITE);
        mRedPaint.setAntiAlias(false);
        mRedPaint.setDither(true);
//        BlurMaskFilter maskFilter = new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER);
        //        mRedPaint.setMaskFilter(maskFilter);
        mRedPaint.setShadowLayer(10, 0, 0, Color.GRAY);
        setLayerType(LAYER_TYPE_SOFTWARE, mRedPaint);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (mRadius < mMaxRaduis) {
                        mRadius += 10;
                        mAlpha-=25;
                        mRedPaint.setAlpha(mAlpha);
                        handler.sendEmptyMessageDelayed(0, 20);// ÿ50���뷢��
                    } else {
                        mRedPaint.setAlpha(0);
                        start = false;
                    }
                    invalidate();
                    break;

                default:
                    break;
            }
        }

    };




}
