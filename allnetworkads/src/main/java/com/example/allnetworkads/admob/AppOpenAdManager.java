package com.example.allnetworkads.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.mediation.ads.MaxAppOpenAd;
import com.example.allnetworkads.MyApplication;
import com.example.allnetworkads.adslib.Constants;
import com.example.allnetworkads.adslib.SharedPrefUtils;
import com.example.allnetworkads.applovin.AppLovinAds;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

/** Inner class that loads and shows app open ads. */
public class AppOpenAdManager {

    private static final String LOG_TAG = "AppOpenAdManager";
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";

    private AppOpenAd appOpenAd = null;
    private boolean isLoadingAd = false;
    public boolean isShowingAd = false;

    /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
    private long loadTime = 0;



    /** Constructor.
     * @param */
    public AppOpenAdManager() {

    }

    /**
     * Load an ad.
     *
     * @param context the context of the activity that loads the ad
     */
    private void loadAd(Context context) {

        try {
            // Do not load ad if there is an unused ad or one is already loading.
            if (isLoadingAd || isAdAvailable(context)) {
                return;
            }

            isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            String openAD = "";
            try {
                openAD = SharedPrefUtils.getStringData(context, Constants.OPEN_AD);
            } catch (Exception e) {
                e.printStackTrace();

                openAD = "";
            }
            AppOpenAd.load(
                    context,
                    openAD,
                    request,
//                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        /**
                         * Called when an app open ad has loaded.
                         *
                         * @param ad the loaded app open ad.
                         */
                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            try {
                                appOpenAd = ad;
                                isLoadingAd = false;
                                loadTime = (new Date()).getTime();

                                Log.d(LOG_TAG, "onAdLoaded.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //                            Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show();
                        }

                        /**
                         * Called when an app open ad has failed to load.
                         *
                         * @param loadAdError the error.
                         */
                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isLoadingAd = false;
                            Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                            //                            Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /** Check if ad was loaded more than n hours ago. */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    /** Check if ad exists and can be shown. */
    private boolean isAdAvailable(Context activity) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(activity, Constants.SHOW_ADMOB);
        if(showAdmob) {
            return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
        }else {
            return AppLovinAds.Companion.ifOpenAdAvailable();
        }
    }

    /**
     * Show the ad if one isn't already showing.
     *
     * @param activity the activity that shows the app open ad
     */
    public void showAdIfAvailable(@NonNull final Activity activity) {
        showAdIfAvailable(
                activity,
                new MyApplication.OnShowAdCompleteListener() {
                    @Override
                    public void onShowAdComplete() {
                        // Empty because the user will go back to the activity that shows the ad.
                    }
                });
    }

    /**
     * Show the ad if one isn't already showing.
     *
     * @param activity the activity that shows the app open ad
     * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
     */
    public void showAdIfAvailable(
            @NonNull final Activity activity,
            @NonNull MyApplication.OnShowAdCompleteListener onShowAdCompleteListener) {

        if (isShowingAd) {
            Log.d(LOG_TAG, "The app open ad is already showing.");
            return;
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable(activity)) {
            Log.d(LOG_TAG, "The app open ad is not ready yet.");
            onShowAdCompleteListener.onShowAdComplete();

            loadOpenAppAd(activity);
            return;
        }

        Log.d(LOG_TAG, "Will show ad.");


        boolean showAdmob = SharedPrefUtils.getBooleanData(activity, Constants.SHOW_ADMOB);
        if (showAdmob) {
            appOpenAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null;
                            isShowingAd = false;
                            Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
                            onShowAdCompleteListener.onShowAdComplete();
                            loadOpenAppAd(activity);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            appOpenAd = null;
                            isShowingAd = false;
                            Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());

                            onShowAdCompleteListener.onShowAdComplete();
                            loadOpenAppAd(activity);
                        }
                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d(LOG_TAG, "onAdShowedFullScreenContent.");
                        }
                    });
        }

        isShowingAd = true;
        showOOpenAd(activity);

    }

    private void showOOpenAd(Activity activity) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(activity, Constants.SHOW_ADMOB);
        if(showAdmob) {
            appOpenAd.show(activity);
        }else{
            AppLovinAds.Companion.showOpenAd(activity);
        }
    }

    private void loadOpenAppAd(Activity activity) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(activity, Constants.SHOW_ADMOB);
        if(showAdmob) {
            loadAd(activity);
        }else{
            AppLovinAds.Companion.loadOpenAd(activity);
        }
    }
}

