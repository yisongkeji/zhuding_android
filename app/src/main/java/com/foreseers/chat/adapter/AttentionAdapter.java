package com.foreseers.chat.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.AttentionData;
import com.foreseers.chat.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class AttentionAdapter extends BaseQuickAdapter<AttentionData, BaseViewHolder> {

    private RecyclerView imgRecyclerView;
    private ItemImageListAdapter itemImageListAdapter;
    private GridLayoutManager gridLayoutManager;
//    private List<String> imgList = new ArrayList<>();
    private TextView praise, volume, sharecount;
    private int itemState;
    private Drawable drawable1;
    private Drawable drawable2;
    private Drawable drawable3;

    public AttentionAdapter() {
        super(R.layout.item_startgroup);
//        for (int i = 0; i < 2; i++) {
//            imgList.add("" + i);
//        }
    }

    @Override
    protected void convert(BaseViewHolder helper, AttentionData item) {

        helper.addOnClickListener(R.id.item_stargroup_share);
        helper.addOnClickListener(R.id.item_stargroup_praise);
        helper.addOnClickListener(R.id.item_stargroup_volume);
        helper.addOnClickListener(R.id.item_stargroup_sharecount);
        praise=helper.getView(R.id.item_stargroup_praise);
        volume=helper.getView(R.id.item_stargroup_volume);
        sharecount=helper.getView(R.id.item_stargroup_sharecount);

        if (item.isPraiseState()){
            drawable1 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_01);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            praise.setCompoundDrawables(drawable1, null, null, null);
        }else {
            drawable1 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_02);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            praise.setCompoundDrawables(drawable1, null, null, null);
        }

        if (item.isVolumeState()){
            drawable2 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_03);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            volume.setCompoundDrawables(drawable2, null, null, null);
        }else {
            drawable2 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_04);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            volume.setCompoundDrawables(drawable2, null, null, null);
        }

        if (item.isShareCountState()){
            drawable3 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_06);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            sharecount.setCompoundDrawables(drawable3, null, null, null);
        }else {
            drawable3 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_05);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            sharecount.setCompoundDrawables(drawable3, null, null, null);
        }

        //列表头像
        GlideUtil.glideMatch(mContext, "", (ImageView) helper.getView(R.id.item_stargroup_icon));
        //itemname
//        helper.setText(R.id.item_stargroup_name,"");
        //time
//        helper.setText(R.id.item_stargroup_time,"");
        //日期
//        helper.setText(R.id.item_stargroup_date,"");
        //内容
//        helper.setText(R.id.item_stargroup_content,"");
        //点赞
        helper.setText(R.id.item_stargroup_praise,"77");
        //评论
        helper.setText(R.id.item_stargroup_volume,"77");
        //分享个数
        helper.setText(R.id.item_stargroup_sharecount,"77");

        Log.e("AttentionAdapter", helper.getLayoutPosition() + "=========" + getData().size());
        if (helper.getLayoutPosition() == getData().size() - 1) {
            helper.setGone(R.id.item_stargroup_line, false);
        } else {
            helper.setGone(R.id.item_stargroup_line, true);
        }

        imgRecyclerView = helper.getView(R.id.item_stargroup_imgrv);
        itemImageListAdapter = new ItemImageListAdapter();
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        imgRecyclerView.setHasFixedSize(true);
        imgRecyclerView.setLayoutManager(gridLayoutManager);
        imgRecyclerView.setAdapter(itemImageListAdapter);
        itemImageListAdapter.setNewData(item.getImgList());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder,position);
        }else {
            if (itemState==1) {
                praise = holder.getView(R.id.item_stargroup_praise);
                if (!getData().get(position).isPraiseState()) {
                    getData().get(position).setPraiseState(true);
                    drawable1 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_01);
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    praise.setCompoundDrawables(drawable1, null, null, null);
                } else {
                    getData().get(position).setPraiseState(false);
                    drawable1 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_02);
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    praise.setCompoundDrawables(drawable1, null, null, null);
                }
            }else if (itemState==2){
                volume = holder.getView(R.id.item_stargroup_volume);
                if (!getData().get(position).isVolumeState()) {
                    getData().get(position).setVolumeState(true);
                    drawable2 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_03);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    volume.setCompoundDrawables(drawable2, null, null, null);
                } else {
                    getData().get(position).setVolumeState(false);
                    drawable2 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_04);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    volume.setCompoundDrawables(drawable2, null, null, null);
                }
            }else if (itemState==3){
                sharecount = holder.getView(R.id.item_stargroup_sharecount);
                if (!getData().get(position).isShareCountState()) {
                    getData().get(position).setShareCountState(true);
                    drawable3 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_06);
                    drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
                    sharecount.setCompoundDrawables(drawable3, null, null, null);
                } else {
                    getData().get(position).setShareCountState(false);
                    drawable3 = mContext.getResources().getDrawable(R.mipmap.icon_starcircle_btn_05);
                    drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
                    sharecount.setCompoundDrawables(drawable3, null, null, null);
                }
            }


        }
    }

    public void notifyItem(int state,int position) {
          this.itemState=state;
          notifyItemChanged(position,1);
    }


}
