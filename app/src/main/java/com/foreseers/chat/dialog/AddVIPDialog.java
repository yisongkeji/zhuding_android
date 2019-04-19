package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.bean.BannerData;
import com.foreseers.chat.view.CustomViewHolder;
import com.foreseers.chat.view.VipCustomViewHolder;
import com.ms.banner.Banner;
import com.ms.banner.Transformer;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddVIPDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.layout_vip1) LinearLayout layoutVip1;
    @BindView(R.id.layout_vip2) LinearLayout layoutVip2;
    @BindView(R.id.layout_vip3) LinearLayout layoutVip3;
    @BindView(R.id.button_vip) Button buttonVip;
    @BindView(R.id.text_day1) TextView textDay1;
    @BindView(R.id.text_price360) TextView textPrice360;
    @BindView(R.id.text_price90) TextView textPrice90;
    @BindView(R.id.text_price30) TextView textPrice30;
    @BindView(R.id.text_1) TextView text1;
    @BindView(R.id.text_day2) TextView textDay2;
    @BindView(R.id.text_2) TextView text2;
    @BindView(R.id.text_day3) TextView textDay3;
    @BindView(R.id.text_3) TextView text3;
    @BindView(R.id.banner) Banner banner;
    private Context context;
    private LeaveMyDialogListener listener;
    private Map<String, String> map;
    private List<BannerData> bannerList = new ArrayList<>();

    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public AddVIPDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddVIPDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
        this.map = map;
    }

    public void upData(Map<String, String> map) {

        textPrice360.setText(map.get("360"));
        textPrice90.setText(map.get("90"));
        textPrice30.setText(map.get("30"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_add_vip);
        ButterKnife.bind(this);
        layoutVip1.setOnClickListener(this);
        layoutVip2.setOnClickListener(this);
        layoutVip3.setOnClickListener(this);
        buttonVip.setOnClickListener(this);
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_05, context.getResources().getString(R.string.foreseers_special_vip_id),
                                      context.getResources().getString(R.string.foreseers_catch_you), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_01, context.getResources().getString(R.string.foreseers_ten_per_day),
                                      context.getResources().getString(R.string.foreseers_look_TA), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_02, context.getResources().getString(R.string.foreseers_infinate_priends_num),
                                      context.getResources().getString(R.string.foreseers_follow_your_heart), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_03, context.getResources().getString(R.string.foreseers_who_see_you),
                                      context.getResources().getString(R.string.foreseers_crush_on_you), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_04, context.getResources().getString(R.string.foreseers_who_see_you_photo),
                                      context.getResources().getString(R.string.foreseers_immediately_TA), false));

        banner.setAutoPlay(true)
                .setPages(bannerList, new HolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new VipCustomViewHolder();
                    }
                })
                .setBannerAnimation(Transformer.Scale)
                .setDelayTime(4000)
                .start();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.layout_vip1:
                listener.onClick(view);
                layoutVip1.setBackgroundResource(R.drawable.background_vip2);
                layoutVip2.setBackgroundResource(R.drawable.background_vip3);
                layoutVip3.setBackgroundResource(R.drawable.background_vip3);

                textDay1.setTextColor(context.getResources()
                                              .getColor(R.color.colorAccent));
                text1.setTextColor(context.getResources()
                                           .getColor(R.color.colorAccent));
                textPrice360.setTextColor(context.getResources()
                                                  .getColor(R.color.colorAccent));

                textDay2.setTextColor(context.getResources()
                                              .getColor(R.color.colorGray));
                text2.setTextColor(context.getResources()
                                           .getColor(R.color.colorGray));
                textPrice90.setTextColor(context.getResources()
                                                 .getColor(R.color.colorGray));
                textDay3.setTextColor(context.getResources()
                                              .getColor(R.color.colorGray));
                text3.setTextColor(context.getResources()
                                           .getColor(R.color.colorGray));
                textPrice30.setTextColor(context.getResources()
                                                 .getColor(R.color.colorGray));

                break;
            case R.id.layout_vip2:
                listener.onClick(view);
                layoutVip1.setBackgroundResource(R.drawable.background_vip3);
                layoutVip2.setBackgroundResource(R.drawable.background_vip2);
                layoutVip3.setBackgroundResource(R.drawable.background_vip3);
                textDay2.setTextColor(context.getResources()
                                              .getColor(R.color.colorAccent));
                text2.setTextColor(context.getResources()
                                           .getColor(R.color.colorAccent));
                textPrice90.setTextColor(context.getResources()
                                                 .getColor(R.color.colorAccent));

                textDay1.setTextColor(context.getResources()
                                              .getColor(R.color.colorGray));
                text1.setTextColor(context.getResources()
                                           .getColor(R.color.colorGray));
                textPrice360.setTextColor(context.getResources()
                                                  .getColor(R.color.colorGray));
                textDay3.setTextColor(context.getResources()
                                              .getColor(R.color.colorGray));
                text3.setTextColor(context.getResources()
                                           .getColor(R.color.colorGray));
                textPrice30.setTextColor(context.getResources()
                                                 .getColor(R.color.colorGray));
                break;
            case R.id.layout_vip3:
                listener.onClick(view);
                layoutVip1.setBackgroundResource(R.drawable.background_vip3);
                layoutVip2.setBackgroundResource(R.drawable.background_vip3);
                layoutVip3.setBackgroundResource(R.drawable.background_vip2);

                textDay3.setTextColor(context.getResources()
                                              .getColor(R.color.colorAccent));
                text3.setTextColor(context.getResources()
                                           .getColor(R.color.colorAccent));
                textPrice30.setTextColor(context.getResources()
                                                 .getColor(R.color.colorAccent));

                textDay1.setTextColor(context.getResources()
                                              .getColor(R.color.colorGray));
                text1.setTextColor(context.getResources()
                                           .getColor(R.color.colorGray));
                textPrice360.setTextColor(context.getResources()
                                                  .getColor(R.color.colorGray));
                textDay2.setTextColor(context.getResources()
                                              .getColor(R.color.colorGray));
                text2.setTextColor(context.getResources()
                                           .getColor(R.color.colorGray));
                textPrice90.setTextColor(context.getResources()
                                                 .getColor(R.color.colorGray));
                break;
            case R.id.button_vip:
                listener.onClick(view);
                bannerList.clear();
                banner.stopAutoPlay();
                break;
        }
    }
}
