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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.android.vending.billing.IInAppBillingService;
import com.foreseers.chat.R;
import com.foreseers.chat.adapter.ViewAdapter;
import com.foreseers.chat.bean.GoogleCheckBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.ShoppingIDBean;
import com.foreseers.chat.bean.UserCanumsNumBean;
import com.foreseers.chat.global.BaseFragment;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.foreseers.chat.util.GooglePayHelper.*;

/**
 * 商店
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends BaseFragment implements ViewPager.OnPageChangeListener {


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
    @BindView(R.id.text_item3)
    TextView textItem3;
    @BindView(R.id.text_item2)
    TextView textItem2;
    @BindView(R.id.text_item1)
    TextView textItem1;
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

    public final int DATASUCCESS = 1;
    public final int DATAFELLED = 0;
    private ShoppingIDBean shoppingIDBean;
    private List<ShoppingIDBean.DataBean> vipBeans = new ArrayList<>();
    private List<ShoppingIDBean.DataBean> eraserBeans = new ArrayList<>();
    private List<List<ShoppingIDBean.DataBean>> dataBeanList = new ArrayList<>();
    private ArrayList<String> skuList = new ArrayList<String>();
    private String productId;
    private String purchaseToken;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
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


    }

    @Override
    public void initDatas() {
        OkGo.<String>get(Urls.Url_ShoppingID).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            shoppingIDBean = gson.fromJson(response.body(), ShoppingIDBean.class);
                            dataBeanList = shoppingIDBean.getData();

                            for (int i = 0; i < dataBeanList.size(); i++) {
                                if (dataBeanList.get(i).get(0).getType().equals("vip")) {
                                    vipBeans = dataBeanList.get(i);
                                    for (int j = 0; j < vipBeans.size(); j++) {
                                        skuList.add(vipBeans.get(j).getGoogleID());
                                    }
                                }
                                if (dataBeanList.get(i).get(0).getType().equals("item")) {
                                    eraserBeans = dataBeanList.get(i);
                                    for (int k = 0; k < eraserBeans.size(); k++) {
                                        skuList.add(eraserBeans.get(k).getGoogleID());
                                    }

                                }
                            }
                            Log.i(TAG, "onSuccess:\n skuList" + skuList.toString());

                            mServiceConn = new MyServiceConnection();
                            Intent serviceIntent = new Intent("com.android.vending.billing" +
                                    ".InAppBillingService.BIND");
                            serviceIntent.setPackage("com.android.vending");

                            getActivity().bindService(serviceIntent, mServiceConn, Context
                                    .BIND_AUTO_CREATE);
                        }
                    }
                });

        getCanumsNum();

    }

    private void getCanumsNum() {
        OkGo.<String>post(Urls.Url_UserCanumsNum).tag(this)
                .params("userid", PreferenceManager.getUserId(getActivity()))
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            userCanumsNumBean = gson.fromJson(response.body(), UserCanumsNumBean
                                    .class);
                            num = userCanumsNumBean.getData().getCountnums();
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
            case 0x995:
                if (priceVip360 != null) {

                    showLog("map" + msg.obj.toString());
                    Map<String, String> map = (Map<String, String>) msg.obj;

                    for (String key : map.keySet()) {
                        if (key.equals(packageName + product1)) {
                            priceVip360.setText(map.get(packageName + product1));
                        }
                        if (key.equals(packageName + product2)) {
                            priceVip90.setText(map.get(packageName + product2));
                        }
                        if (key.equals(packageName + product3)) {
                            priceVip30.setText(map.get(packageName + product3));
                        }
                        if (key.equals(packageName + item1)) {
                            showLog(map.keySet() + "的价格：" + map.get(packageName + item1));
                            textItem1.setText(map.get(packageName + item1));
                        }
                        if (key.equals(packageName + item2)) {
                            textItem2.setText(map.get(packageName + item2));
                        }
                        if (key.equals(packageName + item3)) {
                            textItem3.setText(map.get(packageName + item3));
                        }
                    }

                }
                break;
        }
    }

    @OnClick({R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_prop1, R.id.button_prop2,
            R.id.button_prop3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                try {
                    showLog("开始请求购买360");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName +
                            product1, ITEM_TYPE_INAPP, developerPayload);
                    int response_code = buyIntentBundle.getInt("RESPONSE_CODE");
                    showLog(response_code + "");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer
                                        .valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_2:
                try {
                    showLog("开始请求购买90");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName +
                            product2, ITEM_TYPE_INAPP, developerPayload);
                    int response_code = buyIntentBundle.getInt("RESPONSE_CODE");
                    showLog(response_code + "");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer
                                        .valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_3:
                try {
                    showLog("开始请求购买30");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName +
                            product3, ITEM_TYPE_INAPP, developerPayload);
                    int response_code = buyIntentBundle.getInt("RESPONSE_CODE");
                    showLog(response_code + "");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer
                                        .valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_prop1:
                try {
                    showLog("开始请求擦子200");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName +
                            item3, ITEM_TYPE_INAPP, developerPayload);
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer
                                        .valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_prop2:
                try {
                    showLog("开始请求购买擦子90");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName +
                            item2, ITEM_TYPE_INAPP, developerPayload);
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer
                                        .valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_prop3:
                try {
                    showLog("开始请求购买擦子30");
                    Bundle buyIntentBundle = mService.getBuyIntent(3, packageName, packageName +
                            item1, ITEM_TYPE_INAPP, developerPayload);
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    if (pendingIntent == null) {
                        showLog("错误");
                    } else {
                        showLog("成功");
                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer
                                        .valueOf(0), Integer.valueOf(0));
                    }
                } catch (RemoteException | IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
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
                        ArrayList<String> ownedSkus = purchases.getStringArrayList
                                ("INAPP_PURCHASE_ITEM_LIST");
                        for (String thisResponse : ownedSkus) {
                            if (thisResponse.equals(product1)) {

                            }
                            showLog("拥有" + thisResponse);
                        }
                        showLog("拥有list:" + ownedSkus.toString());
                    }
                    showLog("开始拉取商品信息。。。");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Bundle querySkus = new Bundle();
                            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                            try {
                                Bundle skuDetails = mService.getSkuDetails(3, packageName,
                                        ITEM_TYPE_INAPP, querySkus);
                                int response = skuDetails.getInt("RESPONSE_CODE");
                                Message message = new Message();
                                message.what = 0x999;
                                message.arg1 = response;
                                getHandler().sendMessage(message);
                                if (response == 0) {
                                    ArrayList<String> responseList = skuDetails
                                            .getStringArrayList("DETAILS_LIST");
                                    showLog("list:  " + responseList);
                                    Map<String, String> map = new HashMap<>();
                                    for (String thisResponse : responseList) {
                                        JSONObject object = new JSONObject(thisResponse);
                                        String sku = object.getString("productId");
                                        String price = object.getString("price");
                                        showLog("object######:  " + object.toString());
                                        map.put(sku, price);
                                    }
                                    Message message1 = new Message();
                                    message1.what = 0x995;
                                    message1.obj = map;
                                    getHandler().sendMessage(message1);
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

    /**
     * 获取用户已经拥有的商品
     */
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
                ArrayList<String> ownedSkus = purchases.getStringArrayList
                        ("INAPP_PURCHASE_ITEM_LIST");
                for (String thisResponse : ownedSkus) {
                    //已拥有商品是否是购买商品
                    if (thisResponse.equals(productId)) {
                        showLog("拥有" + thisResponse);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int response = mService.consumePurchase(3, packageName, purchaseToken);
                                    if (response == 0) {
                                        //消耗成功
                                        getCanumsNum();

                                    }
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                        return;
                    }
                }
//            isVip.setText("否");
            }
            showLog("已经拥有的商品获取失败");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                int Code = data.getIntExtra("RESPONSE_CODE", 0);
                showLog("回调成功+Code：" + Code);
                switch (Code) {
                    case 0:
                        showLog("付款成功");
                        String inapp_purchase_data = data.getStringExtra("INAPP_PURCHASE_DATA");
                        showLog("订单详情：" + inapp_purchase_data);
//                        OkGo.<String>post(Urls.Url_Shopping).tag(this)
//                                .params("signtureData", inapp_purchase_data)
//                                .params("type", "android")
//                                .execute(new StringCallback() {
//                                    @Override
//                                    public void onSuccess(Response<String> response) {
//                                        Gson gson = new Gson();
//                                        loginBean = gson.fromJson(response.body(), LoginBean.class);
//                                        if (loginBean.getStatus().equals("success")) {
//                                            GoogleCheckBean googleCheckBean = gson.fromJson(response.body(), GoogleCheckBean.class);
//                                            GoogleCheckBean.DataBean dataBean = googleCheckBean.getData();
//                                            productId = dataBean.getProductId();
//                                            purchaseToken = dataBean.getPurchaseToken();
//                                            if (dataBean.getStatus() == 1) {
//                                                mBroadcastReceiver = new MyBroadcastReceiver();
//                                                Toast.makeText(getActivity(),"购买成功",Toast.LENGTH_SHORT).show();
//                                            }else  {
//                                                Toast.makeText(getActivity(),"购买失败",Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//                                    }
//                                });
                        mBroadcastReceiver = new MyBroadcastReceiver();
                        break;
                    case 7:
                        showLog("你已经拥有该商品");
                        break;
                    default:
                        showLog(Code + " ");
                        break;
                }
            } else {
                showLog("回调失败");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBroadcastReceiver != null) {
            IntentFilter promoFilter = new IntentFilter("com.android.vending.billing" +
                    ".PURCHASES_UPDATED");
            getActivity().registerReceiver(mBroadcastReceiver, promoFilter);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBroadcastReceiver != null) {
            getActivity().unregisterReceiver(mBroadcastReceiver);
        }
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


}
