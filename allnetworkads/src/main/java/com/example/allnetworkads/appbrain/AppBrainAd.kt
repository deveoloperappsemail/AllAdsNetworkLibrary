/*
package com.example.allnetworkads.appbrain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import com.appbrain.AdId
import com.appbrain.InterstitialBuilder


class AppBrainAd {
    companion object {
        private var interstitialBuilder: InterstitialBuilder? = null
        private var myIntent: Intent? = null
        private var myActOrFrag: Boolean? = null
        private var id: Int? = null
        private var isFinish: Boolean? = null
        private var myView: View? = null
        val testMode = false
        private var context: Context? = null
        private var activity: Activity? = null

        fun loadAds(lcontext: Context) {
            interstitialBuilder = InterstitialBuilder.create()
                .setAdId(AdId.DEFAULT)
                .setOnDoneCallback {
//                interstitialBuilder!!.preload(context);
                    if (myActOrFrag == true) {
                        activity?.startActivity(myIntent)
                        if (isFinish == true) {
                            activity?.finish()
                        }

                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isFinish == true) {
                                Navigation.findNavController(
                                    myView!!
                                ).popBackStack()
                            }
                            Navigation.findNavController(
                                myView!!
                            ).navigate(id!!)
                        }, 0)


                    }
                    loadAds(context!!)
                }
                .preload(lcontext)
        }

        fun showActivityAd(
            intent: Intent?,

            actOrFrag: Boolean?,
            bool: Boolean?,
            mContext: Context,
            mActivty: Activity
        ) {
            try {
                isFinish = bool
                myIntent = intent
                myActOrFrag = actOrFrag
                context = mContext
                activity = mActivty
            } catch (e: Exception) {
                e.printStackTrace()
            }
            interstitialBuilder!!.show(context)


        }


        fun showFragmentAd(
            fragment: Int?,
            actOrFrag: Boolean?,
            bool: Boolean?,
            view: View,
            mContext: Context,
            mActivty: Activity
        ) {
            try {
                isFinish = bool
                id = fragment
                myActOrFrag = actOrFrag
                myView = view
                context = mContext
                activity = mActivty
            } catch (e: Exception) {
                e.printStackTrace()
            }
            interstitialBuilder!!.show(context)


        }


//    protected fun loadAds() {
//        interstitialBuilder = InterstitialBuilder.create()
//            .setAdId(AdId.DEFAULT)
//            .setOnDoneCallback {
//                if (interstitialBuilder != null) {
//                    interstitialBuilder.preload(this)
//                }
//                startActivity(myIntent)
//                if (isFinish!!) {
//                    finish()
//                }
//            }
//            .preload(this)
//    }

        //    fun showAd(intent: Intent, bool: Boolean) {
//        isFinish = bool
//        myIntent = intent
//        if (interstitialBuilder != null) {
//            interstitialBuilder.show(this)
//        } else {
//            startActivity(myIntent)
//            if (isFinish) {
//                finish()
//            }
//        }
//        loadAds()
//    }
        fun showAppBEmptyAd(context: Context) {
            try {
                if (interstitialBuilder != null) {
                    interstitialBuilder!!.show(context)
                }
                onlyAppBloadEmptyAds(context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        protected fun onlyAppBloadEmptyAds(context: Context) {
            interstitialBuilder = InterstitialBuilder.create()
                .setAdId(AdId.DEFAULT)
                .preload(context)
        }


        */
/*  fun showAd(
        fragmentIdx: Int,
        viewx: View?,
        bundlex: Bundle?,
        isBackStackx: Boolean
    ) {
        isBackStack = isBackStackx
        fragmentId = fragmentIdx
        view = viewx
        bundle = bundlex
//        myIntent = intent

        Log.d("FragmentStarted", "frag: " + fragmentIdx.toString())

        if (interstitialBuilder != null) {
            interstitialBuilder!!.show(requireContext())
            Log.d("FragmentStarted", "fragm1: " + fragmentIdx.toString())
        } else {
//            startActivity(myIntent)
//            if (isFinish) {
//                requireActivity().finish()
//            }
            Log.d("FragmentStarted", "fragm2: " + fragmentIdx.toString())

            if (viewx != null) {
                Log.d("FrafmentStarted", fragmentIdx.toString())
                findNavController(viewx).navigate(fragmentIdx, bundlex)
                if (isBackStack) {
//                    findNavController().popBackStack()
                }

            }
        }
        loadAds()
    }*//*

        */
/* fun loadAds(){
        interstitialBuilder = InterstitialBuilder.create()
            .setAdId(AdId.DEFAULT)
            .setOnDoneCallback {
                interstitialBuilder!!.preload(getContext());
                if(myType.equals("activity")) {
                    startActivity(myIntent)
                    if(isFinish == true) {
                        requireActivity().finish()
                    }

                }
                else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isFinish == true) {
                            Navigation.findNavController(
                                myView!!
                            ).popBackStack()
                        }
                        Navigation.findNavController(
                            myView!!
                        ).navigate(id!!)
                    }, 0)


                }
            }
            .preload(requireContext())
    }

    fun showAd(intent: Intent?, fragment: Int?, type: String, bool: Boolean?, view: View){
        isFinish=bool
        myIntent=intent
        id=fragment
        myType=type
        myView=view
        interstitialBuilder!!.show(requireContext());


    }*//*


    }
}*/
