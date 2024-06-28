package com.example.allnetworkads;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.allnetworkads.admob.AppOpenAdManager;
import com.example.allnetworkads.adslib.Constants;
import com.example.allnetworkads.adslib.SharedPrefUtils;
import com.example.allnetworkads.applovin.AppLovinAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

//import android.widget.Toast;

/** Application class that initializes, loads and show ads when activities change states. */
public class MyApplication extends Application
        implements ActivityLifecycleCallbacks, LifecycleObserver {

    private AppOpenAdManager appOpenAdManager;
    private Activity currentActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String process = getProcessName();
            if (getPackageName() != process) WebView.setDataDirectorySuffix(process);
        }
        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(
                            @NonNull InitializationStatus initializationStatus) {}
                });


//        boolean showAdmob = SharedPrefUtils.getBooleanData(this, Constants.SHOW_ADMOB);
//        if(showAdmob) {
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            appOpenAdManager = new AppOpenAdManager();
//
//        }else {
//            ProcessLifecycleOwner.get().getLifecycle().addObserver(new MyLifecycleObserver(this));
//        }

    }

/*

    class MyLifecycleObserver implements DefaultLifecycleObserver {
        MyApplication myApplication;
        public MyLifecycleObserver(MyApplication myApplicationx) {
            myApplication = myApplicationx;
        }

        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {
            AppLovinAds.loadOpenAd(myApplication);
        }

        @Override
        public void onResume(@NonNull LifecycleOwner owner) {
//            DefaultLifecycleObserver.super.onResume(owner);
            AppLovinAds.showOpenAd();
        }
    }*/
    /** LifecycleObserver method that shows the app open ad when the app moves to foreground. */
    @OnLifecycleEvent(Event.ON_START)
    protected void onMoveToForeground() {
        // Show the ad (if available) when the app moves to foreground.
        appOpenAdManager.showAdIfAvailable(currentActivity);

        //ignore

    }

    /** ActivityLifecycleCallback methods. */
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {}

    @Override
    public void onActivityPaused(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(@NonNull Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {}

    /**
     * Shows an app open ad.
     *
     * @param activity the activity that shows the app open ad
     * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
     */
    public void showAdIfAvailable(
            @NonNull Activity activity,
            @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {

        appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

    /**
     * Interface definition for a callback to be invoked when an app open ad is complete
     * (i.e. dismissed or fails to show).
     */
    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }


}

