package com.foreseers.chat.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.foreseers.chat.activity.ChangeUserDataActivity;
import com.foreseers.chat.activity.MyVipActivity;
import com.foreseers.chat.adapter.AlbumAdapter;
import com.foreseers.chat.bean.AlbumBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.UserDataBean;
import com.foreseers.chat.dialog.AddVIPDialog;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.global.BaseMainFragment;
import com.foreseers.chat.util.GifSizeFilter;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruffian.library.widget.RImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseMainFragment {


    @BindView(R.id.recycler_img)
    RecyclerView recyclerImg;
    Unbinder unbinder;
    @BindView(R.id.image_head)
    RImageView imageHead;
    @BindView(R.id.layout_album)
    FrameLayout layoutAlbum;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_name2)
    TextView textName2;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_data)
    TextView textData;
    @BindView(R.id.text_age)
    TextView textAge;
    @BindView(R.id.text_ziwei)
    TextView textZiwei;
    @BindView(R.id.text_my_num)
    TextView textMyNum;
    @BindView(R.id.layout_change_user_data)
    FrameLayout layoutChangeUserData;
    @BindView(R.id.text_vip_day)
    TextView textVipDay;
    @BindView(R.id.layout_vip)
    LinearLayout layoutVip;

    private String facebookId;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int REQUEST_CODE_CHOOSE_IMG = 24;
    private static final int REQUEST_CODE_USER_DATA = 200;

    private AlbumBean albumBean;
    private AlbumAdapter albumAdapter;
    private RxPermissions rxPermissions;
    private String userid;
    private LoginBean loginBean;
    private AlbumBean.DataBean dataBean;
    private ArrayList<String> imgList = new ArrayList<>();
    private AddVIPDialog addVIPDialog;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
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
        getLoginToken();
        albumAdapter = new AlbumAdapter(getActivity(), imgList);
        recyclerImg.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        recyclerImg.setAdapter(albumAdapter);
    }

    @Override
    public void initDatas() {
        getDataFromHttp();
    }

    private void getDataFromHttp() {
        OkGo.<String>post(Urls.Url_My).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            albumBean = gson.fromJson(response.body(), AlbumBean.class);

                            dataBean = albumBean.getData();

                            imgList = albumBean.getData().getListimage();

                            getHandler().obtainMessage(DATASUCCESS).sendToTarget();


                        } else if (loginBean.getStatus().equals("fail")) {
                            getHandler().obtainMessage(DATAFELLED).sendToTarget();

                        }
                    }
                });

    }

    @Override
    public void installListeners() {

    }


    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case DATASUCCESS:

                if (dataBean.getCountnum() >= 6) {
                    layoutAlbum.setClickable(false);
                } else {
                    layoutAlbum.setClickable(true);
                }

                Glide.with(getActivity()).load(dataBean.getHead()).into(imageHead);

                textName.setText(dataBean.getUsername());
                textMyNum.setText(dataBean.getNum() + "");
                textVipDay.setText(dataBean.getVipday()+"");

                textName2.setText(dataBean.getUsername());
                textSex.setText(dataBean.getSex().equals("F") ? "女" : "男");
                textData.setText(dataBean.getDate());
                textAge.setText(dataBean.getReservedint() + "");
                textZiwei.setText(dataBean.getZiwei());


                if (imgList == null || imgList.size() == 0) {
                    for (int i = 0; i < 1; i++) {
                        imgList.add("");
                    }
                }


                albumAdapter.setNewData(imgList);
                break;

            case DATAFELLED:

                break;
        }
    }

    @OnClick({R.id.image_head, R.id.layout_album, R.id.layout_change_user_data, R.id.layout_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.image_head:
                rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                        .permission.CAMERA)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(MyFragment.this)
                                            .choose(MimeType.ofAll(), false)
                                            .countable(true)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com" +
                                                    ".foreseers.chat.fileprovider", "test"))
                                            .maxSelectable(1)
                                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K *
                                                    Filter.K))
                                            .gridExpectedSize(getResources()
                                                    .getDimensionPixelSize(R.dimen
                                                            .grid_expected_size))
                                            .restrictOrientation(ActivityInfo
                                                    .SCREEN_ORIENTATION_PORTRAIT)
                                            .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                                            .imageEngine(new GlideEngine())    // for glide-V4
                                            .setOnSelectedListener(new OnSelectedListener() {
                                                @Override
                                                public void onSelected(
                                                        @NonNull List<Uri> uriList, @NonNull
                                                        List<String> pathList) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("onSelected", "onSelected: pathList=" +
                                                            pathList);

                                                }
                                            })
                                            .originalEnable(true)
                                            .maxOriginalSize(10)
                                            .autoHideToolbarOnSingleTap(true)
                                            .setOnCheckedListener(new OnCheckedListener() {
                                                @Override
                                                public void onCheck(boolean isChecked) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("isChecked", "onCheck: isChecked=" +
                                                            isChecked);
                                                }
                                            })
                                            .forResult(REQUEST_CODE_CHOOSE);


                                } else {
                                    Toast.makeText(getActivity(), R.string
                                            .permission_request_denied, Toast.LENGTH_LONG)
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
            case R.id.layout_album://添加相冊

                rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                        .permission.CAMERA)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(MyFragment.this)
                                            .choose(MimeType.ofAll(), false)
                                            .countable(true)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com" +
                                                    ".foreseers.chat.fileprovider", "test"))
                                            .maxSelectable(1)
                                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K *
                                                    Filter.K))
                                            .gridExpectedSize(getResources()
                                                    .getDimensionPixelSize(R.dimen
                                                            .grid_expected_size))
                                            .restrictOrientation(ActivityInfo
                                                    .SCREEN_ORIENTATION_PORTRAIT)
                                            .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                                            .imageEngine(new GlideEngine())    // for glide-V4
                                            .setOnSelectedListener(new OnSelectedListener() {
                                                @Override
                                                public void onSelected(
                                                        @NonNull List<Uri> uriList, @NonNull
                                                        List<String> pathList) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("onSelected", "onSelected: pathList=" +
                                                            pathList);

                                                }
                                            })
                                            .originalEnable(true)
                                            .maxOriginalSize(10)
                                            .autoHideToolbarOnSingleTap(true)
                                            .setOnCheckedListener(new OnCheckedListener() {
                                                @Override
                                                public void onCheck(boolean isChecked) {
                                                    // DO SOMETHING IMMEDIATELY HERE
                                                    Log.e("isChecked", "onCheck: isChecked=" +
                                                            isChecked);
                                                }
                                            })
                                            .forResult(REQUEST_CODE_CHOOSE_IMG);


                                } else {
                                    Toast.makeText(getActivity(), R.string
                                            .permission_request_denied, Toast.LENGTH_LONG)
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

            case R.id.layout_change_user_data://修改個人信息

                Intent intent = new Intent(getActivity(), ChangeUserDataActivity.class);

                MyFragment.this.startActivityForResult(intent, REQUEST_CODE_USER_DATA);
                break;

            case R.id.layout_vip://添加vip

                intent=new Intent(getActivity(),MyVipActivity.class);
                getActivity().startActivity(intent);


//                addVIPDialog = new AddVIPDialog(getActivity(), R.style.MyDialog, new AddVIPDialog
//                        .LeaveMyDialogListener() {
//
//
//                    @Override
//                    public void onClick(View view) {
//                        addVIPDialog.dismiss();
//                    }
//                });
//                addVIPDialog.setCancelable(true);
//                addVIPDialog.show();


                break;


            default:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("onActivityResult", "onActivityResult:requestCode " + requestCode + "   resultCode" + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE: //头像

                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data).get(0);
                    Glide.with(this).load(path).into(imageHead);

                    OkGo.<String>post(Urls.Url_UserHead).tag(this)
                            .params("facebookid", facebookId)
                            .params("file", new File(path))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    Gson gson = new Gson();
                                    UserDataBean userDataBean = gson.fromJson(response.body(), UserDataBean.class);
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",
                                            MODE_PRIVATE);
                                    sharedPreferences.edit().putString("url", userDataBean.getData().getHead())
                                            .commit();
                                }
                            });

                }
                break;

            case REQUEST_CODE_CHOOSE_IMG://相册
                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data).get(0);
                    Log.i("useridMyfragment", "userid: " + userid);
                    Glide.with(this).load(path).into(imageHead);

                    OkGo.<String>post(Urls.Url_UserImg).tag(this)
                            .params("userid", userid)
                            .params("file", new File(path))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    getDataFromHttp();
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    Log.i("okgo", "onError: " + response);
                                }
                            });
                }
                break;

            case REQUEST_CODE_USER_DATA:
                getDataFromHttp();
                break;

            default:
                break;
        }


    }

    /**
     * 获取登录token（facebookID）
     */

    public void getLoginToken() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("loginToken", MODE_PRIVATE);
        facebookId = userInfo.getString("token", null);
        userid = userInfo.getString("huanXinId", "");
    }

    @Override
    public void onResume() {
        super.onResume();
//        getDataFromHttp();
    }
}
