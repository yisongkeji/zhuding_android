package chat.foreseers.com.foreseers.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.global.BaseMainFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 聊天
 */
public class ChatFragment extends SupportFragment implements RadioGroup.OnCheckedChangeListener {


    Unbinder unbinder;
    @BindView(R.id.chat_radio_friend)
    RadioButton chatRadioFriend;
    @BindView(R.id.chat_radio_temporary_session)
    RadioButton chatRadioTemporarySession;
    @BindView(R.id.chat_radio_group)
    RadioGroup chatRadioGroup;
    @BindView(R.id.chat_layout)
    FrameLayout chatLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction, fragmentTransactionShow;

    private ChatConversationFragment chatConversationFragment;
    private ChatFriendFragment chatFriendFragment;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();
        return view;
    }


    public void initViews() {
        chatRadioGroup.setOnCheckedChangeListener(this);

        fragmentManager = getChildFragmentManager();
        fragmentTransactionShow = fragmentManager.beginTransaction();
        if (chatFriendFragment != null) {
            chatFriendFragment = new ChatFriendFragment();
            fragmentTransactionShow.add(R.id.chat_layout, chatFriendFragment);
//            fragmentTransactionShow.show(chatFriendFragment);
        } else {
            chatFriendFragment = new ChatFriendFragment();
            fragmentTransactionShow.add(R.id.chat_layout, chatFriendFragment);
        }

        fragmentTransactionShow.commit();
        chatRadioFriend.setChecked(true);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (checkedId) {
            case R.id.chat_radio_friend:
                chatRadioFriend.setAlpha(1);
                chatRadioTemporarySession.setAlpha((float) 0.5);
                if (chatFriendFragment != null) {
//                    fragmentTransaction.show(chatFriendFragment);
                    chatFriendFragment = new ChatFriendFragment();
                    fragmentTransaction.add(R.id.chat_layout, chatFriendFragment);
                } else {
                    chatFriendFragment = new ChatFriendFragment();
                    fragmentTransaction.add(R.id.chat_layout, chatFriendFragment);
                }

                break;
            case R.id.chat_radio_temporary_session:
                chatRadioTemporarySession.setAlpha(1);
                chatRadioFriend.setAlpha((float) 0.5);
                if (chatConversationFragment != null) {
//                    fragmentTransaction.show(chatConversationFragment);
                    chatConversationFragment = new ChatConversationFragment();
                    fragmentTransaction.add(R.id.chat_layout, chatConversationFragment);
                } else {
                    chatConversationFragment = new ChatConversationFragment();
                    fragmentTransaction.add(R.id.chat_layout, chatConversationFragment);

                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (chatFriendFragment != null) {
            fragmentTransaction.hide(chatFriendFragment);
        }
        if (chatConversationFragment != null) {
            fragmentTransaction.hide(chatConversationFragment);
        }

    }
}
