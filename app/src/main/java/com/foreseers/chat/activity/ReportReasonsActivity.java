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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foreseers.chat.R;
import com.foreseers.chat.adapter.ImgAdapter;
import com.foreseers.chat.global.BaseActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 文字举报
 */
public class ReportReasonsActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.edittext) EditText edittext;
    @BindView(R.id.text_num) TextView textNum;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.text_ok) TextView textOk;
    private List<String> imglist = new ArrayList<>();
    private ImgAdapter imgAdapter;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_report_reasons);
        ButterKnife.bind(this);
        View view = getLayoutInflater().inflate(R.layout.footer_img_view, (ViewGroup) recyclerview.getParent(), false);

        view.setOnClickListener(getFooterClickListener());
        imgAdapter = new ImgAdapter(this, imglist);
        imgAdapter.addFooterView(view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview.setAdapter(imgAdapter);
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
    }

    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case 0:
                imgAdapter.setNewData(imglist);
                textNum.setText(imglist.size() + "");
                break;
        }
    }

    private View.OnClickListener getFooterClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions = new RxPermissions(ReportReasonsActivity.this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(ReportReasonsActivity.this)
                                            .choose(MimeType.ofAll(), false)//图片类型
                                            .theme(R.style.Matisse_Dracula)
                                            .countable(false)//true:选中后显示数字;false:选中后显示对号
                                            .capture(false)//选择照片时，是否显示拍照
                                            .captureStrategy(new CaptureStrategy(true, "com" + ".foreseers.chat.fileprovider", "test"))
                                            .maxSelectable(1)
                                            .addFilter(new GifSizeFilter(320, 320, Filter.K * Filter.K))
                                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                            .thumbnailScale(0.85f)
                                            .imageEngine(new GlideEngine())    // for glide-V4
                                            .setOnSelectedListener(new OnSelectedListener() {
                                                @Override
                                                public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("onSelected", "onSelected: " + "pathList=" + pathList);
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
                                            .forResult(REQUEST_CODE_CHOOSE_IMG);
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
            }
        };
    }

    private static final int REQUEST_CODE_CHOOSE_IMG = 24;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CODE_CHOOSE_IMG:
                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data)
                            .get(0);
                    imglist.add(path);
                    getHandler().obtainMessage(0)
                            .sendToTarget();
                }
        }
    }

    @OnClick(R.id.text_ok)
    public void onViewClicked() {
        Intent intent = new Intent();
        setResult(0x001, intent);
        finish();
    }
}
