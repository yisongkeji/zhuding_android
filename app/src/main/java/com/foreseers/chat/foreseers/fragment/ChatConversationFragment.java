package com.foreseers.chat.foreseers.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.foreseers.chat.foreseers.R;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 临时聊天
 * A simple {@link Fragment} subclass.
 */
public class ChatConversationFragment extends SupportFragment {


    Unbinder unbinder;




    public ChatConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_conversation, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


}
