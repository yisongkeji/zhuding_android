package chat.foreseers.com.foreseers.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.adapter.ChatAdapter;
import chat.foreseers.com.foreseers.bean.ChatBean;
import chat.foreseers.com.foreseers.global.BaseMainFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatConversationFragment extends BaseMainFragment {


    @BindView(R.id.recycler_chat)
    RecyclerView recyclerChat;
    Unbinder unbinder;
    private ChatBean chatBean = new ChatBean();
    private List<ChatBean> chatBeans = new ArrayList<>();
    private ChatAdapter chatAdapter;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initViews() {
        chatAdapter = new ChatAdapter(getActivity(), chatBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerChat.setLayoutManager(linearLayoutManager);
        recyclerChat.setAdapter(chatAdapter);
        chatAdapter.setNewData(chatBeans);
    }

    @Override
    public void initDatas() {
        for (int i = 0; i < 10; i++) {
            chatBean.setName("asdasdasf");
            chatBeans.add(chatBean);

        }
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }
}
