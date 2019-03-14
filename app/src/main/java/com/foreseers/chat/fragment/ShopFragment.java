package com.foreseers.chat.fragment;


import android.app.PendingIntent;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.android.vending.billing.IInAppBillingService;
import com.foreseers.chat.R;
import com.foreseers.chat.adapter.ViewAdapter;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.ShopBean;
import com.foreseers.chat.bean.UserCanumsNumBean;
import com.foreseers.chat.global.BaseMainFragment;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.foreseers.chat.util.GooglePayHelper.*;

/**
 * 商店
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends BaseMainFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.text_num)
    TextView textNum;
    Unbinder unbinder;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.button_1)
    Button button1;
    @BindView(R.id.button_2)
    Button button2;
    @BindView(R.id.button_3)
    Button button3;
    @BindView(R.id.button_prop1)
    Button buttonProp1;
    @BindView(R.id.button_prop2)
    Button buttonProp2;
    @BindView(R.id.button_prop3)
    Button buttonProp3;
    @BindView(R.id.price_vip360)
    TextView priceVip360;
    @BindView(R.id.price_vip90)
    TextView priceVip90;
    @BindView(R.id.price_vip30)
    TextView priceVip30;
    private LoginBean loginBean;
    private UserCanumsNumBean userCanumsNumBean;
    private List<View> viewList = new ArrayList<View>();
    private PagerAdapter viewAdapter;
    private LayoutInflater inflater;
    private int num;
    private String packageName;
    private IInAppBillingService mService;
    private ServiceConnection mServiceConn;
    private BroadcastReceiver mBroadcastReceiver;
    private ArrayList a;
    private String productId;
    private String price;
    private ArrayList<String> obj;
    private ArrayList<ShopBean >  shopBeans=new ArrayList<>();


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initViews() {
        packageName = getActivity().getPackageName();
        inflater = LayoutInflater.from(getActivity());
        View view1 = inflater.inflate(R.layout.view_page1, null);
        View view2 = inflater.inflate(R.layout.view_page2, null);
        View view3 = inflater.inflate(R.layout.view_page3, null);
        View view4 = inflater.inflate(R.layout.view_page4, null);
        View view5 = inflater.inflate(R.layout.view_page5, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);

        viewAdapter = new ViewAdapter(viewList);
        viewpager.setAdapter(viewAdapter);
        viewpager.setOnPageChangeListener(this);


        mServiceConn = new MyServiceConnection();
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");

        getActivity().bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        mBroadcastReceiver = new MyBroadcastReceiver();
    }

    @Override
    public void initDatas() {
        OkGo.<String>post(Urls.Url_UserCanumsNum).tag(this)
                .params("userid", GetLoginTokenUtil.getUserId(getActivity()))
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            userCanumsNumBean = gson.fromJson(response.body(), UserCanumsNumBean.class);
                            num = userCanumsNumBean.getData().getNums();
                            getHandler().obtainMessage(DATASUCCESS).sendToTarget();

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
                Log.i("shengming", "handler: ShopFragment ");
                if (textNum != null) {
                    textNum.setText(num + "");
                }
                break;
            case 0x999:
                if (msg.arg1 == 0) {
                    showLog("拉取vip信息成功");
                } else {
                    showLog("拉取vip信息失败");
                }
                break;
            case 0x998:
                a = (ArrayList) msg.obj;
                productId = (String) a.get(0);
                price = (String) a.get(1);
                showLog(productId + "的价格：" + price);
                priceVip360.setText(price);
                break;
            case 0x997:
                a = (ArrayList) msg.obj;
                productId = (String) a.get(0);
                price = (String) a.get(1);
                showLog(productId + "的价格：" + price);
                priceVip90.setText(price);
                break;
            case 0x996:
                a = (ArrayList) msg.obj;
                productId = (String) a.get(0);
                price = (String) a.get(1);
                showLog(productId + "的价格：" + price);
                priceVip30.setText(price);
                break;
        }
    }

    @OnClick({R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_prop1, R.id.button_prop2, R.id.button_prop3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                try {
                    showLog("开始请求购买360");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName + product1, ITEM_TYPE_INAPP, developerPayload);
                    int response_code = buyIntentBundle.getInt("RESPONSE_CODE");
                    showLog(response_code + "");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_2:
                try {
                    showLog("开始请求购买90");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName + product2, ITEM_TYPE_INAPP, developerPayload);
                    int response_code = buyIntentBundle.getInt("RESPONSE_CODE");
                    showLog(response_code + "");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_3:
                try {
                    showLog("开始请求购买30");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName + product3, ITEM_TYPE_INAPP, developerPayload);
                    int response_code = buyIntentBundle.getInt("RESPONSE_CODE");
                    showLog(response_code + "");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_prop1:
                break;
            case R.id.button_prop2:
                break;
            case R.id.button_prop3:
                break;
        }
    }

    public class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            // check for in-app billing v3 support
            try {
                int response = mService.isBillingSupported(3, packageName, ITEM_TYPE_INAPP);

                if (response == 0) {
                    showLog("关联Google play store成功");
                } else {
                    showLog("关联Google play store不成功");
                }
                if (response == 0) {
                    showLog("正在获取用户已经拥有的商品");
                    Bundle purchases = mService.getPurchases(3, packageName, ITEM_TYPE_INAPP, null);
                    int response_code = (int) purchases.get("RESPONSE_CODE");//判断是否请求成功！！！
                    if (response_code == 0) {
                        showLog("已成功获取用户已经拥有的商品");
                        ArrayList<String> ownedSkus = purchases.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                        for (String thisResponse : ownedSkus) {
                            if (thisResponse.equals(product1)) {
//                                isVip.setText("是");
                            }
                            showLog("拥有" + thisResponse);
                        }
                    }
                    showLog("开始拉取vip的商品信息。。。");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> skuList = new ArrayList<String>();
                            skuList.add(packageName + product1);
                            skuList.add(packageName + product2);
                            skuList.add(packageName + product3);
                            Bundle querySkus = new Bundle();
                            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                            try {
                                Bundle skuDetails = mService.getSkuDetails(3, packageName, ITEM_TYPE_INAPP, querySkus);
                                int response = skuDetails.getInt("RESPONSE_CODE");
                                Message message = new Message();
                                message.what = 0x999;
                                message.arg1 = response;
                                getHandler().sendMessage(message);
                                if (response == 0) {
                                    ArrayList<String> responseList = skuDetails.getStringArrayList("DETAILS_LIST");
                                    showLog("list:  " + responseList);
                                    for (String thisResponse : responseList) {
                                        JSONObject object = new JSONObject(thisResponse);
                                        String sku = object.getString("productId");
                                        String price = object.getString("price");
                                        showLog("object######:  " + object.toString());
                                        if (sku.equals(packageName + product1)) {
                                            obj = new ArrayList<>();
                                            showLog("listobj:  " + obj);
                                            obj.add(0, sku);
                                            obj.add(1, price);
                                            Message message1 = new Message();
                                            message1.what = 0x998;
                                            message1.obj = obj;
                                            getHandler().sendMessage(message1);
                                        }
                                        if (sku.equals(packageName + product2)) {
                                            obj = new ArrayList<>();
                                            showLog("listobj:  " + obj);
                                            obj.add(0, sku);
                                            obj.add(1, price);
                                            Message message1 = new Message();
                                            message1.what = 0x997;
                                            message1.obj = obj;
                                            getHandler().sendMessage(message1);
                                        }
                                        if (sku.equals(packageName + product3)) {
                                            obj = new ArrayList<>();
                                            showLog("listobj:  " + obj);
                                            obj.add(0, sku);
                                            obj.add(1, price);
                                            Message message1 = new Message();
                                            message1.what = 0x996;
                                            message1.obj = obj;
                                            getHandler().sendMessage(message1);
                                        }
                                    }
                                }

                            } catch (RemoteException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (RemoteException e) {
                showLog(e.toString());
            }

        }


    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            showLog("正在获取用户已经拥有的商品");
            Bundle purchases = null;
            try {
                purchases = mService.getPurchases(3, packageName, ITEM_TYPE_INAPP, null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            int response_code = (int) purchases.get("RESPONSE_CODE");//判断是否请求成功！！！
            if (response_code == 0) {
                showLog("已成功获取用户已经拥有的商品");
                ArrayList<String> ownedSkus = purchases.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                for (String thisResponse : ownedSkus) {
                    if (thisResponse.equals(product1)) {
//                    isVip.setText("是");
                        showLog("拥有" + thisResponse);
                        return;
                    }
                }
//            isVip.setText("否");
            }
        }
    }


    public void showLog(String s) {
        Log.d(TAG, "showLog: " + "\n" + s);
    }

    @Override   //滚动时调用
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override  //当页卡被选中时调用
    public void onPageSelected(int i) {

        switch (i) {
            case 0:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;

            case 1:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;
            case 2:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;
            case 3:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;
            case 4:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_03);
                break;

        }
    }

    @Override//当滚动状态改变时被调用
    public void onPageScrollStateChanged(int i) {
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter promoFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
        getActivity().registerReceiver(mBroadcastReceiver, promoFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mService != null) {
            getActivity().unbindService(mServiceConn);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        viewList.clear();
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                showLog("回调成功");

                int Code = data.getIntExtra("RESPONSE_CODE", 0);
                switch (Code) {
                    case 0:
                        showLog("付款成功");
//                        isVip.setText("是");
                        String inapp_purchase_data = data.getStringExtra("INAPP_PURCHASE_DATA");
                        showLog("订单详情：" + inapp_purchase_data);
                        break;
                    case 7:
                        showLog("你已经拥有该商品");
                        break;
                    default:
                        showLog(Code + " ");
                }
            } else {
                showLog("回调失败");
            }
        }
    }
}
