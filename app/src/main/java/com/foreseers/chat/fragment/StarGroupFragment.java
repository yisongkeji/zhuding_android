package com.foreseers.chat.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseFragment;
import com.foreseers.chat.util.FontUtils;
import com.foreseers.chat.view.widget.CloseSlideViewPager;
import com.foreseers.chat.view.widget.MyTitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * File description.
 *
 * @author how
 * @date 2019/7/3
 */
public class StarGroupFragment extends BaseFragment {

    Unbinder unbinder;
    //    @BindView(R.id.tablayout) SlidingScaleTabLayout tablayout;
    @BindView(R.id.viewpager) CloseSlideViewPager viewpager;
    @BindView(R.id.stargroup_attention) TextView stargroupAttention;
    @BindView(R.id.stargroup_hot) TextView stargroupHot;
    @BindView(R.id.stargroup_newest) TextView stargroupNewest;
    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;

    private AttentionFragment attentionFragment;
    private HotFragment hotFragment;
    private NewestFragment newestFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    //    private int[] titles= new int[]{R.string.tab1,R.string.tab2,R.string.tab3};
    private String[] titles = new String[]{"关注", "热门", "最新"};
    private Drawable drawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stargroup, container, false);
        unbinder = ButterKnife.bind(this, view);

        attentionFragment = new AttentionFragment();
        hotFragment = new HotFragment();
        newestFragment = new NewestFragment();
        fragments.add(attentionFragment);
        fragments.add(hotFragment);
        fragments.add(newestFragment);

        viewpager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        viewpager.setCurrentItem(1);
        //        tablayout.setViewPager(viewpager);
        //        viewpager.setOffscreenPageLimit(3);
        //        tablayout.setCurrentTab(1);

        RelativeLayout relativeLayout=myTitlebar.getLeftLayout();
        ImageView imageView=relativeLayout.findViewById(R.id.left_image);
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, FontUtils.getInstance().dp2px(getActivity(),10));
        imageView.setLayoutParams(layoutParams);

        RelativeLayout relativeLayout1=myTitlebar.getRightLayout();
        ImageView imageView1=relativeLayout1.findViewById(R.id.right_image);
        RelativeLayout.LayoutParams layoutParams1= (RelativeLayout.LayoutParams) imageView1.getLayoutParams();
        layoutParams1.setMargins(0,0,0,FontUtils.getInstance().dp2px(getActivity(),10));
        imageView1.setLayoutParams(layoutParams1);

        return view;
    }

    @Override
    public void initViews() {
        drawable = getActivity().getResources().getDrawable(R.mipmap.icon_header_btn_09);

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    @OnClick({R.id.stargroup_attention, R.id.stargroup_hot, R.id.stargroup_newest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stargroup_attention:
                stargroupAttention.setTextSize(25);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                stargroupAttention.setCompoundDrawables(null, null, null, drawable);
                stargroupAttention.setCompoundDrawablePadding(-27);

                stargroupHot.setTextSize(20);
                stargroupHot.setCompoundDrawables(null, null, null, null);
                stargroupNewest.setTextSize(20);
                stargroupNewest.setCompoundDrawables(null, null, null, null);
                viewpager.setCurrentItem(0);
                break;
            case R.id.stargroup_hot:
                stargroupHot.setTextSize(25);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                stargroupHot.setCompoundDrawables(null, null, null, drawable);
                stargroupHot.setCompoundDrawablePadding(-27);

                stargroupAttention.setTextSize(20);
                stargroupAttention.setCompoundDrawables(null, null, null, null);
                stargroupNewest.setTextSize(20);
                stargroupNewest.setCompoundDrawables(null, null, null, null);
                viewpager.setCurrentItem(1);
                break;
            case R.id.stargroup_newest:
                stargroupNewest.setTextSize(25);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                stargroupNewest.setCompoundDrawables(null, null, null, drawable);
                stargroupNewest.setCompoundDrawablePadding(-27);

                stargroupHot.setTextSize(20);
                stargroupHot.setCompoundDrawables(null, null, null, null);
                stargroupAttention.setTextSize(20);
                stargroupAttention.setCompoundDrawables(null, null, null, null);
                viewpager.setCurrentItem(2);
                break;
        }
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
            // super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    class MyViewPagerAdapter1 extends PagerAdapter {

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            View view = (View) object;
            return (int) view.getTag();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "标题位置" + position;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TextView textView = new TextView(getActivity());
            textView.setText(getPageTitle(position));
            textView.setTag(position);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
