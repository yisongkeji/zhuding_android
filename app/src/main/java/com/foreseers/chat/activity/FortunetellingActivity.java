package com.foreseers.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.foreseers.chat.R;
import com.foreseers.chat.adapter.FortunetellingAdapter;
import com.foreseers.chat.bean.FortunetellingBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.ShopCheckBean;
import com.foreseers.chat.bean.ShoppingIDBean;
import com.foreseers.chat.bill.IabBroadcastReceiver;
import com.foreseers.chat.bill.IabHelper;
import com.foreseers.chat.bill.IabResult;
import com.foreseers.chat.bill.Inventory;
import com.foreseers.chat.bill.Purchase;
import com.foreseers.chat.bill.SkuDetails;
import com.foreseers.chat.fragment.ShopFragment;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foreseers.chat.util.GooglePlayHelper.TAG;
import static com.foreseers.chat.util.GooglePlayHelper.base64EncodedPublicKey;

/**
 * 命书测算清单
 */
public class FortunetellingActivity extends BaseActivity implements IabBroadcastReceiver.IabBroadcastListener {

    private String TAG = "FortunetellingActivity";
    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;

    private List<Integer> rgblist = new ArrayList();
    private List<Integer> titlelist = new ArrayList();
    private List<Integer> contentlist = new ArrayList();
    private FortunetellingBean fortunetellingBean;
    private FortunetellingBean.DataBean dataBean;
    private List<FortunetellingBean.DataBean> dataBeanList = new ArrayList<>();
    private FortunetellingAdapter fortunetellingAdapter;
    private String lifeuserid;
    private IabHelper mHelper;
    private IabBroadcastReceiver mBroadcastReceiver;
    private String packageName;
    static final int RC_REQUEST = 10001;
    private ArrayList<String> skuList = new ArrayList<String>();
    public final int SKUSUCCESS = 5;
    private KProgressHUD hud;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            //获得 所有属性  getField->public  以及父类
            Field mCurRootViewField = InputMethodManager.class.getDeclaredField("mCurRootView");
            //设置允许访问权限
            mCurRootViewField.setAccessible(true);
            // 对象
            Object mCurRootView = mCurRootViewField.get(im);
            if (null != mCurRootView){
                Context context = ((View) mCurRootView).getContext();
                if (context == this){
                    //破怪gc 引用链
                    mCurRootViewField.set(im,null);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        packageName = this.getPackageName() + ".";
        mHelper = new IabHelper(MyApplication.getContext(), base64EncodedPublicKey);

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
                mBroadcastReceiver = new IabBroadcastReceiver(FortunetellingActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                //                MyLog.d(TAG, "Setup successful. Querying inventory.");
//                try {
//                    mHelper.queryInventoryAsync(mGotInventoryListener);
//                } catch (IabHelper.IabAsyncInProgressException e) {
//                    //                    complain("Error querying inventory. Another async operation in progress.");
//                }

            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_fortunetelling);
        ButterKnife.bind(this);
        rgblist.add(R.mipmap.icon_login_bg_7);
        rgblist.add(R.mipmap.icon_login_bg_1);
        rgblist.add(R.mipmap.icon_login_bg_2);
        rgblist.add(R.mipmap.icon_login_bg_3);
        rgblist.add(R.mipmap.icon_login_bg_4);
        rgblist.add(R.mipmap.icon_login_bg_5);
        rgblist.add(R.mipmap.icon_login_bg_6);

        titlelist.add(R.mipmap.icon_font_bg_13);
        titlelist.add(R.mipmap.icon_font_bg_1);
        titlelist.add(R.mipmap.icon_font_bg_3);
        titlelist.add(R.mipmap.icon_font_bg_5);
        titlelist.add(R.mipmap.icon_font_bg_7);
        titlelist.add(R.mipmap.icon_font_bg_9);
        titlelist.add(R.mipmap.icon_font_bg_11);

        contentlist.add(0);
        contentlist.add(R.mipmap.icon_font_bg_2);
        contentlist.add(R.mipmap.icon_font_bg_4);
        contentlist.add(R.mipmap.icon_font_bg_6);
        contentlist.add(R.mipmap.icon_font_bg_8);
        contentlist.add(R.mipmap.icon_font_bg_10);
        contentlist.add(R.mipmap.icon_font_bg_12);

        lifeuserid = getIntent().getStringExtra("lifeuserid");

        fortunetellingAdapter = new FortunetellingAdapter(FortunetellingActivity.this, dataBeanList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(fortunetellingAdapter);
        String name = getIntent().getStringExtra("name");
        myTitlebar.setTitle(name);
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                //                .setLabel("Please wait")
                //                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void initDatas() {
        hud.show();
        OkGo.<String>post(Urls.Url_LifeBookCate).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .params("lifeuserid", lifeuserid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {


                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            fortunetellingBean = gson.fromJson(response.body(), FortunetellingBean.class);
                            dataBeanList = fortunetellingBean.getData();
                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
                        }
                    }
                });
    }

    @Override
    public void installListeners() {
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fortunetellingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FortunetellingBean.DataBean dataBean = dataBeanList.get(position);
                name = dataBean.getName();
                if (dataBean.getSign() == 1) {

                    Intent intent = new Intent(FortunetellingActivity.this, FortunetellingListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name );
                    bundle.putString("lifeuserid", dataBean.getLifeuserid());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {

                    String payload = "";
                    String productId = packageName + dataBean.getStoreId();


                    if (productId != null && !productId.isEmpty()) {
                        Log.d(TAG, "onViewClicked-productId: " + productId);
                        try {
                            mHelper.launchPurchaseFlow(getActivity(), productId, RC_REQUEST, mPurchaseFinishedListener, payload);
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            //                    complain("Error launching purchase flow. Another async operation in progress.");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case DATASUCCESS:

                int k = 0;
                for (FortunetellingBean.DataBean dataBean : dataBeanList) {
                    if (dataBean.getSign() == 1) {
                        k++;
                    }
                }
                if (k >= 3) {
                    if (rgblist.size()>6){
                        rgblist.remove(0);
                        titlelist.remove(0);
                        contentlist.remove(0);
                    }
                    dataBeanList.remove(0);

                }

                for (int i = 0; i < dataBeanList.size(); i++) {
                    for (int j = 0; j < rgblist.size(); j++) {
                        if (j == i) {
                            dataBeanList.get(i).setColour(rgblist.get(j));
                        }
                    }
                    for (int j = 0; j <titlelist.size(); j++) {
                        if (j == i) {
                            dataBeanList.get(i).setImgtitle(titlelist.get(j));
                        }
                    }

                    for (int j = 0; j <contentlist.size(); j++) {
                        if (j == i) {
                            dataBeanList.get(i).setImgcontent(contentlist.get(j));
                        }
                    }

                    dataBeanList.get(i).setLifeuserid(lifeuserid);

//                    if (i > 0) {
                        skuList.add(packageName + dataBeanList.get(i)
                                .getStoreId());
//                    }
                }

                fortunetellingAdapter.setNewData(dataBeanList);
                getHandler().obtainMessage(SKUSUCCESS)
                        .sendToTarget();
                hud.dismiss();
                break;

            case SKUSUCCESS:
                if (recyclerview != null) {
                    if (skuList.size() > 0) {
                        try {
                            mHelper.queryInventoryAsync(true, skuList, null, mGotInventoryListener);

                        } catch (IabHelper.IabAsyncInProgressException e) {
                            // complain("Error querying inventory. Another async operation in progress.");
                        }
                    }
                }

                break;
//            case 200:
//                fortunetellingAdapter.setNewData(dataBeanList);
//                break;
        }
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) {
                return;
            }

            // Is it a failure?
            if (result.isFailure()) {
                //                complain("Failed to query inventory: " + result);
                return;
            }
            for (String sku : skuList) {

                if (inventory.hasDetails(sku)) {
                    SkuDetails details = inventory.getSkuDetails(sku);
//                    dataBeanList.get(0)
//                            .setPrice(details.getPrice());
                    for (int i = 0; i < dataBeanList.size(); i++) {
                        if ((packageName + dataBeanList.get(i).getStoreId()).equals(sku)) {
                            dataBeanList.get(i)
                                    .setPrice(details.getPrice());
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fortunetellingAdapter.setNewData(dataBeanList);
                        }
                    });

                }
            }

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
                    OkGo.<String>post(Urls.Url_Shopping).tag(this)
                            .params("userid", PreferenceManager.getUserId(getActivity()))
                            .params("lifeuserid", lifeuserid)
                            .params("productId", productId)
                            .params("purchaseToken", purchaseToken)
                            .params("os", "A")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Gson gson = new Gson();
                                    LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                                    if (loginBean.getStatus()
                                            .equals("success")) {
                                        ShopCheckBean shopCheckBean = gson.fromJson(response.body(), ShopCheckBean.class);
                                        if (shopCheckBean.getData()
                                                .getStatus() == 1) {
                                            initDatas();
                                            if (!shopCheckBean.getData().getProductId().equals("com.foreseers.chat.iap100")){
                                                Intent intent = new Intent(FortunetellingActivity.this, FortunetellingListActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("name", name);
                                                bundle.putString("lifeuserid", lifeuserid);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        }
                                    }
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

    public void afterPurchase(int requestCode, int resultCode, Intent data) {
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mHelper == null) {
            return;
        }
        if (requestCode == 10001) {
            // Pass on the activity result to the helper for handling
            if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to in-app
                // billing...
                super.onActivityResult(requestCode, resultCode, data);
            } else {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter promoFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        OkGo.cancelTag(OkGo.getInstance()
                               .getOkHttpClient(), this);
        if (skuList != null) {
            skuList.clear();
            rgblist.clear();
            titlelist.clear();
            contentlist.clear();
        }
        if (mBroadcastReceiver != null) {
            FortunetellingActivity.this.unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }

//
//            RefWatcher refWatcher = MyApplication.getRefWatcher(this);//1
//            refWatcher.watch(this);

    }


}
