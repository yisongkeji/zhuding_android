package chat.foreseers.com.foreseers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chat.foreseers.com.foreseers.fragment.ChatFragment;
import chat.foreseers.com.foreseers.fragment.FriendFragment;
import chat.foreseers.com.foreseers.fragment.MatchFragment;
import chat.foreseers.com.foreseers.fragment.MyFragment;
import chat.foreseers.com.foreseers.fragment.ShopFragment;
import me.yokeyword.fragmentation.SupportActivity;


public class MainActivity extends SupportActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initData();
        initListener();
    }



    private void initData() {
//        聊天
        ChatFragment chatFragment = new ChatFragment();
        mFragmentList.add(chatFragment);

//        朋友
        FriendFragment friendFragment = new FriendFragment();
        mFragmentList.add(friendFragment);


//        匹配
        MatchFragment matchFragment = new MatchFragment();
        mFragmentList.add(matchFragment);


//        商店
        ShopFragment shopFragment = new ShopFragment();
        mFragmentList.add(shopFragment);


//        個人
        MyFragment myFragment = new MyFragment();
        mFragmentList.add(myFragment);


        changeFragment(2);
        mBottomBarLayout.setCurrentItem(2);
    }

    private void initListener() {

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int
                    currentPosition) {
                changeFragment(currentPosition);
            }
        });


    }

    private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
    }


}
