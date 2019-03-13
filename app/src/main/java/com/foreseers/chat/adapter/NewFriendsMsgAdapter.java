/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.foreseers.chat.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foreseers.chat.bean.UserBean;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.domain.InviteMessage;
import com.foreseers.chat.R;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruffian.library.widget.RImageView;


import java.util.List;

public class NewFriendsMsgAdapter extends ArrayAdapter<InviteMessage> {

    private Context context;
    private InviteMessgeDao messgeDao;

    public NewFriendsMsgAdapter(Context context, int textViewResourceId, List<InviteMessage>
            objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        messgeDao = new InviteMessgeDao(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.em_row_invite_msg, null);
            holder.avator = (RImageView) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.agreeBtn = (Button) convertView.findViewById(R.id.agree);
            holder.refuseBtn = (Button) convertView.findViewById(R.id.refuse);
            // holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final InviteMessage msg = getItem(position);
        if (msg != null) {
            holder.agreeBtn.setVisibility(View.GONE);
            holder.refuseBtn.setVisibility(View.GONE);
            Log.i("msg@@@@@", "getView: " + msg.getReason());
            OkGo.<String>post(Urls.Url_Userquery).tag(this)
                    .params("userid",msg.getFrom())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson=new Gson();
                            UserBean userBean =gson.fromJson(response.body(),UserBean.class);
                           String  head = userBean.getData().getHead();
                           String name=userBean.getData().getUsername();

                            if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEAGREED) {//已同意你的好友请求
                                holder.agreeBtn.setVisibility(View.GONE);
                                holder.refuseBtn.setVisibility(View.GONE);
                                holder.message.setText(context.getResources().getString(R.string
                                        .Has_agreed_to_your_friend_request));
                                Glide.with(context).load(head).error(R.mipmap.icon_me_loading_03).into(holder.avator);
                            } else if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEINVITEED || msg
                                    .getStatus() == InviteMessage.InviteMessageStatus.BEAPPLYED ||
                                    msg.getStatus() == InviteMessage.InviteMessageStatus.GROUPINVITATION) {
                                String[] arr = msg.getReason().split("\\|");


                                holder.name.setText(arr[0]);

                                Glide.with(context).load(arr[1]).error(R.mipmap.icon_me_loading_03).into(holder.avator);

                                holder.agreeBtn.setVisibility(View.VISIBLE);
                                holder.refuseBtn.setVisibility(View.VISIBLE);
                                if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEINVITEED) {
                                    if (msg.getReason() == null) {
                                        // use default text
                                        holder.message.setText(context.getResources().getString(R.string
                                                .Request_to_add_you_as_a_friend));
                                    }
                                } else if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEAPPLYED) {
                                    //application to join group
                                    if (TextUtils.isEmpty(msg.getReason())) {
                                        holder.message.setText(context.getResources().getString(R.string
                                                .Apply_to_the_group_of) + msg.getGroupName());
                                    }
                                }
//				else if (msg.getStatus() == InviteMessage.InviteMessageStatus.GROUPINVITATION) {
//				    if (TextUtils.isEmpty(msg.getReason())) {
//                        holder.message.setText(context.getResources().getString(R.string
// .invite_join_group) + msg.getGroupName());
//                    }
//				}

                                // set click listener
                                holder.agreeBtn.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // accept invitation
                                        acceptInvitation(holder.avator, holder.agreeBtn, holder.refuseBtn, msg);

                                    }
                                });
                                holder.refuseBtn.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // decline invitation
                                        refuseInvitation(holder.agreeBtn, holder.refuseBtn, msg);
                                    }
                                });
                            } else {
                                String str = "";
                                InviteMessage.InviteMessageStatus status = msg.getStatus();
                                switch (status) {
                                    case AGREED:
                                        str = context.getResources().getString(R.string.Has_agreed_to);
                                        Glide.with(context).load(head).into(holder.avator);
                                        break;
                                    case REFUSED:
                                        str = context.getResources().getString(R.string.Has_refused_to);
                                        Glide.with(context).load(head).into(holder.avator);
                                        break;
//                    case GROUPINVITATION_ACCEPTED:
//                        str = context.getResources().getString(R.string.accept_join_group);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case GROUPINVITATION_DECLINED:
//                        str = context.getResources().getString(R.string.refuse_join_group);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
                                    case MULTI_DEVICE_CONTACT_ADD:
                                        str = context.getResources().getString(R.string.multi_device_contact_add);
                                        str = String.format(str, msg.getFrom());
                                        break;
                                    case MULTI_DEVICE_CONTACT_BAN:
                                        str = context.getResources().getString(R.string.multi_device_contact_ban);
                                        str = String.format(str, msg.getFrom());
                                        break;
                                    case MULTI_DEVICE_CONTACT_ALLOW:
                                        str = context.getResources().getString(R.string.multi_device_contact_allow);
                                        str = String.format(str, msg.getFrom());
                                        break;
                                    case MULTI_DEVICE_CONTACT_ACCEPT:
                                        str = context.getResources().getString(R.string
                                                .multi_device_contact_accept);
                                        str = String.format(str, msg.getFrom());
                                        break;
                                    case MULTI_DEVICE_CONTACT_DECLINE:
                                        str = context.getResources().getString(R.string
                                                .multi_device_contact_decline);
                                        str = String.format(str, msg.getFrom());
                                        break;
//                    case MULTI_DEVICE_GROUP_CREATE:
//                        str = context.getResources().getString(R.string
// .multi_device_group_create);
//                        break;
//                    case MULTI_DEVICE_GROUP_DESTROY:
//                        str = context.getResources().getString(R.string
// .multi_device_group_destroy);
//                        break;
//                    case MULTI_DEVICE_GROUP_JOIN:
//                        str = context.getResources().getString(R.string.multi_device_group_join);
//                        break;
//                    case MULTI_DEVICE_GROUP_LEAVE:
//                        str = context.getResources().getString(R.string.multi_device_group_leave);
//                        break;
//                    case MULTI_DEVICE_GROUP_APPLY:
//                        str = context.getResources().getString(R.string.multi_device_group_apply);
//                        break;
//                    case MULTI_DEVICE_GROUP_APPLY_ACCEPT:
//                        str = context.getResources().getString(R.string
// .multi_device_group_apply_accept);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_APPLY_DECLINE:
//                        str = context.getResources().getString(R.string
// .multi_device_group_apply_decline);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_INVITE:
//                        str = context.getResources().getString(R.string
// .multi_device_group_invite);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_INVITE_ACCEPT:
//                        str = context.getResources().getString(R.string
// .multi_device_group_invite_accept);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_INVITE_DECLINE:
//                        str = context.getResources().getString(R.string
// .multi_device_group_invite_decline);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_KICK:
//                        str = context.getResources().getString(R.string.multi_device_group_kick);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_BAN:
//                        str = context.getResources().getString(R.string.multi_device_group_ban);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_ALLOW:
//                        str = context.getResources().getString(R.string.multi_device_group_allow);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_BLOCK:
//                        str = context.getResources().getString(R.string.multi_device_group_block);
//                        break;
//                    case MULTI_DEVICE_GROUP_UNBLOCK:
//                        str = context.getResources().getString(R.string
// .multi_device_group_unblock);
//                        break;
//                    case MULTI_DEVICE_GROUP_ASSIGN_OWNER:
//                        str = context.getResources().getString(R.string
// .multi_device_group_assign_owner);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_ADD_ADMIN:
//                        str = context.getResources().getString(R.string
// .multi_device_group_add_admin);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_REMOVE_ADMIN:
//                        str = context.getResources().getString(R.string
// .multi_device_group_remove_admin);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_ADD_MUTE:
//                        str = context.getResources().getString(R.string
// .multi_device_group_add_mute);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
//                    case MULTI_DEVICE_GROUP_REMOVE_MUTE:
//                        str = context.getResources().getString(R.string
// .multi_device_group_remove_mute);
//                        str = String.format(str, msg.getGroupInviter());
//                        break;
                                    default:
                                        break;
                                }
                                holder.message.setText(str);
                            }
                            }
                    });





//            Glide.with(context).load(arr[1]).into(holder.avator);

//             holder.time.setText(DateUtils.getTimestampString(new
//             Date(msg.getTime())));

            Log.i("friend@@@@", "msg.getStatus(): " + msg.getStatus().toString());

        }

        return convertView;
    }

    /**
     * accept invitation
     *
     * @param buttonAgree
     * @param buttonRefuse
     * @param msg
     */
    private void acceptInvitation(final ImageView avator, final Button buttonAgree, final Button buttonRefuse, final InviteMessage msg) {
        final ProgressDialog pd = new ProgressDialog(context);
        String str1 = context.getResources().getString(R.string.Are_agree_with);
        final String str2 = context.getResources().getString(R.string.Has_agreed_to);
        final String str3 = context.getResources().getString(R.string.Agree_with_failure);
        pd.setMessage(str1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                // call api
                try {
                    if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEINVITEED) {//accept be friends接受做朋友
                        Log.i("BEINVITEED", "msg.getFrom(): " + msg.getFrom());
//                        EMClient.getInstance().contactManager().addContact(msg.getFrom(), msg.getFrom() + "|" + avator);
                        EMClient.getInstance().contactManager().acceptInvitation(msg.getFrom());
                        OkGo.<String>post(Urls.Url_ADDFriend).tag(this)
                                .params("userid", EMClient.getInstance().getCurrentUser())
                                .params("friendid", msg.getFrom())
                                .params("reation", 0)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {

                                        Log.e("friend", "onSuccess: 添加好友被同意");
                                    }
                                });

                    } else if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEAPPLYED) {
                        //accept application to join group
                        EMClient.getInstance().groupManager().acceptApplication(msg.getFrom(),
                                msg.getGroupId());
                    } else if (msg.getStatus() == InviteMessage.InviteMessageStatus
                            .GROUPINVITATION) {
                        EMClient.getInstance().groupManager().acceptInvitation(msg.getGroupId(),
                                msg.getGroupInviter());
                    }
                    msg.setStatus(InviteMessage.InviteMessageStatus.AGREED);
                    // update database
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    messgeDao.updateMessage(msg.getId(), values);
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            buttonAgree.setText(str2);
                            buttonAgree.setBackground(null);
                            buttonAgree.setEnabled(false);
                            String[] arr = msg.getReason().split("\\|");

                            Glide.with(context).load(arr[1]).into(avator);
                            buttonRefuse.setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (final Exception e) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(context, str3 + e.getMessage(), Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }
        }).start();
    }

    /**
     * decline invitation
     *
     * @param buttonAgree
     * @param buttonRefuse
     * @param msg
     */
    private void refuseInvitation(final Button buttonAgree, final Button buttonRefuse, final
    InviteMessage msg) {
        final ProgressDialog pd = new ProgressDialog(context);
        String str1 = context.getResources().getString(R.string.Are_refuse_with);
        final String str2 = context.getResources().getString(R.string.Has_refused_to);
        final String str3 = context.getResources().getString(R.string.Refuse_with_failure);
        pd.setMessage(str1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                // call api
                try {
                    if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEINVITEED) {//decline the invitation
                        EMClient.getInstance().contactManager().declineInvitation(msg.getFrom());
                    } else if (msg.getStatus() == InviteMessage.InviteMessageStatus.BEAPPLYED) {
                        //decline application to join group
                        EMClient.getInstance().groupManager().declineApplication(msg.getFrom(),
                                msg.getGroupId(), "");
                    } else if (msg.getStatus() == InviteMessage.InviteMessageStatus
                            .GROUPINVITATION) {
                        EMClient.getInstance().groupManager().declineInvitation(msg.getGroupId(),
                                msg.getGroupInviter(), "");
                    }
                    msg.setStatus(InviteMessage.InviteMessageStatus.REFUSED);
                    // update database
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    messgeDao.updateMessage(msg.getId(), values);
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            buttonRefuse.setText(str2);
                            buttonRefuse.setBackground(null);
                            buttonRefuse.setEnabled(false);

                            buttonAgree.setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (final Exception e) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(context, str3 + e.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                }
            }
        }).start();
    }

    private static class ViewHolder {
        ImageView avator;
        TextView name;
        TextView message;
        Button agreeBtn;
        Button refuseBtn;
    }

}
