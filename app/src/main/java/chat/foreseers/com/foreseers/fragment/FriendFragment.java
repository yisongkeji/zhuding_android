package chat.foreseers.com.foreseers.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.activity.ChatActivity;
import chat.foreseers.com.foreseers.global.BaseFragment;

/**
 * 联系人
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {


    @BindView(R.id.text)
    TextView text;
    Unbinder unbinder;
    private EaseContactListFragment contactListFragment;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
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
        contactListFragment = new EaseContactListFragment();
        //需要设置联系人列表才能启动fragment
        contactListFragment.setContactsMap(getContacts());
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id
                .layout_FriendFragment, contactListFragment).commit();

    }

    /**
     * 手动添加好友
     *
     * @return
     */
    private Map<String, EaseUser> getContacts() {
        Map<String, EaseUser> contacts = new HashMap<String, EaseUser>();
        for (int i = 1; i <= 10; i++) {
            EaseUser user = new EaseUser("fffseuitest" + i);
            contacts.put("asdf" + i, user);

        }
        return contacts;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment
                .EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant
                        .EXTRA_USER_ID, user.getUsername()));
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

    }
}
