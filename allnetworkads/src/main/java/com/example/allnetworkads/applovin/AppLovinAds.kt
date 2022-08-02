package com.example.allnetworkads.applovin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.navigation.Navigation
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder
import com.example.allnetworkads.AdsCounter
import com.example.allnetworkads.R
import com.example.allnetworkads.admob.AdmobAds
import com.example.allnetworkads.admob.ENUMS
import com.example.allnetworkads.adslib.Constants
import com.example.allnetworkads.adslib.InHouseAds
import com.example.allnetworkads.adslib.SharedPrefUtils
import java.util.concurrent.TimeUnit
import kotlin.math.pow

class AppLovinAds {
    companion object {
        private lateinit var interstitialAd: MaxInterstitialAd
        private var retryAttempt = 0.0

        private lateinit var nativeAdLoader: MaxNativeAdLoader
        private var nativeAd: MaxAd? = null

       // private lateinit var nativeAdLayout: FrameLayout

       /* fun loadNativeAd(context: Context, activity: Activity,  appName: String,
                         pkgName: String,  isSmallAd: Int) {
            //val nativeAdLayout: FrameLayout = activity.findViewById(R.id.fl_adplaceholder)

            val nativeAds = activity.findViewById<FrameLayout>(R.id.fl_adplaceholder)
            val AdsAreaEmpty = activity.findViewById<LinearLayout>(R.id.ads_area_empty)
            val inHouseAdArea: LinearLayoutCompat = activity.findViewById(R.id.inHouseAd)

           // nativeAdLayout = findViewById(R.id.native_ad_layout)
            val adId = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_NATIVE)
            Log.i("MyLog", "adid: "+adId)
            nativeAdLoader = MaxNativeAdLoader(adId, context)
            nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
                override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                    // Cleanup any pre-existing native ad to prevent memory leaks.
                    if (nativeAd != null) {
                        nativeAdLoader.destroy(nativeAd)
                    }

                    // Save ad for cleanup.
                    nativeAd = ad

                    // Add ad view to view.
                    nativeAds.removeAllViews()
                    nativeAds.addView(nativeAdView)

                    //correct

                    //correct
                    nativeAds.visibility = View.VISIBLE
                    inHouseAdArea.visibility = View.GONE
                    AdsAreaEmpty.visibility = View.GONE

                }

                override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                    if (InHouseAds.getModelAdsList().size > 0) {
                        AdmobAds.showInHouseAds(context, activity, appName, pkgName, isSmallAd)
                        nativeAds.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.GONE
                        inHouseAdArea.visibility = View.VISIBLE
                    } else {
                        nativeAds.visibility = View.GONE
                        inHouseAdArea.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.VISIBLE
                    }
                }
                override fun onNativeAdClicked(ad: MaxAd) {}
            })
            nativeAdLoader.loadAd()
        }*/

        fun loadNativeAd(context: Context, activity: Activity, appName: String, pkgName: String,
                         isSmallAd: Int, nativeThemeColor: Int) {
            val nativeAdLayout = activity.findViewById<FrameLayout>(R.id.fl_adplaceholder)
            val AdsAreaEmpty = activity.findViewById<LinearLayout>(R.id.ads_area_empty)
            val inHouseAdArea: LinearLayoutCompat = activity.findViewById(R.id.inHouseAd)

            val layout = getNativeLayout(isSmallAd, nativeThemeColor)

            val binder: MaxNativeAdViewBinder = MaxNativeAdViewBinder
                .Builder(layout)
                .setTitleTextViewId(R.id.title_text_view)
                .setBodyTextViewId(R.id.body_text_view)
                .setAdvertiserTextViewId(R.id.advertiser_textView)
                .setIconImageViewId(R.id.icon_image_view)
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setOptionsContentViewGroupId(R.id.ad_options_view)
                .setCallToActionButtonId(R.id.cta_button)
                .build()
            val nativeAdView = MaxNativeAdView(binder, context)

            //val adId = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_NATIVE)

            var adId: String? = ""
            try {
                adId = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_NATIVE)
                if (adId == null) {
                    adId = ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
                adId = ""
            }

            Log.i("MyLog", "adId: "+adId)

            nativeAdLoader = MaxNativeAdLoader(adId, context)
            nativeAdLoader.setRevenueListener { }
            nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
                override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {

                    // Cleanup any pre-existing native ad to prevent memory leaks.
                    if (nativeAd != null) {
                        nativeAdLoader.destroy(nativeAd)
                    }

                    // Save ad for cleanup.
                    nativeAd = ad

                    // Add ad view to view.
                    nativeAdLayout.removeAllViews()
                    nativeAdLayout.addView(nativeAdView)

                    nativeAdLayout.visibility = View.VISIBLE
                    inHouseAdArea.visibility = View.GONE
                    AdsAreaEmpty.visibility = View.GONE
                }

                override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                    Log.i("MyLog", "Error: "+error.toString())
                    if (InHouseAds.getModelAdsList().size > 0) {
                        AdmobAds.showInHouseAds(context, activity, appName, pkgName, isSmallAd)
                        nativeAdLayout.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.GONE
                        inHouseAdArea.visibility = View.VISIBLE
                    } else {
                        nativeAdLayout.visibility = View.GONE
                        inHouseAdArea.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.VISIBLE
                    }
                }
                override fun onNativeAdClicked(ad: MaxAd) {}
            })

            nativeAdLoader.loadAd(nativeAdView)
        }

        fun loadFragmentNativeAd(context: Context, view: View, appName: String, pkgName: String,
                         isSmallAd: Int, nativeThemeColor: Int) {
            val nativeAdLayout = view.findViewById<FrameLayout>(R.id.fl_adplaceholder)
            val AdsAreaEmpty = view.findViewById<LinearLayout>(R.id.ads_area_empty)
            val inHouseAdArea: LinearLayoutCompat = view.findViewById(R.id.inHouseAd)

            val layout = getNativeLayout(isSmallAd, nativeThemeColor)

            val binder: MaxNativeAdViewBinder = MaxNativeAdViewBinder
                .Builder(layout)
                .setTitleTextViewId(R.id.title_text_view)
                .setBodyTextViewId(R.id.body_text_view)
                .setAdvertiserTextViewId(R.id.advertiser_textView)
                .setIconImageViewId(R.id.icon_image_view)
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setOptionsContentViewGroupId(R.id.ad_options_view)
                .setCallToActionButtonId(R.id.cta_button)
                .build()
            val nativeAdView = MaxNativeAdView(binder, context)

            //val adId = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_NATIVE)

            var adId: String? = ""
            try {
                adId = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_NATIVE)
                if (adId == null) {
                    adId = ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
                adId = ""
            }

            nativeAdLoader = MaxNativeAdLoader(adId, context)
            nativeAdLoader.setRevenueListener { }
            nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
                override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {

                    // Cleanup any pre-existing native ad to prevent memory leaks.
                    if (nativeAd != null) {
                        nativeAdLoader.destroy(nativeAd)
                    }

                    // Save ad for cleanup.
                    nativeAd = ad

                    // Add ad view to view.
                    nativeAdLayout.removeAllViews()
                    nativeAdLayout.addView(nativeAdView)

                    nativeAdLayout.visibility = View.VISIBLE
                    inHouseAdArea.visibility = View.GONE
                    AdsAreaEmpty.visibility = View.GONE
                }

                override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                    if (InHouseAds.getModelAdsList().size > 0) {
                        AdmobAds.showFragmentInHouseAds(context, view, appName, pkgName, isSmallAd)
                        nativeAdLayout.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.GONE
                        inHouseAdArea.visibility = View.VISIBLE
                    } else {
                        nativeAdLayout.visibility = View.GONE
                        inHouseAdArea.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.VISIBLE
                    }
                }
                override fun onNativeAdClicked(ad: MaxAd) {}
            })

            nativeAdLoader.loadAd(nativeAdView)
        }

        private fun getNativeLayout(isSmallAd: Int, nativeThemeColor: Int): Int {
            var layout = R.layout.applovin_native_custom_white_layout
            if (nativeThemeColor == ENUMS.BLACK) {
                if (isSmallAd == ENUMS.SMALL_ADS) {
                    layout = R.layout.applovin_native_custom_black_small
                } else if (isSmallAd == ENUMS.LARGE_ADS) {
                    layout = R.layout.applovin_native_custom_black_layout
                }
            } else if (nativeThemeColor == ENUMS.WHITE) {
                if (isSmallAd == ENUMS.SMALL_ADS) {
                    layout = R.layout.applovin_native_custom_white_small
                } else if (isSmallAd == ENUMS.LARGE_ADS) {
                    layout = R.layout.applovin_native_custom_white_layout
                }
            }
            return layout
        }

        /*fun loadFragmentNativeAd(context: Context, view: View,  appName: String,
                         pkgName: String,  isSmallAd: Int) {
            //val nativeAdLayout: FrameLayout = activity.findViewById(R.id.fl_adplaceholder)

            val nativeAds = view.findViewById<FrameLayout>(R.id.fl_adplaceholder)
            val AdsAreaEmpty = view.findViewById<LinearLayout>(R.id.ads_area_empty)
            val inHouseAdArea: LinearLayoutCompat = view.findViewById(R.id.inHouseAd)

            // nativeAdLayout = findViewById(R.id.native_ad_layout)
            val adId = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_NATIVE)
            Log.i("MyLog", "adid: "+adId)
            nativeAdLoader = MaxNativeAdLoader(adId, context)
            nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
                override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                    // Cleanup any pre-existing native ad to prevent memory leaks.
                    if (nativeAd != null) {
                        nativeAdLoader.destroy(nativeAd)
                    }

                    // Save ad for cleanup.
                    nativeAd = ad

                    // Add ad view to view.
                    nativeAds.removeAllViews()
                    nativeAds.addView(nativeAdView)

                    //correct

                    //correct
                    nativeAds.visibility = View.VISIBLE
                    inHouseAdArea.visibility = View.GONE
                    AdsAreaEmpty.visibility = View.GONE

                }

                override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                    if (InHouseAds.getModelAdsList().size > 0) {
                        AdmobAds.showFragmentInHouseAds(context, view, appName, pkgName, isSmallAd)
                        nativeAds.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.GONE
                        inHouseAdArea.visibility = View.VISIBLE
                    } else {
                        nativeAds.visibility = View.GONE
                        inHouseAdArea.visibility = View.GONE
                        AdsAreaEmpty.visibility = View.VISIBLE
                    }
                }
                override fun onNativeAdClicked(ad: MaxAd) {}
            })
            nativeAdLoader.loadAd()
        }*/

        fun loadInterstitialAd(context: Context, activity: Activity) {
            //val adId = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_INTER)

            var interID: String? = ""
            try {
                interID = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_INTER)
                if (interID == null) {
                    interID = ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
                interID = ""
            }

            interstitialAd = MaxInterstitialAd(interID, activity )
            // Load the first ad
            interstitialAd.loadAd()
        }

        fun RedirectActivity(context: Context, activity: Activity, intent: Intent, isFinish: Boolean) {
            if (AdsCounter.isShowAd(context)) {
                interstitialAd.setListener(object : MaxAdListener {
                    override fun onAdLoaded(ad: MaxAd?) {
                        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'
                        // Reset retry attempt
                        retryAttempt = 0.0
                    }

                    override fun onAdDisplayed(ad: MaxAd?) {}
                    override fun onAdClicked(ad: MaxAd?) {}

                    override fun onAdHidden(ad: MaxAd?) {
                        // Interstitial ad is hidden. Pre-load the next ad
                        interstitialAd.loadAd()
                        context.startActivity(intent)
                        if (isFinish) {
                            activity.finish()
                        }
                    }

                    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                        // Interstitial ad failed to load
                        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

                        retryAttempt++
                        val delayMillis = TimeUnit.SECONDS.toMillis(
                            2.0.pow(
                                6.0.coerceAtMost(
                                    retryAttempt
                                )
                            ).toLong()
                        )

                        Handler().postDelayed({ interstitialAd.loadAd() }, delayMillis)
                    }

                    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
                        interstitialAd.loadAd()
                    }
                })

                if (interstitialAd.isReady)
                    interstitialAd.showAd()
                else {
                    context.startActivity(intent)
                    if (isFinish) {
                        activity.finish()
                    }
                }
            }
            else {
                context.startActivity(intent)
                if (isFinish) {
                    activity.finish()
                }
            }

        }

        fun adOnBack(context: Context, activity: Activity) {
            if (AdsCounter.isShowAd(context)) {
                interstitialAd.setListener(object : MaxAdListener {
                    override fun onAdLoaded(ad: MaxAd?) {
                        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'
                        // Reset retry attempt
                        retryAttempt = 0.0
                    }

                    override fun onAdDisplayed(ad: MaxAd?) {}
                    override fun onAdClicked(ad: MaxAd?) {}

                    override fun onAdHidden(ad: MaxAd?) {
                        // Interstitial ad is hidden. Pre-load the next ad
                        interstitialAd.loadAd()
                        activity.finish()
                    }

                    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                        // Interstitial ad failed to load
                        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

                        retryAttempt++
                        val delayMillis = TimeUnit.SECONDS.toMillis(
                            2.0.pow(
                                6.0.coerceAtMost(
                                    retryAttempt
                                )
                            ).toLong()
                        )

                        Handler().postDelayed({ interstitialAd.loadAd() }, delayMillis)
                    }

                    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
                        interstitialAd.loadAd()
                    }
                })

                if (interstitialAd.isReady)
                    interstitialAd.showAd()
                else {
                    activity.finish()
                }
            }
            else {
                activity.finish()
            }
        }

        fun redirectFragmentWithNavController(context: Context, activity: Activity, fragmentId: Int,
                                              view:View, bundle: Bundle, backStack: Boolean) {
            if (AdsCounter.isShowAd(context)) {
                interstitialAd.setListener(object : MaxAdListener {
                    override fun onAdLoaded(ad: MaxAd?) {
                        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'
                        // Reset retry attempt
                        retryAttempt = 0.0
                    }

                    override fun onAdDisplayed(ad: MaxAd?) {}
                    override fun onAdClicked(ad: MaxAd?) {}

                    override fun onAdHidden(ad: MaxAd?) {
                        // Interstitial ad is hidden. Pre-load the next ad
                        interstitialAd.loadAd()
                        Navigation.findNavController(view).popBackStack(fragmentId, backStack)
                        Navigation.findNavController(view).navigate(fragmentId, bundle)
                    }

                    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                        // Interstitial ad failed to load
                        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

                        retryAttempt++
                        val delayMillis = TimeUnit.SECONDS.toMillis(
                            2.0.pow(
                                6.0.coerceAtMost(
                                    retryAttempt
                                )
                            ).toLong()
                        )

                        Handler().postDelayed({ interstitialAd.loadAd() }, delayMillis)
                    }

                    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
                        interstitialAd.loadAd()
                    }
                })

                if (interstitialAd.isReady)
                    interstitialAd.showAd()
                else {
                    Navigation.findNavController(view).popBackStack(fragmentId, backStack)
                    Navigation.findNavController(view).navigate(fragmentId, bundle)
                }
            }
            else {
                Navigation.findNavController(view).popBackStack(fragmentId, backStack)
                Navigation.findNavController(view).navigate(fragmentId, bundle)
            }
        }
    }
}