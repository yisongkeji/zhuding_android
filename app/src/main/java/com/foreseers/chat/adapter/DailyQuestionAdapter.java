package com.foreseers.chat.adapter;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.DailyQuestionBean;
import com.foreseers.chat.view.widget.SliderImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DailyQuestionAdapter extends BaseQuickAdapter<DailyQuestionBean, BaseViewHolder> {

    private SliderImageView sliderImageView;
    private RvCanScrollImp rvCanScrollImp;
    private List<Integer> imageList = new ArrayList<>();

    public DailyQuestionAdapter() {
        super(R.layout.daily_question_item_layout);
        imageList.add(R.mipmap.icon_dayask_01);
        imageList.add(R.mipmap.icon_dayask_02);
        imageList.add(R.mipmap.icon_dayask_03);
        imageList.add(R.mipmap.icon_dayask_04);
        imageList.add(R.mipmap.icon_dayask_05);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DailyQuestionBean item) {
        Glide.with(mContext)
                .load(imageList.get(helper.getLayoutPosition()))
                .into((ImageView) helper.getView(R.id.question_iv));
        helper.setText(R.id.question_content, "aaaa");

        SpannableString spannableString = new SpannableString("在文本中添加表情（表情）");
        Drawable drawable = mContext.getResources()
                .getDrawable(R.mipmap.icon_dayask_09);
        drawable.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan = new ImageSpan(drawable);

        Drawable drawable2 = mContext.getResources()
                .getDrawable(R.mipmap.icon_dayask_10);
        drawable2.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan2 = new ImageSpan(drawable2);

        Drawable drawable3 = mContext.getResources()
                .getDrawable(R.mipmap.icon_dayask_11);
        drawable3.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan3 = new ImageSpan(drawable3);
        spannableString.setSpan(imageSpan, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan2, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan3, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.question_answer, spannableString);

        if (item.isSwipe()) {//表示已经滑动过了
            helper.setGone(R.id.open_answer, false);
            helper.setGone(R.id.animation_iv, false);
            helper.setGone(R.id.question_answer,true);
            //            helper.setGone(R.id.item_slide_rv_list_slide, false);
        } else {
            //            helper.setGone(R.id.open_answer, true);
            //            helper.setGone(R.id.item_slide_rv_list_slide, true);
            //            helper.setGone(R.id.animation_iv,true);
        }

        //        final RelativeLayout relativeLayout = helper.getView(R.id.item_rl);
        final ImageView answer = helper.getView(R.id.open_answer);

        Log.e("存储的状态", helper.getLayoutPosition() + "====" + item.isSwipe());
        if (!item.isSwipe()) {
            helper.setOnClickListener(R.id.open_answer, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TranslateAnimation animation = new TranslateAnimation(Animation.ABSOLUTE, answer.getLeft(), Animation.ABSOLUTE, 0f,
                                                                          Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f);
                    animation.setDuration(500);
                    ImageView imageView = helper.getView(R.id.animation_iv);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            item.setSwipe(true);
                            notifyItemChanged(helper.getLayoutPosition());
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });

            helper.setOnTouchListener(R.id.open_answer, new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //                            sliderImageView = helper.getView(R.id.item_slide_rv_list_slide);
                            //
                            //                            Log.e("aaaaaaaaaaaa",
                            //                                  "==========setOnTouchListener" + relativeLayout.getRight() + "=======" +
                            // relativeLayout.getWidth() + "======" +
                            //                                          answer.getLeft());
                            //                            sliderImageView.showDragView();
                            //                            sliderImageView.setOnReleasedListener(new SliderImageView.OnReleasedListener() {
                            //                                @Override
                            //                                public void onReleased() {
                            //                                    item.setSwipe(true);
                            //                                    //                                    notifyDataSetChanged();
                            //                                    notifyItemChanged(helper.getLayoutPosition());
                            //                                }
                            //                            });
                            //                            sliderImageView.setOnLeftValueListener(new SliderImageView.OnLeftValueListener() {
                            //                                @Override
                            //                                public void setLeftValue(boolean isCanRvScroll) {
                            //                                    rvCanScrollImp.setRvCanScroll(isCanRvScroll);
                            //                                }
                            //                            });
                            break;
                    }
                    return false;
                }
            });
        }
    }

    public void setRvCanScrollImp(RvCanScrollImp rvCanScrollImp) {
        this.rvCanScrollImp = rvCanScrollImp;
    }

    public interface RvCanScrollImp {
        void setRvCanScroll(boolean isCanRvScroll);
    }


}
