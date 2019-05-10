package com.foreseers.chat.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.Login;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.ShoppingIDBean;
import com.foreseers.chat.bean.VipTimeBean;
import com.foreseers.chat.bill.IabBroadcastReceiver;
import com.foreseers.chat.bill.IabHelper;
import com.foreseers.chat.bill.IabResult;
import com.foreseers.chat.bill.Inventory;
import com.foreseers.chat.bill.Purchase;
import com.foreseers.chat.bill.SkuDetails;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruffian.library.widget.RImageView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.foreseers.chat.activity.FortunetellingActivity.RC_REQUEST;
import static com.foreseers.chat.util.GooglePlayHelper.TAG;
import static com.foreseers.chat.util.GooglePlayHelper.base64EncodedPublicKey;
import static com.foreseers.chat.util.GooglePlayHelper.product1;
import static com.foreseers.chat.util.GooglePlayHelper.product2;
import static com.foreseers.chat.util.GooglePlayHelper.product3;

public class MyVipActivity extends BaseActivity implements IabBroadcastReceiver.IabBroadcastListener {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.text1) TextView text1;
    @BindView(R.id.layout_vip1) FrameLayout layoutVip1;
    @BindView(R.id.text2) TextView text2;
    @BindView(R.id.layout_vip2) LinearLayout layoutVip2;
    @BindView(R.id.layout_vip3) LinearLayout layoutVip3;
    @BindView(R.id.rimg_head) RImageView rimgHead;
    @BindView(R.id.text_username) TextView textUsername;
    @BindView(R.id.text_day) TextView textDay;
    @BindView(R.id.text3) TextView text3;
    @BindView(R.id.img) ImageView img;
    private String head;
    private String day;
    private String name;
    private IabHelper mHelper;
    private IabBroadcastReceiver mBroadcastReceiver;
    private ShoppingIDBean shoppingIDBean;
    private List<ShoppingIDBean.DataBean> vipBeans = new ArrayList<>();
    private List<List<ShoppingIDBean.DataBean>> dataBeanList = new ArrayList<>();
    private ArrayList<String> skuList = new ArrayList<String>();
    private String packageName;
    private VipTimeBean vipTimeBean;

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_my_vip);
        ButterKnife.bind(this);
        packageName = getActivity().getPackageName();
    }

    @Override
    public void initDatas() {
        Bundle bundle = getIntent().getExtras();
        head = bundle.getString("head", "");
        day = bundle.getString("day", "");
        name = bundle.getString("name", "");
        Glide.with(this)
                .load(head)
                .into(rimgHead);
        textUsername.setText(name);
        textDay.setText(day);
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
       switch (msg.what){
           case DATASUCCESS:
               if (textDay!=null){
                   textDay.setText(vipTimeBean.getData().getViptime());
               }
               break;
       }
    }

    @OnClick({R.id.layout_vip1, R.id.layout_vip2, R.id.layout_vip3})
    public void onViewClicked(View view) {
        String payload = "";
        String productId = "";
        switch (view.getId()) {
            case R.id.layout_vip1:
                productId = packageName + product1;
                layoutVip1.setBackgroundResource(R.drawable.background_vip_postpone2);
                layoutVip2.setBackgroundResource(R.drawable.background_vip_postpone);
                layoutVip3.setBackgroundResource(R.drawable.background_vip_postpone);
                img.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_vip2:
                productId = packageName + product2;
                layoutVip1.setBackgroundResource(R.drawable.background_vip_postpone);
                layoutVip2.setBackgroundResource(R.drawable.background_vip_postpone2);
                layoutVip3.setBackgroundResource(R.drawable.background_vip_postpone);
                img.setVisibility(View.GONE);
                break;
            case R.id.layout_vip3:
                productId = packageName + product3;
                layoutVip1.setBackgroundResource(R.drawable.background_vip_postpone);
                layoutVip2.setBackgroundResource(R.drawable.background_vip_postpone);
                layoutVip3.setBackgroundResource(R.drawable.background_vip_postpone2);
                img.setVisibility(View.GONE);
                break;
        }
        if (productId != null && !productId.isEmpty()) {
            Log.d(TAG, "onViewClicked-productId: " + productId);
            try {
                mHelper.launchPurchaseFlow(getActivity(), productId, RC_REQUEST, mPurchaseFinishedListener, payload);
            } catch (IabHelper.IabAsyncInProgressException e) {
                //                    complain("Error launching purchase flow. Another async operation in progress.");
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);

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
                mBroadcastReceiver = new IabBroadcastReceiver(MyVipActivity.this);
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
                                    //                                    getHandler().obtainMessage(SKUSUCCESS).sendToTarget();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });
            }
        });
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
            for (String sku : skuList) {
                if (inventory.hasDetails(sku)) {
                    SkuDetails details = inventory.getSkuDetails(sku);

                    switch (details.getSku()) {

                        case "com.foreseers.chat.vip360":
                            text1.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.vip90":
                            text2.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.vip30":
                            text3.setText(details.getPrice());
                            break;
                    }
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
                    OkGo.<String>post(Urls.Url_Shopping).tag(this)
                            .params("userid", PreferenceManager.getUserId(getActivity()))
                            .params("productId", productId)
                            .params("purchaseToken", purchaseToken)
                            .params("os", "A")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    //                                    getCanumsNum();
                                    refresh();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    protected void onDestroy() {
        super.onDestroy();
        OkGo.cancelTag(OkGo.getInstance()
                               .getOkHttpClient(), this);
        if (text1 != null) {
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
    private void refresh() {

        OkGo.<String>post(Urls.Url_VipTime).tag(this)
                .params("userid",PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson=new Gson();
                        LoginBean loginBean=gson.fromJson(response.body(),LoginBean.class);
                        if (loginBean.getStatus().equals("success")){
                            vipTimeBean = gson.fromJson(response.body(), VipTimeBean.class);
                            getHandler().obtainMessage(DATASUCCESS).sendToTarget();
                        }
                    }
                });
    }
}
