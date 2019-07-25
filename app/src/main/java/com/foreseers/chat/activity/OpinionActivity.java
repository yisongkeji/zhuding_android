package com.foreseers.chat.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.GifSizeFilter;
import com.foreseers.chat.view.widget.MyTitleBar;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 意见反馈
 */
public class OpinionActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.edittext) EditText edittext;
    @BindView(R.id.textnum) TextView textnum;
    @BindView(R.id.img_thirdly) ImageView imgThirdly;
    @BindView(R.id.layout_cancel3) FrameLayout layoutCancel3;
    @BindView(R.id.layout_thirdly) FrameLayout layoutThirdly;
    @BindView(R.id.img_second) ImageView imgSecond;
    @BindView(R.id.layout_cancel2) FrameLayout layoutCancel2;
    @BindView(R.id.layout_second) FrameLayout layoutSecond;
    @BindView(R.id.img_first) ImageView imgFirst;
    @BindView(R.id.layout_cancel1) FrameLayout layoutCancel1;
    @BindView(R.id.layout_first) FrameLayout layoutFirst;
    @BindView(R.id.text_ok) TextView textOk;
    private RxPermissions rxPermissions;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int REQUEST_CODE_CHOOSE2 = 24;
    private static final int REQUEST_CODE_CHOOSE3 = 25;
    private String first;
    private String second;
    private String thirdly;

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
        setContentView(R.layout.activity_opinion);
        ButterKnife.bind(this);
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
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                textnum.setText(String.valueOf(edittext.getText().length()));
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    @OnClick({R.id.layout_first, R.id.layout_second, R.id.layout_thirdly, R.id.layout_cancel1, R.id.layout_cancel2, R.id.layout_cancel3, R.id
            .text_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_first:
                rxPermissions = new RxPermissions(OpinionActivity.this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(OpinionActivity.this)
                                            .choose(MimeType.ofAll(), false)
                                            .theme(R.style.Matisse_Dracula)
                                            .countable(false)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com.foreseers.chat.fileprovider", "test"))
                                            .maxSelectable(1)
                                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                            .thumbnailScale(0.85f)
                                            //                                            .imageEngine(new GlideEngine())  // for glide-V3
                                            .imageEngine(new GlideEngine())    // for glide-V4
                                            .setOnSelectedListener(new OnSelectedListener() {
                                                @Override
                                                public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                                                }
                                            })
                                            .originalEnable(true)
                                            .maxOriginalSize(10)
                                            .autoHideToolbarOnSingleTap(true)
                                            .setOnCheckedListener(new OnCheckedListener() {
                                                @Override
                                                public void onCheck(boolean isChecked) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                                }
                                            })
                                            .forResult(REQUEST_CODE_CHOOSE);
                                } else {
                                    Toast.makeText(getActivity(), R.string.permission_request_denied, Toast.LENGTH_LONG)
                                            .show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                break;

            case R.id.layout_second:
                rxPermissions = new RxPermissions(OpinionActivity.this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(OpinionActivity.this)
                                            .choose(MimeType.ofAll(), false)
                                            .theme(R.style.Matisse_Dracula)
                                            .countable(false)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com.foreseers.chat.fileprovider", "test"))
                                            .maxSelectable(1)
                                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                            .thumbnailScale(0.85f)
                                            //                                            .imageEngine(new GlideEngine())  // for glide-V3
                                            .imageEngine(new GlideEngine())    // for glide-V4
                                            .setOnSelectedListener(new OnSelectedListener() {
                                                @Override
                                                public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                                                }
                                            })
                                            .originalEnable(true)
                                            .maxOriginalSize(10)
                                            .autoHideToolbarOnSingleTap(true)
                                            .setOnCheckedListener(new OnCheckedListener() {
                                                @Override
                                                public void onCheck(boolean isChecked) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                                }
                                            })
                                            .forResult(REQUEST_CODE_CHOOSE2);
                                } else {
                                    Toast.makeText(getActivity(), R.string.permission_request_denied, Toast.LENGTH_LONG)
                                            .show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                break;
            case R.id.layout_thirdly:
                rxPermissions = new RxPermissions(OpinionActivity.this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(OpinionActivity.this)
                                            .choose(MimeType.ofAll(), false)
                                            .theme(R.style.Matisse_Dracula)
                                            .countable(false)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com.foreseers.chat.fileprovider", "test"))
                                            .maxSelectable(1)
                                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                            .thumbnailScale(0.85f)
                                            //                                            .imageEngine(new GlideEngine())  // for glide-V3
                                            .imageEngine(new GlideEngine())    // for glide-V4
                                            .setOnSelectedListener(new OnSelectedListener() {
                                                @Override
                                                public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                                                }
                                            })
                                            .originalEnable(true)
                                            .maxOriginalSize(10)
                                            .autoHideToolbarOnSingleTap(true)
                                            .setOnCheckedListener(new OnCheckedListener() {
                                                @Override
                                                public void onCheck(boolean isChecked) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                                }
                                            })
                                            .forResult(REQUEST_CODE_CHOOSE3);
                                } else {
                                    Toast.makeText(getActivity(), R.string.permission_request_denied, Toast.LENGTH_LONG)
                                            .show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                break;
            case R.id.layout_cancel1:
                Glide.with(this)
                        .load(R.mipmap.icon_say_01)
                        .into(imgFirst);
                layoutCancel1.setVisibility(View.GONE);
                break;
            case R.id.layout_cancel2:
                Glide.with(this)
                        .load(R.mipmap.icon_say_01)
                        .into(imgSecond);
                layoutCancel2.setVisibility(View.GONE);
                break;
            case R.id.layout_cancel3:
                Glide.with(this)
                        .load(R.mipmap.icon_say_01)
                        .into(imgThirdly);
                layoutCancel3.setVisibility(View.GONE);
                break;
            case R.id.text_ok:
                Toast.makeText(this, getActivity().getResources().getString(R.string.up_success), Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE:

                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data)
                            .get(0);
                    Glide.with(this)
                            .load(path)
                            .into(imgFirst);
                    first = path;
                    layoutCancel1.setVisibility(View.VISIBLE);
                }
                break;
            case REQUEST_CODE_CHOOSE2:
                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data)
                            .get(0);
                    Glide.with(this)
                            .load(path)
                            .into(imgSecond);
                    second = path;
                    layoutCancel2.setVisibility(View.VISIBLE);
                }
                break;

            case REQUEST_CODE_CHOOSE3:
                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data)
                            .get(0);
                    Glide.with(this)
                            .load(path)
                            .into(imgThirdly);
                    thirdly = path;
                    layoutCancel3.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
