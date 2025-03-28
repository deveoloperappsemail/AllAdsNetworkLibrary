package com.example.allnetworkads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentTransaction;

import com.example.allnetworkads.admob.AdmobAds;
import com.example.allnetworkads.adslib.Constants;
import com.example.allnetworkads.adslib.SharedPrefUtils;
import com.example.allnetworkads.applovin.AppLovinAds;

public class Ads {


    public static void showActivityBanner(Context context, Activity activity) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
        if(showAdmob) {
            AdmobAds.showActivityBanner(context, activity);
        }
        else {
            AppLovinAds.Companion.showActivityBanner(context, activity);
        }
    }

    public static void showFragmentBanner(Context context, View view) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
        if(showAdmob) {
            AdmobAds.showFragmentBanner(context, view);
        }
        else {
            AppLovinAds.Companion.showFragmentBanner(context, view);
        }
    }

    public static void loadNative(Context context, Activity activity, View view, String appName,
                                  String pkgName, int isSmallAd, int nativeTheme,
                                  boolean isFragment) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
        if(showAdmob) {
            if(isFragment) {
                AdmobAds.refreshFragmentAd(context, activity, view, appName, pkgName, isSmallAd, nativeTheme);
            }
            else {
                AdmobAds.refreshAd(context, activity, appName, pkgName, isSmallAd, nativeTheme);
            }
        }
        else {
            if(isFragment) {
                AppLovinAds.Companion.loadFragmentNativeAd(context, view, appName, pkgName,
                        isSmallAd, nativeTheme);
            }
            else {
                AppLovinAds.Companion.loadNativeAd(context, activity, appName, pkgName,
                        isSmallAd, nativeTheme);
            }
        }
    }

    public static void loadInter(Context context, Activity activity) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
        if(showAdmob) {
            AdmobAds.loadAdmobInters(context);
        }
        else {
            AppLovinAds.Companion.loadInterstitialAd(context, activity);
        }
    }

    public static void showInterEmpty(Context context, Activity activity) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);

        String packageName = activity.getPackageName();
        String appName = activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString();

        if(showAdmob) {
            AdmobAds.showInter(context, activity, appName, packageName);
        }
        else {
            AppLovinAds.Companion.showInter(context, activity, appName, packageName);
        }
    }

    public static void showInter(Context context, Activity activity, Intent intent,
                                 boolean isFinish) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);

        String packageName = activity.getPackageName();
        String appName = activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString();

        if(showAdmob) {
            AdmobAds.RedirectActivity(context, activity, appName, packageName, intent, isFinish);
        }
        else {
            AppLovinAds.Companion.RedirectActivity(context, activity, appName, packageName, intent, isFinish);
        }
    }

    public static void showFragmentInterWithCommit(Context context, Activity activity,
                                                   FragmentTransaction fragmentTransaction) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);

        String packageName = activity.getPackageName();
        String appName = activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString();

        if(showAdmob) {
            AdmobAds.redirectFragmentWithCommit(context, activity, appName, packageName, fragmentTransaction);
        }
        else {
            AppLovinAds.Companion.redirectFragmentWithCommit(context, activity, appName, packageName, fragmentTransaction);
        }
    }
    public static void showFragmentInterWithCommitStateloss(Context context, Activity activity,
                                                   FragmentTransaction fragmentTransaction) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);

        String packageName = activity.getPackageName();
        String appName = activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString();

        if(showAdmob) {
            AdmobAds.redirectFragmentWithCommitStateLoss(context, activity, appName, packageName, fragmentTransaction);
        }
        else {
            AppLovinAds.Companion.redirectFragmentWithCommit(context, activity, appName, packageName, fragmentTransaction);
        }
    }

    public static void showFragmentInterWithNavController(Context context, Activity activity,
                                                          int fragmentId, View view,
                                                          Bundle bundle, boolean backStack) {
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);

        String packageName = activity.getPackageName();
        String appName = activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString();

        if(showAdmob) {
            AdmobAds.redirectFragmentWithNavController(context, activity, appName, packageName,
                    fragmentId, view, bundle, backStack);
        }
        else {
            AppLovinAds.Companion.redirectFragmentWithNavController(context, activity, appName, packageName,
                    fragmentId, view, bundle, backStack);
        }
    }

    public static void adOnBack(Context context, Activity activity){
        boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);

        String packageName = activity.getPackageName();
        String appName = activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString();

        if(showAdmob) {
            AdmobAds.adOnBack(context, activity, appName, packageName);
        }
        else {
            AppLovinAds.Companion.adOnBack(context, activity, appName, packageName);
        }
    }


//    public static void loadAppBAd(Context context){
//        AppBrainAd.Companion.loadAds(context);
//    }
/*
    public static void showAppBFragmentAd(Context context, Activity activity, boolean actOrFrag, Intent intent,
                                      boolean isFinish,
                                      View view, int fragID ){
        AppBrainAd.Companion.showFragmentAd( fragID, actOrFrag, isFinish, view,context, activity);
    }


    public static void showAppBActivityAd(Context context, Activity activity, boolean actOrFrag, Intent intent,
                                      boolean isFinish){
        AppBrainAd.Companion.showActivityAd(intent, actOrFrag, isFinish, context, activity);
    }

    public static void showAppBEmptyAd(Context context){
        AppBrainAd.Companion.showAppBEmptyAd(context);
    }*/
}
