package com.foreseers.chat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
	private boolean START = true;// ������������START�����ж�,ÿ�ε����Ļ��,��Ļֻ�������һ��Բ

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
			canvas.drawCircle(wave.X, wave.Y, wave.radius, wave.paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// �����Ļ�� �뾶��Ϊ0,alpha����Ϊ255
			MyViewBean bean = new MyViewBean();
			bean.radius = 0; // ����� �뾶����Ϊ0
			bean.alpha = MaxAlpha; // alpha��Ϊ���ֵ 255
			bean.width = bean.radius / 8; // ��߿�� �������
			bean.X = (int) event.getX(); // �����Ƶ�Բ��X����
			bean.Y = (int) event.getY(); // �����Ƶ�Բ��Y����
			bean.paint = initPaint(bean.alpha, bean.width);

			if (list.size() == 0) {
				// ������������START�����ж�,ÿ�ε����Ļ��,��Ļֻ�������һ��Բ
				START = true;
			}
			list.add(bean);
			invalidate();
			if (START) {
				handler.sendEmptyMessage(0);
			}

			break;
		}
		return true;
	}

	/**
	 * ��ʼ��paint
	 */
	private Paint initPaint(int alpha, float width) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);// �����
		paint.setStrokeWidth(width);// ��߿��
		paint.setStyle(Paint.Style.STROKE);// Բ��
		paint.setAlpha(alpha);// ͸����
		paint.setColor(Color.BLUE);// ��ɫ
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
			MyViewBean bean = list.get(i);
			if (START == false && bean.alpha == 0) {
				list.remove(i);
				bean.paint = null;
				bean = null;
				continue;
			} else if (START == true) {
				START = false;
			}
			bean.radius += 5;// �뾶ÿ��+5
			bean.alpha -= 10;// ͸����ÿ�μ�10
			if (bean.alpha < 0) {
				// ͸����С��0��ʱ�� ��Ϊ0
				bean.alpha = 0;
			}
			bean.width = bean.radius / 8; // ��߿������Ϊ�뾶��1/4
			bean.paint.setAlpha(bean.alpha);
			bean.paint.setStrokeWidth(bean.width);
		}
	}

}
