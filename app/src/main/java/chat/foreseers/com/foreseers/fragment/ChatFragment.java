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

/**
 * 聊天
 */
public class ChatFragment extends BaseMainFragment implements RadioGroup.OnCheckedChangeListener {


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


        return view;
    }

    @Override
    public void initViews() {
        chatRadioGroup.setOnCheckedChangeListener(this);

        fragmentManager = getChildFragmentManager();
        fragmentTransactionShow = fragmentManager.beginTransaction();
        if (chatFriendFragment != null) {
            fragmentTransactionShow.show(chatFriendFragment);
        } else {
            chatFriendFragment = new ChatFriendFragment();
            fragmentTransactionShow.add(R.id.chat_layout, chatFriendFragment);
        }

        fragmentTransactionShow.commit();
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
                if (chatFriendFragment != null) {
                    fragmentTransaction.show(chatFriendFragment);
                } else {
                    chatFriendFragment = new ChatFriendFragment();
                    fragmentTransaction.add(R.id.chat_layout, chatFriendFragment);
                }

                break;
            case R.id.chat_radio_temporary_session:

                if (chatConversationFragment != null) {
                    fragmentTransaction.show(chatConversationFragment);

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
