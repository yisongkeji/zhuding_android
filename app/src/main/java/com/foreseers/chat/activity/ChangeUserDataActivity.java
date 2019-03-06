package com.foreseers.chat.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.UserDataBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
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

public class ChangeUserDataActivity extends BaseActivity {


    @BindView(R.id.text_name)
    EditText textName;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_data)
    TextView textData;
    @BindView(R.id.text_age)
    TextView textAge;
    @BindView(R.id.text_ziwei)
    TextView textZiwei;
    @BindView(R.id.layout_back)
    FrameLayout layoutBack;
    @BindView(R.id.layout_change_sex)
    LinearLayout layoutChangeSex;
    private LoginBean loginBean;
    private UserDataBean userDataBean;
    private UserDataBean.DataBean dataBean;
    private String userid;
    private ArrayList<String> sexList = new ArrayList<>();
    private ArrayList<String> one = new ArrayList<>();
    private ArrayList<String> three = new ArrayList<>();
    private OptionsPickerView pvSexOptions;
    private int REQUEST_CODE_USER_DATA;
    private TimePickerView pvCustomLunar;
    private String birthData;
    private String time;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_data);
        ButterKnife.bind(this);

        getdata();
        getDataFromHttp();

        getSexData();
        initSexOptionsPicker();

        initLunarPicker();
        listener();

    }

    private void getdata() {

        userid = GetLoginTokenUtil.getUserId(this);
    }

    private void listener() {
        textName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.i("okgo", "afterTextChanged: " + editable.toString());
                Log.i("okgo", "userid: " + userid);
                OkGo.<String>post(Urls.Url_UpdateUser).tag(this)
                        .params("userid", userid)
                        .params("name", editable.toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                REQUEST_CODE_USER_DATA = 200;
                            }
                        });
            }
        });
    }

    private void getDataFromHttp() {


        OkGo.<String>post(Urls.Url_Query).tag(this)
                .params("facebookid", GetLoginTokenUtil.getFaceBookId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {//老用户

                            userDataBean = gson.fromJson(response.body(), UserDataBean.class);

                            dataBean = userDataBean.getData();

                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();


                        } else if (loginBean.getStatus().equals("fail")) {

                        }
                    }
                });
    }

    private final int DATASUCCESS = 1;
    private final int DATASEXSUCCESS = 3;
    private final int DATATIMESUCCESS = 4;
    private final int DATAFELLED = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    textName.setText(dataBean.getUsername());
                    textSex.setText(dataBean.getSex().equals("M") ? R.string.man : R.string.woman);
                    textAge.setText(dataBean.getReservedint() + "");
                    textZiwei.setText(dataBean.getZiwei());
                    Log.e("okgo", "textZiwei: " + dataBean.getZiwei());
                    textData.setText(upData());
                    break;
                case DATASEXSUCCESS:

                    textZiwei.setText(dataBean.getZiwei());
                    break;
                case DATATIMESUCCESS:
                    textAge.setText(dataBean.getReservedint() + "");
                    textZiwei.setText(dataBean.getZiwei());
                    break;
                case DATAFELLED:

                    break;
            }
        }
    };

    @OnClick({R.id.layout_back, R.id.layout_change_sex, R.id.layout_birth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                intent = new Intent();
                setResult(REQUEST_CODE_USER_DATA, intent);
                finish();
                break;
            case R.id.layout_change_sex:
                pvSexOptions.show();
                break;

            case R.id.layout_birth:
                pvCustomLunar.show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            setResult(REQUEST_CODE_USER_DATA, this.intent);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //格式化出生日期
    private String upData() {
        String birth = dataBean.getDate();
        //出生日期
        String[] data = birth.split("-");
        String birthData = data[0] + getString(R.string.year) + data[1] + getString(R.string.month) + data[2] + getString(R.string.day);
        //出生时间
        String time1 = dataBean.getTime();
        int index = time1.indexOf(":");
        String time2 = birth.substring(0, index);
        String time = time2 + getString(R.string.time);
        return birthData + time;
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
        endDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                textData.setText(getTime(date));

                String birth = textData.getText().toString();
                //出生日期
                int indexYear = birth.indexOf("日");
                String date1 = birth.substring(0, indexYear);
                String date2 = date1.replace("年", "-");
                String date3 = date2.replace("月", "-");


                //出生时间
                int index = birth.indexOf(" ");
                String time1 = birth.substring(index + 1, birth.length() - 1);
                String time2 = time1 + ":00:00";
                Log.e("okgo", "birthData: " + birthData + "  time" + time);

                OkGo.<String>post(Urls.Url_UpdateUser).tag(this)
                        .params("userid", userid)
                        .params("date", date3)
                        .params("time", time2)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();
                                loginBean = gson.fromJson(response.body(), LoginBean.class);
                                if (loginBean.getStatus().equals("success")) {//老用户
                                    userDataBean = gson.fromJson(response.body(), UserDataBean.class);
                                    dataBean = userDataBean.getData();
                                    REQUEST_CODE_USER_DATA = 200;
                                    mHandler.obtainMessage(DATATIMESUCCESS).sendToTarget();
                                }
                            }
                        });
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
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean
                                    isChecked) {
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


    //    getTime
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH時");
        return format.format(date);
    }

    //選擇性別
    private void initSexOptionsPicker() {
        pvSexOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                textSex.setText(sexList.get(options2));
                String newSex;
                if (sexList.get(options2).equals("男")) {
                    newSex = "M";
                } else {
                    newSex = "F";
                }
                OkGo.<String>post(Urls.Url_UpdateUser).tag(this)
                        .params("userid", userid)
                        .params("sex", newSex)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();
                                loginBean = gson.fromJson(response.body(), LoginBean.class);
                                if (loginBean.getStatus().equals("success")) {//老用户
                                    userDataBean = gson.fromJson(response.body(), UserDataBean.class);
                                    dataBean = userDataBean.getData();

                                    REQUEST_CODE_USER_DATA = 200;
                                    mHandler.obtainMessage(DATASEXSUCCESS).sendToTarget();
                                }


                            }
                        });
            }
        })
                .setSubmitColor(R.color.colorAccent)
                .setCancelColor(R.color.colorAccent)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                         textUserTimezone.setText(timezone.get(options2));
//                        String str = "options1: " + options1 + "\noptions2: " + options2 +
// "\noptions3: " + options3;
//                        Toast.makeText(UserDataActivity.this, str, Toast.LENGTH_SHORT).show();
                        Log.i("@@@@@@", "sex :" + sexList.get(options2));
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvSexOptions.setNPicker(one, sexList, three);
        pvSexOptions.setSelectOptions(0, 27, 0);
    }

    //性別
    private void getSexData() {
        sexList.add(getString(R.string.man));
        sexList.add(getString(R.string.woman));

        one.add("");
        three.add("");
    }


    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {

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
}
