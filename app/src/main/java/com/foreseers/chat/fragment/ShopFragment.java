package com.foreseers.chat.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.activity.FortunetellingUserActivity;
import com.foreseers.chat.bean.BannerData;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.ShopCheckBean;
import com.foreseers.chat.bean.ShoppingIDBean;
import com.foreseers.chat.bean.UserCanumsNumBean;
import com.foreseers.chat.bill.IabBroadcastReceiver;
import com.foreseers.chat.bill.IabHelper;
import com.foreseers.chat.bill.IabResult;
import com.foreseers.chat.bill.Inventory;
import com.foreseers.chat.bill.Purchase;
import com.foreseers.chat.bill.SkuDetails;
import com.foreseers.chat.global.BaseFragment;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.CustomViewHolder;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ms.banner.Banner;
import com.ms.banner.Transformer;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;
import com.ruffian.library.widget.utils.TextViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.foreseers.chat.util.GooglePlayHelper.TAG;
import static com.foreseers.chat.util.GooglePlayHelper.base64EncodedPublicKey;
import static com.foreseers.chat.util.GooglePlayHelper.item1;
import static com.foreseers.chat.util.GooglePlayHelper.item2;
import static com.foreseers.chat.util.GooglePlayHelper.item3;
import static com.foreseers.chat.util.GooglePlayHelper.product1;
import static com.foreseers.chat.util.GooglePlayHelper.product2;
import static com.foreseers.chat.util.GooglePlayHelper.product3;

/**
 * 商店
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends BaseFragment implements IabBroadcastReceiver.IabBroadcastListener {

    Unbinder unbinder;
    @BindView(R.id.text_num) TextView textNum;
    @BindView(R.id.button_1) Button button1;
    @BindView(R.id.button_2) Button button2;
    @BindView(R.id.button_3) Button button3;
    @BindView(R.id.button_prop1) Button buttonProp1;
    @BindView(R.id.button_prop2) Button buttonProp2;
    @BindView(R.id.button_prop3) Button buttonProp3;
    @BindView(R.id.price_vip360) TextView priceVip360;
    @BindView(R.id.price_vip90) TextView priceVip90;
    @BindView(R.id.price_vip30) TextView priceVip30;
    @BindView(R.id.text_item3) TextView textItem3;
    @BindView(R.id.text_item2) TextView textItem2;
    @BindView(R.id.text_item1) TextView textItem1;
    @BindView(R.id.banner) Banner banner;

    private LoginBean loginBean;
    private UserCanumsNumBean userCanumsNumBean;
    private List<BannerData> bannerList = new ArrayList<>();
    private LayoutInflater inflater;
    private int num;
    private String packageName;
    public final int SKUSUCCESS = 2;
    private ShoppingIDBean shoppingIDBean;
    private List<ShoppingIDBean.DataBean> vipBeans = new ArrayList<>();
    private List<ShoppingIDBean.DataBean> eraserBeans = new ArrayList<>();
    private List<List<ShoppingIDBean.DataBean>> dataBeanList = new ArrayList<>();
    private ArrayList<String> skuList = new ArrayList<String>();
    private String productId;
    private String purchaseToken;
    private boolean billingEnable = true;
    // The helper object
    private IabHelper mHelper;
    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;
    // Provides purchase notification while this app is running
    private IabBroadcastReceiver mBroadcastReceiver;
    private Intent intent;
    private String userid;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                mBroadcastReceiver = new IabBroadcastReceiver(ShopFragment.this);
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
                                    getHandler().obtainMessage(SKUSUCCESS).sendToTarget();
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
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            //            MyLog.d(TAG, "Query inventory finished.");
            showLog("Query inventory finished.");

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
                    showLog("sku:  " + details.toString());

                    switch (details.getSku()) {
                        case "com.foreseers.chat.item1":
                            textItem1.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.item2":
                            textItem2.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.item3":
                            textItem3.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.vip360":
                            priceVip360.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.vip90":
                            priceVip90.setText(details.getPrice());
                            break;
                        case "com.foreseers.chat.vip30":
                            priceVip30.setText(details.getPrice());
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
                    Log.i(TAG, "onConsumeFinished: productId" + productId + "     purchaseToken" + purchaseToken);
                    OkGo.<String>post(Urls.Url_Shopping).tag(this)
                            .params("userid", userid)
                            .params("productId", productId)
                            .params("purchaseToken", purchaseToken)
                            .params("os", "A")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    getCanumsNum();
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
    public void initViews() {
        packageName = getActivity().getPackageName();
        userid = PreferenceManager.getUserId(getActivity());
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_05, getResources().getString(R.string.foreseers_special_vip_id),
                                      getResources().getString(R.string.foreseers_catch_you), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_01, getResources().getString(R.string.foreseers_ten_per_day),
                                      getResources().getString(R.string.foreseers_look_TA), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_02, getResources().getString(R.string.foreseers_infinate_priends_num),
                                      getResources().getString(R.string.foreseers_follow_your_heart), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_03, getResources().getString(R.string.foreseers_who_see_you),
                                      getResources().getString(R.string.foreseers_crush_on_you), false));
        bannerList.add(new BannerData(R.mipmap.icon_vip_me_04, getResources().getString(R.string.foreseers_who_see_you_photo),
                                      getResources().getString(R.string.foreseers_immediately_TA), false));

        banner.setAutoPlay(true)
                .setPages(bannerList, new HolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new CustomViewHolder();
                    }
                })
                .setBannerAnimation(Transformer.Scale)
                .setDelayTime(4000)
                .start();
    }

    @Override
    public void initDatas() {
        getCanumsNum();
    }

    private void getCanumsNum() {
        OkGo.<String>post(Urls.Url_UserCanumsNum).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            userCanumsNumBean = gson.fromJson(response.body(), UserCanumsNumBean.class);
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
            case SKUSUCCESS:
                if (textNum != null) {

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

    @OnClick({R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_prop1, R.id.button_prop2, R.id.button_prop3, R.id.layout_fortunetelling})
    public void onViewClicked(View view) {
        String payload = "";
        String productId = "";
        switch (view.getId()) {
            case R.id.button_1:
                productId = packageName + product1;
                break;
            case R.id.button_2:
                productId = packageName + product2;
                break;
            case R.id.button_3:
                productId = packageName + product3;
                break;
            case R.id.button_prop1:
                productId = packageName + item3;
                break;
            case R.id.button_prop2:
                productId = packageName + item2;
                break;
            case R.id.button_prop3:
                productId = packageName + item1;

                break;
            case R.id.layout_fortunetelling://命书
                intent = new Intent(getActivity(), FortunetellingUserActivity.class);
                getActivity().startActivity(intent);
                break;
        }
        if (productId != null && !productId.isEmpty()) {
            Log.d(TAG, "onViewClicked-productId: " + productId);
            try {
                mHelper.launchPurchaseFlow(getActivity(), productId, RC_REQUEST, mPurchaseFinishedListener, payload);
            } catch (IabHelper.IabAsyncInProgressException e) {
                //                                    complain("Error launching purchase flow. Another async operation in progress.");
            }
        }
    }

    @Override
    public void receivedBroadcast() {
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            //            complain("Error querying inventory. Another async operation in progress.");
        }
    }

    public void showLog(String s) {
        Log.d(TAG, "showLog: " + "\n" + s);
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
    public void onResume() {
        super.onResume();

        IntentFilter promoFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        bannerList.clear();
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), this);
        if (textNum != null) {
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

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
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
}
