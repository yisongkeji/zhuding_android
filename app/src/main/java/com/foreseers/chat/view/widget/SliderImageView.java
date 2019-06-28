package com.foreseers.chat.view.widget;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by Ocean on 2019/6/22.
 */

public class SliderImageView extends LinearLayout {

    private ViewDragHelper viewDragHelper;
    private View child;
    private Point childPosition = new Point();
    private Point childEndPosition = new Point();
    private OnReleasedListener onReleasedListener;
    private OnLeftValueListener onLeftValueListener;

    private int oldX;

    private int offsetRightX = 140;

    Handler handler = new Handler();

    public SliderImageView(Context context) {
        super(context);
    }

    public SliderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new MyViewDragCallBack());
    }


    public class MyViewDragCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            oldX = 0;
            return child == child;
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            oldX = child.getWidth() - offsetRightX - left;
            Log.i("", "==========left:" + left);
            if (onLeftValueListener != null) {
                onLeftValueListener.setLeftValue(false);
            }
            if (left > 0) {
                return left;
            } else {
                return 0;
            }
        }


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            child.layout(left, child.getTop(), child.getWidth() + left, child.getBottom());
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (onLeftValueListener != null) {
                onLeftValueListener.setLeftValue(true);
            }
            if (oldX > getWidth() / 3) {
                //当滑动的位置超过了delview的一半的时候那么自动滑动到完全覆盖状态,高度不动
                viewDragHelper.settleCapturedViewAt(childEndPosition.x, childEndPosition.y);

                invalidate();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onReleasedListener.onReleased();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("", "ooooooooo==before===" + child.getLeft() + "=====" + child.getTop() + "=====" + child.getRight() + "=====" + child.getBottom());
                                child.layout(child.getLeft() + child.getWidth(), 0, child.getRight() + child.getWidth(), child.getBottom() - child.getTop());
                                Log.i("", "ooooooooo===after==" + child.getLeft() + "=====" + child.getTop() + "=====" + child.getRight() + "=====" + child.getBottom());
                            }
                        }, 300);

                    }
                }, 400);

            } else {
                //当滑动的位置不超过了delview的一半的时候那么还原到delview完全显示状态,高度不动
                viewDragHelper.settleCapturedViewAt(childPosition.x, childPosition.y);
                invalidate();
            }


            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

    }


  /*  public void setTouchable(boolean isTouch) {
        if (isTouch) {
            //新建viewDragHelper ,viewGroup, 灵敏度，回调(子view的移动)
            viewDragHelper = ViewDragHelper.create(this, 1.0f,new MyViewDragCallBack());
        } else {
            viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    return false;
                }
            });
        }
    }*/

    public void showDragView() {
        child.layout(child.getLeft() - offsetRightX, child.getTop(), child.getRight() - offsetRightX, child.getBottom());
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child = getChildAt(0);

    }


    @Override  //viewDragHelper拦截事件
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //定位一开始的坐标
        childPosition.x = child.getWidth();
        childPosition.y = child.getTop();

        //滑动成功后定位坐标
        childEndPosition.x = child.getLeft();
        childEndPosition.y = child.getTop();
        Log.i("", "=====before=====" + child.getLeft() + "=====" + child.getTop() + "=====" + child.getRight() + "=====" + child.getBottom());

        Log.i("", "=====child.getWidth()=====" + child.getWidth());

        child.layout(child.getLeft() + child.getWidth(), child.getTop(), child.getRight() + child.getWidth(), child.getBottom());
        Log.i("", "=====after=====" + child.getLeft() + "=====" + child.getTop() + "=====" + child.getRight() + "=====" + child.getBottom());
    }

    //为了实现弹性滑动
    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void setOnReleasedListener(OnReleasedListener onReleasedListener) {
        this.onReleasedListener = onReleasedListener;
    }

    public void setOnLeftValueListener(OnLeftValueListener onLeftValueListener) {
        this.onLeftValueListener = onLeftValueListener;
    }

    public interface OnReleasedListener {
        void onReleased();
    }

    public interface OnLeftValueListener {
        void setLeftValue(boolean isCanRvScroll);
    }


}
