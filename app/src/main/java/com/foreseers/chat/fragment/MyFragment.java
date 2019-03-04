package com.foreseers.chat.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foreseers.chat.activity.ChangeUserDataActivity;
import com.foreseers.chat.activity.MyVipActivity;
import com.foreseers.chat.activity.SettingActivity;
import com.foreseers.chat.activity.SignActivity;
import com.foreseers.chat.activity.WipeHistoryActivity;
import com.foreseers.chat.adapter.AlbumAdapter;
import com.foreseers.chat.bean.AlbumBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.UserDataBean;
import com.foreseers.chat.dialog.AddVIPDialog;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.global.BaseMainFragment;
import com.foreseers.chat.util.BitmapDispose;
import com.foreseers.chat.util.FileUtil;
import com.foreseers.chat.util.GetLoginTokenUtil;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.Context.MODE_PRIVATE;

/**
 * 个人
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
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.img_album)
    ImageView imgAlbum;
    @BindView(R.id.layout_wipe)
    LinearLayout layoutWipe;


    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int REQUEST_CODE_CHOOSE_IMG = 24;
    private static final int REQUEST_CODE_USER_DATA = 200;
    private static final int REQUEST_CODE_DELETEIMG = 201;
    @BindView(R.id.text_sign)
    TextView textSign;
    @BindView(R.id.layout_sign)
    LinearLayout layoutSign;
    private AlbumBean albumBean;
    private AlbumAdapter albumAdapter;
    private RxPermissions rxPermissions;
    private String userid;
    private LoginBean loginBean;
    private AlbumBean.DataBean dataBean;
    private ArrayList<String> imgList = new ArrayList<>();
    private AddVIPDialog addVIPDialog;
    private String blurPath;
    private String blurImagePath;
    private int vip;
    private String newpath;

    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
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
        albumAdapter = new AlbumAdapter(getActivity(), MyFragment.this, imgList);
        recyclerImg.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        recyclerImg.setAdapter(albumAdapter);
    }

    @Override
    public void initDatas() {
        userid = GetLoginTokenUtil.getUserId(getActivity());
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
                            vip = dataBean.getVip();

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
//                    layoutAlbum.setClickable(false);
                    imgAlbum.setBackgroundResource(R.mipmap.icon_site_05);

                } else {
//                    layoutAlbum.setClickable(true);
                    imgAlbum.setBackgroundResource(R.mipmap.icon_site_02);
                }

                Glide.with(getActivity()).load(dataBean.getHead()).into(imageHead);

                textName.setText(dataBean.getUsername());
                textMyNum.setText(dataBean.getNum() + "");
                textVipDay.setText(dataBean.getVipday() + "");
                textSign.setText(dataBean.getObligate());



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

            case USERHEADSUCCESS:
                OkGo.<String>post(Urls.Url_UserBlurHead).tag(this)
                        .params("userid", userid)
                        .params("file", new File(blurPath))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
//
//                                FileUtil.deleteFile(blurPath);
//                                FileUtil.deleteFile(newpath);

                            }
                        });

                break;

            case USERIMGSUCCESS:
                OkGo.<String>post(Urls.Url_UserBlurImg).tag(this)
                        .params("userid", userid)
                        .params("file", new File(blurImagePath))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                FileUtil.deleteFile(blurImagePath);
                                FileUtil.deleteFile(newpath);
                                if (dataBean.getCountnum() >= 6) {
                                    layoutAlbum.setClickable(false);
                                    imgAlbum.setBackgroundResource(R.mipmap.icon_site_05);
                                    Toast.makeText(getActivity(), "相册已达数量上限", Toast.LENGTH_LONG)
                                            .show();
                                } else {
                                    layoutAlbum.setClickable(true);
                                    imgAlbum.setBackgroundResource(R.mipmap.icon_site_02);
                                }

                                albumAdapter.setNewData(imgList);
                            }
                        });

                break;
        }
    }

    @OnClick({R.id.image_head, R.id.layout_album, R.id.layout_change_user_data, R.id.layout_vip, R
            .id.img_setting, R.id.layout_wipe, R.id.layout_sign})
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
                                            .countable(false)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com" +
                                                    ".foreseers.chat.foreseers.fileprovider",
                                                    "test"))
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
                if (dataBean.getCountnum() >= 6) {
                    Toast.makeText(getActivity(), "相册已达数量上限", Toast.LENGTH_LONG).show();
                } else {
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
                                                .choose(MimeType.ofAll(), false)//图片类型
                                                .countable(false)//true:选中后显示数字;false:选中后显示对号
                                                .capture(false)//选择照片时，是否显示拍照
                                                .captureStrategy(new CaptureStrategy(true, "com" +
                                                        ".foreseers.chat.foreseers.fileprovider",
                                                        "test"))

                                                .maxSelectable(1)
                                                .addFilter(new GifSizeFilter(320, 320, Filter.K *
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
                                                        Log.e("onSelected", "onSelected: " +
                                                                "pathList=" +
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

                }
                break;

            case R.id.layout_change_user_data://修改個人信息

                Intent intent = new Intent(getActivity(), ChangeUserDataActivity.class);

                MyFragment.this.startActivityForResult(intent, REQUEST_CODE_USER_DATA);
                break;

            case R.id.layout_vip://添加vip

                if (vip == 1) {
                    String[] arr = dataBean.getViptime().split(" ");


                    intent = new Intent(getActivity(), MyVipActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("head", dataBean.getHead());
                    bundle.putString("day", arr[0]);
                    bundle.putString("name", dataBean.getUsername());

                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                } else {

                    addVIPDialog = new AddVIPDialog(getActivity(), R.style.MyDialog, new
                            AddVIPDialog
                            .LeaveMyDialogListener() {


                        @Override
                        public void onClick(View view) {
                            addVIPDialog.dismiss();
                        }
                    });
                    addVIPDialog.setCancelable(true);
                    addVIPDialog.show();
                }


                break;
            case R.id.img_setting://设置

                intent = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.layout_wipe:
                intent = new Intent(getActivity(), WipeHistoryActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.layout_sign:
                intent = new Intent(getActivity(), SignActivity.class);
                getActivity().startActivity(intent);
                break;

            default:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("onActivityResult", "onActivityResult:requestCode " + requestCode + "   resultCode"
                + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE: //头像

                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data).get(0);
                    Glide.with(this).load(path).into(imageHead);
                    try {

                        FileInputStream fis = new FileInputStream(path);
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);
                        Bitmap compressbitmap = compressImage(bitmap);
                        newpath = BitmapDispose.saveBitmap(compressbitmap, 1);

                        Bitmap blurBitmap = BitmapDispose.blurBitmap(getActivity(),
                                compressbitmap, 25);
                        blurPath = BitmapDispose.saveBitmap(blurBitmap, 0);

                        Log.i("blurPath", "blurPath: " + blurPath);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    OkGo.<String>post(Urls.Url_UserHead).tag(this)
                            .params("userid", userid)
                            .params("file", new File(newpath))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    Gson gson = new Gson();
                                    UserDataBean userDataBean = gson.fromJson(response.body(),
                                            UserDataBean.class);
                                    SharedPreferences sharedPreferences = getActivity()
                                            .getSharedPreferences("user",
                                                    MODE_PRIVATE);
                                    sharedPreferences.edit().putString("url", userDataBean
                                            .getData().getHead())
                                            .commit();

                                    getHandler().obtainMessage(USERHEADSUCCESS).sendToTarget();

                                }
                            });


                }
                break;

            case REQUEST_CODE_CHOOSE_IMG://相册
                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data).get(0);
                    Log.i("useridMyfragment", "userid: " + userid);


                    try {

                        FileInputStream fis = new FileInputStream(path);
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);
                        Bitmap blurBitmap = BitmapDispose.blurBitmap(getActivity(), bitmap, 25);
                        blurImagePath = BitmapDispose.saveBitmap(blurBitmap, 0);

                        Log.i("blurPath", "blurPath: " + blurImagePath);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    OkGo.<String>post(Urls.Url_UserImg).tag(this)
                            .params("userid", userid)
                            .params("file", new File(path))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    getHandler().obtainMessage(USERIMGSUCCESS).sendToTarget();

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

            case REQUEST_CODE_DELETEIMG:
                getDataFromHttp();
                break;
            default:
                break;
        }


    }


    @Override
    public void onResume() {
        super.onResume();
//        getDataFromHttp();
    }

    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        Log.i("######", "compressImage: " + baos.toByteArray().length);
        while (baos.toByteArray().length / 1024 > 400) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            Log.i("######", "compressImage: " + baos.toByteArray().length);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory
            // .decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1280f;//这里设置高度为800f
        float ww = 768f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
//        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
        return bitmap;//压缩好比例大小后再进行质量压缩
    }
}
