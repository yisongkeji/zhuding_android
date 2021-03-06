package com.foreseers.chat.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseers.chat.activity.MyActivity;
import com.foreseers.chat.activity.UserDetailsActivity;
import com.foreseers.chat.bean.Constant;
import com.foreseers.chat.bean.UserBean;
import com.foreseers.chat.util.HuanXinHelper;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * 聊天
 * A simple {@link Fragment} subclass.
 */
public class MyChatFragment extends EaseChatFragment implements EaseChatFragment
        .EaseChatFragmentHelper {


    private SharedPreferences sharedPreferences;
    private Intent intent;

    public MyChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        setChatFragmentHelper(this);
//        fragmentArgs = getArguments();
//        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);
        HuanXinHelper.getInstance().isLoggedIn();
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onSetMessageAttributes(EMMessage message) {
        //set message extension
        message.setAttribute("em_robot_message", EMClient.getInstance().getCurrentUser());
        //设置要发送扩展消息用户昵称
        message.setAttribute(Constant.USER_NAME, sharedPreferences.getString("nick", ""));
        message.setAttribute(Constant.USER, EMClient.getInstance().getCurrentUser());
        //设置要发送扩展消息用户头像
        message.setAttribute(Constant.HEAD_IMAGE_URL, sharedPreferences.getString("url", ""));
        Log.e("rrrrrrrrr", message.toString());

        String hxIdTo = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);
//        String userName = fragmentArgs.getString(EaseConstant.EXTRA_USER_NAME);
//        String avatar = fragmentArgs.getString(EaseConstant.EXTRA_USER_AVATAR);
//        EaseUser easeUser = new EaseUser(hxIdTo);
//        easeUser.setAvatar(avatar);
//        easeUser.setNickname(userName);
        OkGo.<String>post(Urls.Url_Userquery).tag(this)
                .params("uid", PreferenceManager.getUserId(getActivity()))
                .params("userid",hxIdTo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson=new Gson();
                        UserBean userBean =gson.fromJson(response.body(), UserBean.class);
                        int userid=userBean.getData().getUserid();
                        int vip=userBean.getData().getVip();
                        String  head = userBean.getData().getHead();
                        String name=userBean.getData().getUsername();

                        EaseUser easeUser = new EaseUser(userid+"");
                        easeUser.setAvatar(head);
                        easeUser.setNickname(name);

                        sharedPreferences.edit().putString(userid+"", name + "&" + head+"&"+vip).commit();
                    }
                });

//        sharedPreferences.edit().putString(hxIdTo, userName + "&" + avatar).commit();

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
//        Toast.makeText(getActivity(), "头像被点击了" + username, Toast.LENGTH_SHORT).show();
        if (username.equals(EMClient.getInstance().getCurrentUser())) {
            intent = new Intent(getActivity(), MyActivity.class);
            getActivity().startActivity(intent);
        } else {
            intent = new Intent(getActivity(), UserDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userid", username);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }


}
