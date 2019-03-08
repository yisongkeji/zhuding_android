package com.foreseers.chat.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.BlackBean;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.Urls;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

public class BlackListAdapter extends BaseQuickAdapter<BlackBean.DataBean, BaseViewHolder> {
    Activity context;

    public BlackListAdapter(Activity context, @Nullable List<BlackBean.DataBean> data) {
        super(R.layout.item_black_list, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BlackBean.DataBean item) {


        GlideUtil.glideMatch(context, item.getUserhead(), (ImageView) helper.getView(R.id.img_head));

        helper.setText(R.id.text_name, item.getUsername())
                .setText(R.id.text_time, item.getDate());


//        helper.getView(R.id.layout_item).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new PopWindow.Builder(context)
//                        .setStyle(PopWindow.PopWindowStyle.PopUp)
//                        .addItemAction(new PopItemAction("从黑名单中移除", PopItemAction.PopItemStyle.Normal, new
//                                PopItemAction.OnClickListener() {
//                                    @Override
//                                    public void onClick() {
//
//                                        new Thread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                try {
//                                                    EMClient.getInstance().contactManager().removeUserFromBlackList(item.getUserid() + "");
//                                                } catch (HyphenateException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }).start();
//
//
//                                        OkGo.<String>post(Urls.Url_RemoveBlack).tag(this)
//                                                .params("userid", GetLoginTokenUtil.getUserId(context))
//                                                .params("blackid", item.getUserid() + "")
//                                                .execute(new StringCallback() {
//                                                    @Override
//                                                    public void onSuccess(Response<String> response) {
//                                                        helper.getView(R.id.layout_item).clearAnimation();
//
//                                                    }
//                                                });
//
//
//                                    }
//                                }))
//                        .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel, new
//                                PopItemAction.OnClickListener() {
//                                    @Override
//                                    public void onClick() {
//
//                                    }
//                                }))
//                        .create()
//                        .show();
//
//
//            }
//        });


    }

}
