package com.foreseers.chat.view;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.foreseers.chat.R;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * @author ñ���ڲ�ס����
 *
 * @DATE 2016/5/6
 *
 */
public class MyView extends View {

    private List<MyViewBean> list = null;
    private int MaxAlpha = 255;// ���͸����
    private int time = 4;
    private boolean START = true;// ������������START�����ж�,ÿ�ε����Ļ��,��Ļֻ�������һ��Բ
    private Bitmap mBitmap;
    private Rect mSrcRect, mDestRect;

    private List<Bitmap> bitmapList = new ArrayList<>();
    private MyViewBean bean;


    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        list = new ArrayList<MyViewBean>();
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
        for (int i = 0; i < list.size(); i++) {
            MyViewBean wave = list.get(i);
//            canvas.drawCircle(wave.X, wave.Y, wave.radius, wave.paint);
            canvas.drawBitmap(wave.bitmap, mSrcRect, wave.mDestRect, wave.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.chupingfankui_00001));
                bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.chupingfankui_00002));
                bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.chupingfankui_00003));
                bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.chupingfankui_00004));
                bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.chupingfankui_00005));
                MyViewBean bean = new MyViewBean();
                bean.alpha = MaxAlpha;
                bean.time = 4;

                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chupingfankui_00001);

                bean.X = (int) event.getX(); // �����Ƶ�Բ��X����
                bean.Y = (int) event.getY(); // �����Ƶ�Բ��Y����
                bean.bitmap = mBitmap;
                bean.paint = initPaint();

                mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                mDestRect = new Rect(bean.X-(mBitmap.getWidth()/2),  bean.Y-(mBitmap.getHeight()/2), bean.X+(mBitmap.getWidth()/2), bean.Y+ (mBitmap.getHeight()/2));
                bean.mDestRect=mDestRect;

                if (list.size() == 0) {

                    START = true;
                }
                list.add(bean);
                invalidate();
                if (START) {
                    handler.sendEmptyMessage(0);
                }

                break;
        }
        return false;
    }

    /**
     * ��ʼ��paint
     */
    private Paint initPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        return paint;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Refresh();
                    invalidate();
                    if (list != null && list.size() > 0) {
                        handler.sendEmptyMessageDelayed(0, 50);// ÿ50���뷢��
                    }
                    break;

                default:
                    break;
            }
        }

    };

    /***
     * ˢ��
     */
    private void Refresh() {
        for (int i = 0; i < list.size(); i++) {
            bean = list.get(i);
            if (START == false && bean.alpha == 0) {
                list.remove(i);
                bean.paint = null;
                bean = null;
                continue;
            } else if (START == true) {
                START = false;
            }
            bean.time -= 1;
            switch (bean.time) {
                case 3:
                    bean.bitmap = bitmapList.get(0);
                    break;
                case 2:
                    bean.bitmap = bitmapList.get(1);
                    break;

                case 1:
                    bean.bitmap = bitmapList.get(2);
                    break;
                case 0:
                    bean.bitmap = bitmapList.get(3);
                    break;
            }
            if (time==3){
                bean.alpha=128;
            }

            if (bean.time < 0) {
                bean.alpha = 0;
            }
            bean.paint.setAlpha(bean.alpha);

        }
    }

}
