package com.valora.promotion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valora.promotion.databinding.ActivityMainBinding
import com.valora.promotions.ui.promotion.PromotionDialog
import com.valora.promotions.ui.promotion.callApi
import com.valora.promotions.ui.rate.RateDialog
import com.valora.promotions.ui.rate.showRateDialog
import com.valora.promotions.ui.update.UpdateDialog
import com.valora.promotions.ui.update.checkUpdate
import com.valora.promotions.ui.web.ValoraWebView
import com.valora.promotions.ui.web.WebViewHandler

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    private fun initView() {
        binding.btnPromotion.setOnClickListener {
            PromotionDialog.build(this)?.let {
                it.callApi("5", "1.0", "android")
            }
        }

        binding.btnRate.setOnClickListener {
            RateDialog.build(this)?.let {
                it.showRateDialog("com.valora.indianartical",this)
            }
        }


        binding.btnUpdate.setOnClickListener {
            UpdateDialog.build(this)?.let {
                it.checkUpdate("5", "1.0", "android")
            }
        }

        binding.run {
            btnPrivacy.setOnClickListener {
                WebViewHandler.Builder(this@MainActivity).openPrivacy("5","1.0","android")
            }
        }

        binding.run {
            btnTerms.setOnClickListener {
                WebViewHandler.Builder(this@MainActivity).openTerms("5","1.0","android")
            }
        }
    }
}