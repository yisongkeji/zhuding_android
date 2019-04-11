package com.foreseers.chat.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.foreseers.chat.R;
import com.foreseers.chat.activity.ChangeUserDataActivity;
import com.foreseers.chat.activity.LifeBookActivity;
import com.foreseers.chat.activity.MyVipActivity;
import com.foreseers.chat.activity.SettingActivity;
import com.foreseers.chat.activity.SignActivity;
import com.foreseers.chat.activity.WipeHistoryActivity;
import com.foreseers.chat.adapter.AlbumAdapter;
import com.foreseers.chat.bean.AlbumBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.ShoppingIDBean;
import com.foreseers.chat.bean.UserDataBean;
import com.foreseers.chat.bill.IabBroadcastReceiver;
import com.foreseers.chat.bill.IabHelper;
import com.foreseers.chat.bill.IabResult;
import com.foreseers.chat.bill.Inventory;
import com.foreseers.chat.bill.Purchase;
import com.foreseers.chat.bill.SkuDetails;
import com.foreseers.chat.dialog.AddVIPDialog;
import com.foreseers.chat.global.BaseFragment;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.BitmapDispose;
import com.foreseers.chat.util.FileUtil;
import com.foreseers.chat.util.GifSizeFilter;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.Context.MODE_PRIVATE;
import static com.foreseers.chat.util.GooglePlayHelper.TAG;
import static com.foreseers.chat.util.GooglePlayHelper.base64EncodedPublicKey;
import static com.foreseers.chat.util.GooglePlayHelper.product1;
import static com.foreseers.chat.util.GooglePlayHelper.product2;
import static com.foreseers.chat.util.GooglePlayHelper.product3;

/**
 * 个人
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment  implements IabBroadcastReceiver.IabBroadcastListener {
    Unbinder unbinder;

    @BindView(R.id.recycler_img) RecyclerView recyclerImg;
    @BindView(R.id.image_head) RImageView imageHead;
    @BindView(R.id.layout_album) FrameLayout layoutAlbum;
    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_name2) TextView textName2;
    @BindView(R.id.text_sex) TextView textSex;
    @BindView(R.id.text_data) TextView textData;
    @BindView(R.id.text_age) TextView textAge;
    @BindView(R.id.text_ziwei) TextView textZiwei;
    @BindView(R.id.text_my_num) TextView textMyNum;
    @BindView(R.id.layout_change_user_data) FrameLayout layoutChangeUserData;
    @BindView(R.id.text_vip_day) TextView textVipDay;
    @BindView(R.id.layout_vip) LinearLayout layoutVip;
    @BindView(R.id.img_setting) ImageView imgSetting;
    @BindView(R.id.img_album) ImageView imgAlbum;
    @BindView(R.id.layout_wipe) LinearLayout layoutWipe;
    private final String TAG = "MyFragment@@@@@@";
    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int REQUEST_CODE_CHOOSE_IMG = 24;
    private static final int REQUEST_CODE_USER_DATA = 200;
    private static final int REQUEST_CODE_DELETEIMG = 201;
    public final int DATASUCCESS = 1;
    public final int DATAFELLED = 0;
    public final int USERHEADSUCCESS = 2;
    public final int USERIMGSUCCESS = 3;
    public final int SKUSUCCESS = 200;
    @BindView(R.id.text_sign) TextView textSign;
    @BindView(R.id.layout_sign) LinearLayout layoutSign;
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
    private int albumNum;
    private Intent intent;
    private IabHelper mHelper;
    private IabBroadcastReceiver mBroadcastReceiver;
    private ShoppingIDBean shoppingIDBean;
    private List<List<ShoppingIDBean.DataBean>> dataBeanList = new ArrayList<>();
    private List<ShoppingIDBean.DataBean> vipBeans = new ArrayList<>();
    private ArrayList<String> skuList = new ArrayList<String>();
    private TextView textday1;
    private TextView textprice360;
    private TextView textprice90;
    private TextView textprice30;
    private int viptype=0x002;
    private String packageName;
    private Map<String, String> pricemap;

    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void initViews() {
        packageName = getActivity().getPackageName();

        rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean){
                            //看本地有没有头像 有就加载
                            if (PreferenceManager.getInstance()
                                    .getHeadImgUrl() != null) {
                                File file = new File(Urls.ImgHead + "/" + PreferenceManager.getInstance()
                                        .getHeadImgUrl()
                                        .split("/")[4]);
                                GlideUtil.glideFile(MyApplication.getContext(), file, imageHead);

                                Log.d(TAG, "fileurl: " + PreferenceManager.getInstance()
                                        .getHeadImgUrl());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        albumAdapter = new AlbumAdapter(getActivity(), MyFragment.this, imgList);
        recyclerImg.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerImg.setAdapter(albumAdapter);
        layoutAlbum.setClickable(false);
        imgAlbum.setBackgroundResource(R.mipmap.icon_site_05);
    }

    @Override
    public void initDatas() {
        userid = PreferenceManager.getUserId(getActivity());
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
                        if (loginBean.getStatus()
                                .equals("success")) {
                            albumBean = gson.fromJson(response.body(), AlbumBean.class);
                            dataBean = albumBean.getData();
                            imgList = albumBean.getData()
                                    .getListimage();
                            vip = dataBean.getVip();
                            albumNum = dataBean.getCountnum();
                            Log.d(TAG, "onSuccess: " + dataBean.getHead());
                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
                        } else if (loginBean.getStatus()
                                .equals("fail")) {
                            getHandler().obtainMessage(DATAFELLED)
                                    .sendToTarget();
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

                if (layoutAlbum != null) {
                    if (albumNum >= 6) {
                        layoutAlbum.setClickable(false);
                        imgAlbum.setBackgroundResource(R.mipmap.icon_site_05);
                    } else {
                        layoutAlbum.setClickable(true);
                        imgAlbum.setBackgroundResource(R.mipmap.icon_site_02);
                    }

                    if (dataBean.getHead() != null) {
                        if (PreferenceManager.getInstance()
                                .getHeadImgUrl() == null) {
                            Log.i(TAG, "initViews: 加载网络图片");

                            GlideUtil.glideMatch(getActivity(), dataBean.getHead(), imageHead);

                            OkGo.<Bitmap>get(dataBean.getHead()).tag(this)
                                    .execute(new BitmapCallback() {
                                        @Override
                                        public void onSuccess(Response<Bitmap> response) {
                                            FileUtil.saveFile(response.body(), dataBean.getHead()
                                                    .split("/")[4]);
                                            PreferenceManager.getInstance()
                                                    .setHeadImgUrl(dataBean.getHead());
                                        }
                                    });
                        } else if (PreferenceManager.getInstance()
                                .getHeadImgUrl() != null) {
                            if (!PreferenceManager.getInstance()
                                    .getHeadImgUrl()
                                    .equals(dataBean.getHead())) {

                                Glide.with(getActivity())
                                        .load(dataBean.getHead())
                                        .error(R.mipmap.icon_me_loading_03)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(imageHead);
                                OkGo.<Bitmap>get(dataBean.getHead()).tag(this)
                                        .execute(new BitmapCallback() {
                                            @Override
                                            public void onSuccess(Response<Bitmap> response) {
                                                FileUtil.saveFile(response.body(), dataBean.getHead()
                                                        .split("/")[4]);
                                                PreferenceManager.getInstance()
                                                        .setHeadImgUrl(dataBean.getHead());
                                                Log.i(TAG, "initViews: 加载更新网络图片" + dataBean.getHead());
                                            }
                                        });
                            }
                        }
                    } else {
                        Glide.with(getActivity())
                                .load(dataBean.getHead())
                                .error(R.mipmap.icon_me_loading_03)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(imageHead);
                    }

                    textName.setText(dataBean.getUsername());
                    textMyNum.setText(dataBean.getNum() + "");
                    textVipDay.setText(dataBean.getVipday() + "");
                    textSign.setText(dataBean.getObligate());

                    textName2.setText(dataBean.getUsername());
                    textSex.setText(dataBean.getSex()
                                            .equals("F") ? "女" : "男");
                    textData.setText(dataBean.getDate());
                    textAge.setText(dataBean.getReservedint() + "");
                    textZiwei.setText(dataBean.getZiwei());

                    if (imgList == null || imgList.size() == 0) {
                        for (int i = 0; i < 1; i++) {
                            imgList.add("");
                        }
                    }

                    albumAdapter.setNewData(imgList);
                }

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
                                FileUtil.deleteFile(blurPath);
                                FileUtil.deleteFile(newpath);
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
                                getDataFromHttp();
                                //                                if (albumNum >= 6) {
                                //                                    layoutAlbum.setClickable(false);
                                //                                    imgAlbum.setBackgroundResource(R.mipmap.icon_site_05);
                                //                                    Toast.makeText(getActivity(), "相册已达数量上限", Toast.LENGTH_LONG)
                                //                                            .show();
                                //                                } else {
                                //                                    layoutAlbum.setClickable(true);
                                //                                    imgAlbum.setBackgroundResource(R.mipmap.icon_site_02);
                                //                                }
                                //
                                //                                albumAdapter.setNewData(imgList);
                            }
                        });

                break;
            case SKUSUCCESS:
                if (textName!=null){
                    if (skuList.size() > 0) {
                        try {
                            mHelper.queryInventoryAsync(true, skuList, null, mGotInventoryListener);
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            // complain("Error querying inventory. Another async operation in progress.");
                        }
                    }
                }

                break;
        }
    }

    @OnClick({R.id.image_head, R.id.layout_album, R.id.layout_change_user_data, R.id.layout_vip, R.id.img_setting, R.id.layout_wipe, R.id
            .layout_sign, R.id.lifebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.image_head:
                rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Matisse.from(MyFragment.this)
                                            .choose(MimeType.ofAll(), false)
                                            .theme(R.style.Matisse_Dracula)
                                            .countable(false)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com" + ".foreseers.chat.fileprovider", "test"))
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
            case R.id.layout_album://添加相冊
                if (albumNum >= 6) {
                    Toast.makeText(getActivity(), "相册已达数量上限", Toast.LENGTH_LONG)
                            .show();
                } else {
                    rxPermissions = new RxPermissions(getActivity());
                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .subscribe(new Observer<Boolean>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onNext(Boolean aBoolean) {
                                    if (aBoolean) {
                                        Matisse.from(MyFragment.this)
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
                                                //                                            .imageEngine(new GlideEngine())  // for glide-V3
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
                break;
            case R.id.lifebook://命书
                intent = new Intent(getActivity(), LifeBookActivity.class);
                intent.putExtra("type", 0);
                getActivity().startActivity(intent);
                break;
            case R.id.layout_change_user_data://修改個人信息

                intent = new Intent(getActivity(), ChangeUserDataActivity.class);

                MyFragment.this.startActivityForResult(intent, REQUEST_CODE_USER_DATA);
                break;

            case R.id.layout_vip://添加vip

                if (vip == 1) {
                    String[] arr = dataBean.getViptime()
                            .split(" ");

                    intent = new Intent(getActivity(), MyVipActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("head", dataBean.getHead());
                    bundle.putString("day", arr[0]);
                    bundle.putString("name", dataBean.getUsername());

                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                } else {
                    mHelper = new IabHelper(getContext(), base64EncodedPublicKey);

                    // enable debug logging (for a production application, you should set this to false).
                    mHelper.enableDebugLogging(true);
                    mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                        public void onIabSetupFinished(IabResult result) {

                            if (!result.isSuccess()) {
                                // Oh noes, there was a problem.
                                //                    complain("Problem setting up in-app billing: " + result);
                                return;
                            }

                            // Have we been disposed of in the meantime? If so, quit.
                            if (mHelper == null) {
                                return;
                            }
                            mBroadcastReceiver = new IabBroadcastReceiver(MyFragment.this);
                            IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                            getActivity().registerReceiver(mBroadcastReceiver, broadcastFilter);

                            // IAB is fully set up. Now, let's get an inventory of stuff we own.
                            //                MyLog.d(TAG, "Setup successful. Querying inventory.");

                            OkGo.<String>get(Urls.Url_ShoppingID).tag(this)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            Gson gson = new Gson();
                                            LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                                            if (loginBean.getStatus()
                                                    .equals("success")) {
                                                shoppingIDBean = gson.fromJson(response.body(), ShoppingIDBean.class);
                                                dataBeanList = shoppingIDBean.getData();

                                                for (int i = 0; i < dataBeanList.size(); i++) {
                                                    if (dataBeanList.get(i)
                                                            .get(0)
                                                            .getType()
                                                            .equals("vip")) {
                                                        vipBeans = dataBeanList.get(i);
                                                        for (int j = 0; j < vipBeans.size(); j++) {
                                                            skuList.add(vipBeans.get(j)
                                                                                .getGoogleID());
                                                        }
                                                    }

                                                }
                                                getHandler().obtainMessage(SKUSUCCESS)
                                                        .sendToTarget();
                                            }
                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);
                                            Log.i(TAG, "onError: ");
                                        }
                                    });
                        }
                    });
                        addVIPDialog = new AddVIPDialog(getActivity(), R.style.MyDialog ,  new AddVIPDialog.LeaveMyDialogListener() {

                            @Override
                            public void onClick(View view) {

                                switch (view.getId()) {
                                    case R.id.layout_vip1:

                                        viptype = 0x001;

                                        textday1 = view.findViewById(R.id.text_day1);
                                        textprice360 = view.findViewById(R.id.text_price360);

                                        break;
                                    case R.id.layout_vip2:
                                        viptype=0x002;
                                        textprice90 = view.findViewById(R.id.text_price90);

                                        break;
                                    case R.id.layout_vip3:
                                        viptype=0x003;
                                        textprice30 = view.findViewById(R.id.text_price30);
                                        break;
                                    case R.id.button_vip:

                                        String payload = "";
                                        String productId = "";
                                        switch (viptype){
                                            case 0x001:
                                                productId = packageName + product1;
                                                break;
                                            case 0x002:
                                                productId = packageName + product2;
                                                break;
                                            case 0x003:
                                                productId = packageName + product3;
                                                break;
                                        }
                                        if (productId != null && !productId.isEmpty()) {
                                            Log.d(TAG, "onViewClicked-productId: " + productId);
                                            try {
                                                mHelper.launchPurchaseFlow(getActivity(), productId, 1002, mPurchaseFinishedListener, payload);
                                            } catch (IabHelper.IabAsyncInProgressException e) {
                                                //                    complain("Error launching purchase flow. Another async operation in progress.");
                                            }
                                        }

                                        addVIPDialog.dismiss();

                                        break;
                                }
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

            case R.id.layout_wipe://谁擦过我
                intent = new Intent(getActivity(), WipeHistoryActivity.class);
                intent.putExtra("vip", vip);
                getActivity().startActivity(intent);
                break;
            case R.id.layout_sign://签名
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

        Log.i("onActivityResult", "onActivityResult:requestCode " + requestCode + "   resultCode" + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE: //头像

                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data)
                            .get(0);
                    Glide.with(this)
                            .load(path)
                            .into(imageHead);
                    try {

                        FileInputStream fis = new FileInputStream(path);
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);
                        //                        Bitmap compressbitmap = compressImage(bitmap);
                        newpath = BitmapDispose.saveBitmap(bitmap, 1);

                        Bitmap blurBitmap = BitmapDispose.blurBitmap(getActivity(), bitmap, 25);
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
                                    UserDataBean userDataBean = gson.fromJson(response.body(), UserDataBean.class);
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
                                    sharedPreferences.edit()
                                            .putString("url", userDataBean.getData()
                                                    .getHead())
                                            .commit();

                                    getHandler().obtainMessage(USERHEADSUCCESS)
                                            .sendToTarget();
                                }
                            });
                }
                break;

            case REQUEST_CODE_CHOOSE_IMG://相册
                if (resultCode == RESULT_OK) {
                    String path = Matisse.obtainPathResult(data)
                            .get(0);
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
                                    getHandler().obtainMessage(USERIMGSUCCESS)
                                            .sendToTarget();
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



            case 10002:

                if (mHelper == null) {
                    return;
                }

                // Pass on the activity result to the helper for handling
                if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
                    // not handled, so handle it ourselves (here's where you'd
                    // perform any handling of activity results not related to in-app
                    // billing...
                    super.onActivityResult(requestCode, resultCode, data);
                } else {

                }

            break;

            default:
                break;
        }
    }



    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            //            MyLog.d(TAG, "Query inventory finished.");


            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) {
                return;
            }

            // Is it a failure?
            if (result.isFailure()) {
                //                complain("Failed to query inventory: " + result);
                return;
            }
            pricemap = new HashMap<>();
            for (String sku : skuList) {
                if (inventory.hasDetails(sku)) {
                    SkuDetails details = inventory.getSkuDetails(sku);
                    switch (details.getSku()) {
                        case "com.foreseers.chat.vip360":
                            pricemap.put("360", details.getPrice());
//                            textprice360.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.vip90":
                            pricemap.put("90", details.getPrice());
//                            textprice90.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.vip30":
                            pricemap.put("30", details.getPrice());
//                            textprice30.setText(details.getPrice());
                            break;
                    }
                }
            }
            addVIPDialog.upData(pricemap);
            // Check for item delivery
            // 检查物品交付情况
            List<Purchase> allpurchasedItems = inventory.getAllPurchases();
            for (Purchase item : allpurchasedItems) {
                try {
                    mHelper.consumeAsync(inventory.getPurchase(item.getSku()), mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    //                    complain("Error consuming item. Another async operation in progress.");
                }
            }
            //            Purchase itemPurchase = inventory.getPurchase(packageName + product1);
            //            if (itemPurchase != null && verifyDeveloperPayload(itemPurchase)) {
            //                try {
            //                    mHelper.consumeAsync(inventory.getPurchase(packageName + product1), mConsumeFinishedListener);
            //                } catch (IabHelper.IabAsyncInProgressException e) {
            ////                    complain("Error consuming item. Another async operation in progress.");
            //                }
            //                return;
            //            }
        }
    };

    // Callback for when a purchase is finished
    // 当购买完成时的回调
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) {
                return;
            }

            if (result.isFailure()) {
                //                complain("Error purchasing: " + result);

                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                //                complain("Error purchasing. Authenticity verification failed.");
                return;
            }
            try {
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            } catch (IabHelper.IabAsyncInProgressException e) {
                //                complain("Error consuming item. Another async operation in progress.");
                return;
            }
            //            MyLog.d(TAG, "Purchase successful.");
        }
    };

    // Called when consumption is complete
    // 消费完成时调用
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) {
                return;
            }

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit

                try {
                    final String productId = purchase.getSku();
                    String purchaseToken = purchase.getToken();
                    Log.i(TAG, "onConsumeFinished: productId" + productId + "     purchaseToken" + purchaseToken);
                    OkGo.<String>post(Urls.Url_Shopping).tag(this)
                            .params("userid", PreferenceManager.getUserId(getActivity()))
                            .params("productId", productId)
                            .params("purchaseToken", purchaseToken)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                }
                            });
                } catch (Exception e) {
                }
            } else {
                //                complain("Error while consuming: " + result);
            }
            //            MyLog.d(TAG, "End consumption flow.");
        }
    };

    /**
     * Verifies the developer payload of a purchase.
     * 验证购买的开发人员有效负载。
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }
    @Override
    public void receivedBroadcast() {
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            //            complain("Error querying inventory. Another async operation in progress.");
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        //        getDataFromHttp();
    }

//    private Bitmap compressImage(Bitmap image) {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        Log.i("######", "compressImage: " + baos.toByteArray().length);
//        while (baos.toByteArray().length / 1024 > 400) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            options -= 10;//每次都减少10
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            Log.i("######", "compressImage: " + baos.toByteArray().length);
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//        //把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }

//    private Bitmap comp(Bitmap image) {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory
//            // .decodeStream）时溢出
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 1280f;//这里设置高度为800f
//        float ww = 768f;//这里设置宽度为480f
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0) {
//            be = 1;
//        }
//        newOpts.inSampleSize = be;//设置缩放比例
//        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        isBm = new ByteArrayInputStream(baos.toByteArray());
//        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
//        //        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
//        return bitmap;//压缩好比例大小后再进行质量压缩
//    }

    /**
     *
     *
     */

    @Override
    public void onStop() {
        super.onStop();
        Log.i("shengming", "onStop: MyFragment ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("shengming", "onDestroy: MyFragment ");
        OkGo.cancelTag(OkGo.getInstance()
                               .getOkHttpClient(), this);
        if (textName!=null){
            if (skuList != null) {
                skuList.clear();
            }
            if (mBroadcastReceiver != null) {
                getActivity().unregisterReceiver(mBroadcastReceiver);
            }

            // very important:
            if (mHelper != null) {
                mHelper.disposeWhenFinished();
                mHelper = null;
            }
        }

    }

    /**
     * 申请权限
     */
//    private void initauthority() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
//            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
//        }
//    }
}
