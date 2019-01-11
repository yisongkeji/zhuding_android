package chat.foreseers.com.foreseers.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.activity.UserDetailsActivity;
import chat.foreseers.com.foreseers.bean.ListOfPopularBean;

public class ListOfPopularAdapter extends BaseQuickAdapter<ListOfPopularBean,BaseViewHolder> {
    private Activity context;

    public ListOfPopularAdapter(Activity context, @Nullable List<ListOfPopularBean> data) {
        super(R.layout.item_list_of_popular, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ListOfPopularBean item) {

        baseViewHolder.getView(R.id.item_popular).findViewById(R.id.item_popular).setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,UserDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }



}
