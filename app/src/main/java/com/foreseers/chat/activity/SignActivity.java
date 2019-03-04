package com.foreseers.chat.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.google.zxing.common.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends BaseActivity {

    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    @BindView(R.id.edittext)
    EditText edittext;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.text_save)
    TextView textSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                textNum.setText(String.valueOf(edittext.getText().length()));

            }
        });


    }

    @OnClick(R.id.text_save)
    public void onViewClicked() {
        String sign = edittext.getText().toString();
        if (sign != null && !sign.isEmpty()) {
            OkGo.<String>post(Urls.Url_UserSign).tag(this)
                    .params("userid", GetLoginTokenUtil.getUserId(this))
                    .params("obligate", sign)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson=new Gson();
                            LoginBean loginBean=gson.fromJson(response.body(),LoginBean.class);
                            if (loginBean.getStatus().equals("success")){
                                finish();
                            }else {
                                Toast.makeText(SignActivity.this, "发送失败，请检查网络重新发送", Toast
                                        .LENGTH_LONG)
                                        .show();
                            }

                        }
                    });
        } else {
            Toast.makeText(this, "签名不能为空", Toast.LENGTH_LONG).show();
        }


    }
}
