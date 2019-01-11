package chat.foreseers.com.foreseers.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
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
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.bean.UserDataBean;
import chat.foreseers.com.foreseers.dialog.DoubtDialog;
import chat.foreseers.com.foreseers.util.LimitInputTextWatcher;
import chat.foreseers.com.foreseers.util.Urls;


public class UserDataActivity extends AppCompatActivity {

    @BindView(R.id.img_doubt)
    ImageView imgDoubt;
    @BindView(R.id.edit_user_name)
    EditText editUserName;
    @BindView(R.id.bt_affirm_name)
    Button btAffirmName;
    @BindView(R.id.layout_user_name)
    LinearLayout layoutUserName;
    @BindView(R.id.text_user_birth)
    TextView textUserBirth;
    @BindView(R.id.bt_affirm_birth)
    Button btAffirmBirth;
    @BindView(R.id.layout_user_birth)
    LinearLayout layoutUserBirth;
    @BindView(R.id.text_user_name_affirm)
    TextView textUserNameAffirm;
    @BindView(R.id.text_user_birth_affirm)
    TextView textUserBirthAffirm;
    @BindView(R.id.bt_affirm)
    Button btAffirm;
    @BindView(R.id.layout_user_data_affirm)
    LinearLayout layoutUserDataAffirm;
    @BindView(R.id.umg_name_modification)
    ImageView umgNameModification;
    @BindView(R.id.umg_birth_modification)
    ImageView umgBirthModification;
    @BindView(R.id.umg_sex_modification)
    ImageView umgSexModification;
    @BindView(R.id.text_user_sex_affirm)
    TextView textUserSexAffirm;
    @BindView(R.id.radio_sex_man)
    RadioButton radioSexMan;
    @BindView(R.id.radio_sex_woman)
    RadioButton radioSexWoman;
    @BindView(R.id.radio_user_sex)
    RadioGroup radioUserSex;
    @BindView(R.id.text_user_timezone)
    TextView textUserTimezone;
    @BindView(R.id.umg_timezone_modification)
    ImageView umgTimezoneModification;
    @BindView(R.id.text_user_timezone_affirm)
    TextView textUserTimezoneAffirm;
    @BindView(R.id.layout_user_data)
    RelativeLayout layoutUserData;

    private TimePickerView pvCustomLunar;
    private int i = 0;
    private DoubtDialog doubtDialog;
    private Bundle bundle;
    private String facebookName;
    private String facebookId;
    private OptionsPickerView pvNoLinkOptions;
    private ArrayList<String> timezone = new ArrayList<>();
    private ArrayList<Double> doubleTimezone = new ArrayList<Double>();
    private ArrayList<String> one = new ArrayList<>();
    private ArrayList<String> three = new ArrayList<>();
    private boolean name = false, sex = false;
    private boolean birth = false, time = false;
    private Intent intent;

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private String date;
    private String time2;
    private String gender;
    private Gson gson;
    private UserDataBean userDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        ButterKnife.bind(this);

        init();
        Listen();

    }

    private void init() {
        bundle = getIntent().getExtras();
        facebookName = bundle.getString("facebookName");
        facebookId = bundle.getString("facebookId");
        editUserName.setText(facebookName);
        if (editUserName.getText().length() > 0) {
            name = true;
            doubleChose();
        }
        getNoLinkData();
        initNoLinkOptionsPicker();
        initLunarPicker();

        layoutUserName.setVisibility(View.VISIBLE);
        layoutUserBirth.setVisibility(View.GONE);
        layoutUserDataAffirm.setVisibility(View.GONE);
    }

    //監聽
    private void Listen() {

        editUserName.addTextChangedListener(new LimitInputTextWatcher(editUserName));
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

        textUserBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0) {
                    btAffirmBirth.setBackgroundResource(R.drawable.rounded_text_accent);
                    btAffirmBirth.setEnabled(true);
                } else {
                    btAffirmBirth.setBackgroundResource(R.drawable.rounded_text_gray);
                    btAffirmBirth.setEnabled(false);
                }
            }
        });

        radioUserSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.radio_sex_man:
                        textUserSexAffirm.setText(R.string.man);
                        sex = true;
                        gender = "M";
                        doubleChose();
                        break;
                    case R.id.radio_sex_woman:
                        textUserSexAffirm.setText(R.string.woman);
                        sex = true;
                        gender = "F";
                        doubleChose();
                        break;
                    default:
                        textUserSexAffirm.setText(R.string.man);
                        sex = false;
                        doubleChose();
                        break;
                }
            }
        });
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
        endDate.set(2069, 12, 31);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                textUserBirth.setText(getTime(date));
                textUserBirthAffirm.setText(getTime(date));
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
                                doubleTime();
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
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f
                                        : 1.1f);
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
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year
                                .getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams)
                                    childAt.getLayoutParams());
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


    @OnClick({R.id.img_doubt, R.id.bt_affirm_name, R.id.text_user_birth, R.id.bt_affirm_birth, R
            .id.bt_affirm, R.id.umg_name_modification, R.id.umg_sex_modification, R.id
            .umg_birth_modification, R.id.text_user_timezone, R.id.umg_timezone_modification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_doubt:

                doubtDialog = new DoubtDialog(this, R.style.MyDialog, new DoubtDialog
                        .LeaveMyDialogListener() {

                    @Override
                    public void onClick(View view) {
                        doubtDialog.dismiss();
                    }
                });
                doubtDialog.setCancelable(true);
                doubtDialog.show();

                break;
            case R.id.bt_affirm_name://姓名确认button
                layoutUserData.setVisibility(View.GONE);
                switch (i) {
                    case 0:
                        layoutUserName.setVisibility(View.GONE);
                        layoutUserBirth.setVisibility(View.VISIBLE);
                        layoutUserDataAffirm.setVisibility(View.GONE);
                        layoutUserData.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        layoutUserName.setVisibility(View.GONE);
                        layoutUserBirth.setVisibility(View.GONE);
                        layoutUserDataAffirm.setVisibility(View.VISIBLE);
                        layoutUserData.setVisibility(View.VISIBLE);
                        break;
                }
                textUserNameAffirm.setText(editUserName.getText());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                chengAnimation();


                break;
            case R.id.text_user_birth:
                pvCustomLunar.show();
                break;
            case R.id.text_user_timezone:
                pvNoLinkOptions.show();


                break;
            case R.id.bt_affirm_birth://出生日期确认button
                layoutUserName.setVisibility(View.GONE);
                layoutUserBirth.setVisibility(View.GONE);
                layoutUserDataAffirm.setVisibility(View.VISIBLE);
                chengAnimation();
                break;

            case R.id.umg_name_modification:
                layoutUserName.setVisibility(View.VISIBLE);
                layoutUserBirth.setVisibility(View.GONE);
                layoutUserDataAffirm.setVisibility(View.GONE);
                i = 1;
                break;
            case R.id.umg_sex_modification:
                layoutUserName.setVisibility(View.VISIBLE);
                layoutUserBirth.setVisibility(View.GONE);
                layoutUserDataAffirm.setVisibility(View.GONE);
                i = 1;
                break;
            case R.id.umg_birth_modification:
                layoutUserName.setVisibility(View.GONE);
                layoutUserBirth.setVisibility(View.VISIBLE);
                layoutUserDataAffirm.setVisibility(View.GONE);
                break;
            case R.id.umg_timezone_modification:
                layoutUserName.setVisibility(View.GONE);
                layoutUserBirth.setVisibility(View.VISIBLE);
                layoutUserDataAffirm.setVisibility(View.GONE);
                break;
            case R.id.bt_affirm://最终确认
//                intent = new Intent(UserDataActivity.this, MainActivity.class);
//                startActivity(intent);
                upData();
                upDataForHttp();


//                mHandler.obtainMessage(DATASUCCESS).sendToTarget();
//                intent = new Intent(UserDataActivity.this, MainActivity.class);
//                startActivity(intent);

                break;

            default:
                break;
        }
    }

    private void upDataForHttp() {

        Log.e("OKGO", "username: ##"+facebookName );
        Log.e("OKGO", "date: ##"+date );
        Log.e("OKGO", "time: ##"+time2 );
        Log.e("OKGO", "gender: ##"+gender );
        Log.e("OKGO", "facebookid: ##"+facebookId );
        Log.e("OKGO", "zone: ##"+textUserTimezone.getText().toString() );


        OkGo.<String>post(Urls.Url_UserData).tag(this)
//                .isSpliceUrl(true)
                .params("username", facebookName)
                .params("date", date)
                .params("time", time2)
                .params("gender",gender)
                .params("facebookid", facebookId)
                .params("zone",  textUserTimezone.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("OkGo", "Success: @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
                        gson = new Gson();
                        userDataBean = gson.fromJson(response.body(),UserDataBean.class);
                        if (userDataBean.getStatus().equals("success")){
                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();
                        }else if (userDataBean.getStatus().equals("fail")){
                            Toast.makeText(UserDataActivity.this,"发送失败" ,Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("OkGo", "onError:############################### "+response.getException() );
//                        mHandler.obtainMessage(DATAFELLED).sendToTarget();
                    }
                });
    }

    private void upData() {
        Log.i("OkGo", "date: " + textUserBirth.getText().toString());

        String birth = textUserBirth.getText().toString();
        //出生日期
        int indexYear = birth.indexOf("日");
        String date1 = birth.substring(0, indexYear);
        String date2 = date1.replace("年", "-");
        date = date2.replace("月", "-");


        //出生时间
        int index = birth.indexOf(" ");
        String time1 = birth.substring(index+1, birth.length() - 1);
        time2 = time1+ ":00:00" ;
//
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    intent = new Intent(UserDataActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case DATAFELLED:
                    upDataForHttp();
                    break;
            }
        }
    };

    //選擇時區
    private void initNoLinkOptionsPicker() {
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                textUserTimezone.setText(timezone.get(options2));
                textUserTimezoneAffirm.setText(timezone.get(options2));
                time = true;
                doubleTime();

            }
        })
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

    //對第一頁的判斷
    private void doubleChose() {

        if (name && sex) {
            btAffirmName.setBackgroundResource(R.drawable.rounded_text_accent);
            btAffirmName.setEnabled(true);
        } else {
            btAffirmName.setBackgroundResource(R.drawable.rounded_text_gray);
            btAffirmName.setEnabled(false);
        }

    }

    //對第一頁時間的判斷
    private void doubleTime() {
        if (birth && time) {
            btAffirmBirth.setBackgroundResource(R.drawable.rounded_text_accent);
            btAffirmBirth.setEnabled(true);
        } else {
            btAffirmBirth.setBackgroundResource(R.drawable.rounded_text_gray);
            btAffirmBirth.setEnabled(false);
        }

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
//        doubleTimezone.add(-12);
//        doubleTimezone.add(-11);
//        doubleTimezone.add(-10);
//        doubleTimezone.add(-9.5);
//        doubleTimezone.add(-9);
//        doubleTimezone.add(-8);
//        doubleTimezone.add(-7);
//        doubleTimezone.add(-6);
//        doubleTimezone.add(-5);
//        doubleTimezone.add(-4);
//        doubleTimezone.add(-3.5);
//        doubleTimezone.add(-3);
//        doubleTimezone.add(-2);
//        doubleTimezone.add(-1);
//        doubleTimezone.add(0);
//        doubleTimezone.add(+1);
//        doubleTimezone.add(+2);
//        doubleTimezone.add(+3);
//        doubleTimezone.add(+3.5);
//        doubleTimezone.add(+4);
//        doubleTimezone.add(+4.5);
//        doubleTimezone.add(+5);
//        doubleTimezone.add(+5.5);
//        doubleTimezone.add(+6);
//        doubleTimezone.add(+7);
//        doubleTimezone.add(+8);
//        doubleTimezone.add(+9);
//        doubleTimezone.add(+10);
//        doubleTimezone.add(+11);
//        doubleTimezone.add(+12);
//        doubleTimezone.add(+13);
//        doubleTimezone.add(+14);
    }

    //渐变
    private void chengAnimation() {
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(2500);
        layoutUserData.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
//				redirectTo();
                Log.i("log", "触发了事件");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }

}
