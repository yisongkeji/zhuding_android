package com.foreseers.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.activity.FortunetellingOutlineActivity;
import com.foreseers.chat.bean.LifeBookUserBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/22
 */
public class FortunetellingUserAdapter extends BaseQuickAdapter<LifeBookUserBean.DataBean, BaseViewHolder> {

    private Context context;

    public FortunetellingUserAdapter(Context context, List<LifeBookUserBean.DataBean> data) {
        super(R.layout.item_expandable_lv1, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final LifeBookUserBean.DataBean item) {

        holder.setText(R.id.text_name, item.getName())
                .setText(R.id.text_time, item.getDate());

        if (holder.getAdapterPosition() == 0) {
            holder.getView(R.id.right)
                    .setVisibility(View.GONE);
        } else {
            holder.getView(R.id.right)
                    .setVisibility(View.VISIBLE);
        }

        holder.getView(R.id.content)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, FortunetellingOutlineActivity.class);
                        intent.putExtra("lifeuserid", item.getLifeuserid() + "");
                        intent.putExtra("name", item.getName() + "");
                        context.startActivity(intent);
                    }
                });

        holder.getView(R.id.right)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OkGo.<String>post(Urls.Url_DeletelifeUser).tag(this)
                                .params("userid", PreferenceManager.getUserId(context))
                                .params("lifeuserid", item.getLifeuserid() + "")
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        Gson gson = new Gson();
                                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                                        if (loginBean.getStatus()
                                                .equals("success")) {
                                            int pos = holder.getAdapterPosition();
                                            remove(pos);
                                        }
                                    }
                                });
                    }
                });
    }
}
