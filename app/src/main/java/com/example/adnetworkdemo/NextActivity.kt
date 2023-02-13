package com.example.adnetworkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.allnetworkads.Ads
import com.example.allnetworkads.admob.ENUMS
import com.example.allnetworkads.adslib.TestAds

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        TestAds.getTestAds(this, ENUMS.APPLOVIN, packageName)

        Ads.loadInter(this, this)

    }

    override fun onBackPressed() {
        Ads.adOnBack(this, this)
    }
}