package com.foreseers.chat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.FortunetellingOutlineBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 命书创建用户
 */
public class FortunetellingDataActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.edit_user_name) EditText editUserName;
    @BindView(R.id.radio_sex_man) RadioButton radioSexMan;
    @BindView(R.id.radio_sex_woman) RadioButton radioSexWoman;
    @BindView(R.id.radio_user_sex) RadioGroup radioUserSex;
    @BindView(R.id.text_user_birth) TextView textUserBirth;
    @BindView(R.id.text_user_timezone) TextView textUserTimezone;
    @BindView(R.id.bt_affirm) Button btAffirm;

    private String gender;
    private TimePickerView pvCustomLunar;
    private OptionsPickerView pvNoLinkOptions;
    private ArrayList<String> timezone = new ArrayList<>();
    private ArrayList<String> one = new ArrayList<>();
    private ArrayList<String> three = new ArrayList<>();
    private String date;
    private String time2;
    private boolean name = false, sex = false;
    private boolean birth = false, time = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_fortunetelling_data);
        ButterKnife.bind(this);
        initLunarPicker();
        initNoLinkOptionsPicker();
        getNoLinkData();
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    name = true;
                    doubleChose();
                } else {
                    name = false;
                    doubleChose();
                }
            }
        });
        radioUserSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.radio_sex_man:
                        gender = "M";
                        sex = true;
                        doubleChose();
                        break;
                    case R.id.radio_sex_woman:
                        gender = "F";
                        sex = true;
                        doubleChose();
                        break;
                    default:
                        sex = false;
                        doubleChose();
                        break;
                }
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    @OnClick({R.id.text_user_birth, R.id.text_user_timezone, R.id.bt_affirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_user_birth:
                hideInput(this);
                pvCustomLunar.show();
                break;
            case R.id.text_user_timezone:
                hideInput(this);
                pvNoLinkOptions.show();
                break;
            case R.id.bt_affirm:
                upData();
                OkGo.<String>post(Urls.Url_LifeBook).tag(this)
                        .params("userid", PreferenceManager.getUserId(this))
                        .params("name", editUserName.getText()
                                .toString())
                        .params("date", date)
                        .params("time", time2)
                        .params("gender", gender)
                        .params("timezone", textUserTimezone.getText()
                                .toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();
                                LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                                if (loginBean.getStatus()
                                        .equals("success")) {
                                    FortunetellingOutlineBean fortunetellingOutlineBean = gson.fromJson(response.body(),
                                                                                                        FortunetellingOutlineBean.class);
                                    FortunetellingOutlineBean.DataBean dataBean = fortunetellingOutlineBean.getData();
                                    Intent intent = new Intent(getActivity(), FortunetellingOutlineActivity.class);
                                    //                                    Intent intent = new Intent();
                                    //                                    setResult(0x001, intent);
                                    intent.putExtra("lifeuserid", dataBean.getLifeuserid() + "");
                                    intent.putExtra("name", editUserName.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                break;
        }
    }

    /**
     * 日期選擇
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1940, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                textUserBirth.setText(getTime(date));
                //                Toast.makeText(UserDataActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                                birth = true;
                                doubleChose();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                            }
                        });
                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, true, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH時");
        return format.format(date);
    }

    private void upData() {
        Log.i("OkGo", "date: " + textUserBirth.getText()
                .toString());

        String birth = textUserBirth.getText()
                .toString();
        //出生日期
        int indexYear = birth.indexOf("日");
        String date1 = birth.substring(0, indexYear);
        String date2 = date1.replace("年", "-");
        date = date2.replace("月", "-");

        //出生时间
        int index = birth.indexOf(" ");
        String time1 = birth.substring(index + 1, birth.length() - 1);
        time2 = time1 + ":00:00";
        //
    }

    //選擇時區
    private void initNoLinkOptionsPicker() {
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                textUserTimezone.setText(timezone.get(options2));
                time = true;
                doubleChose();
            }
        }).setSubmitColor(getResources().getColor(R.color.colorAccent))
                .setCancelColor(getResources().getColor(R.color.colorAccent))
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        //                         textUserTimezone.setText(timezone.get(options2));
                        //                        String str = "options1: " + options1 + "\noptions2: " + options2 +
                        // "\noptions3: " + options3;
                        //                        Toast.makeText(UserDataActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvNoLinkOptions.setNPicker(one, timezone, three);
        pvNoLinkOptions.setSelectOptions(0, 27, 0);
    }

    //時區
    private void getNoLinkData() {
        timezone.add("UTC-12:00");
        timezone.add("UTC-11:00");
        timezone.add("UTC-10:00");
        timezone.add("UTC-09:30");
        timezone.add("UTC-09:00");
        timezone.add("UTC-08:00");
        timezone.add("UTC-07:00");
        timezone.add("UTC-06:00");
        timezone.add("UTC-05:00");
        timezone.add("UTC-04:00");
        timezone.add("UTC-03:30");
        timezone.add("UTC-03:00");
        timezone.add("UTC-02:00");
        timezone.add("UTC-01:00");
        timezone.add("UTC 0");
        timezone.add("UTC+01:00");
        timezone.add("UTC+02:00");
        timezone.add("UTC+03:00");
        timezone.add("UTC+03:30");
        timezone.add("UTC+04:00");
        timezone.add("UTC+04:30");
        timezone.add("UTC+05:00");
        timezone.add("UTC+05:30");
        timezone.add("UTC+05:45");
        timezone.add("UTC+06:00");
        timezone.add("UTC+06:30");
        timezone.add("UTC+07:00");
        timezone.add("UTC+08:00");
        timezone.add("UTC+08:45");
        timezone.add("UTC+09:00");
        timezone.add("UTC+09:30");
        timezone.add("UTC+10:00");
        timezone.add("UTC+10:30");
        timezone.add("UTC+11:00");
        timezone.add("UTC+12:00");
        timezone.add("UTC+12:45");
        timezone.add("UTC+13:00");
        timezone.add("UTC+14:00");

        one.add("");
        three.add("");
    }

    public static void hideInput(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(context.getWindow()
                                            .getDecorView()
                                            .getWindowToken(), 0);
    }

    private void doubleChose() {

        if (name && sex && birth && time) {
            btAffirm.setBackgroundResource(R.drawable.rounded_text_accent);
            btAffirm.setEnabled(true);
        } else {
            btAffirm.setBackgroundResource(R.drawable.rounded_text_gray);
            btAffirm.setEnabled(false);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
