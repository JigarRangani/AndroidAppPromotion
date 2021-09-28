package com.valora.promotion

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.valora.promotion.databinding.ActivityMainBinding
import com.valora.promotions.ui.carousal.SliderHandler
import com.valora.promotions.ui.promotion.PromotionDialog
import com.valora.promotions.ui.promotion.callApi
import com.valora.promotions.ui.rate.RateDialog
import com.valora.promotions.ui.rate.showRateDialog
import com.valora.promotions.ui.update.UpdateDialog
import com.valora.promotions.ui.update.checkUpdate
import com.valora.promotions.ui.web.WebViewHandler

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val imageList =
        mutableListOf<String>("https://i.picsum.photos/id/543/1920/1080.jpg?hmac=UtCWk-nZ7XAtZCSidy4USQbgyQxieO_0Gs08mByubp4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageList.add("https://i.picsum.photos/id/136/1920/1080.jpg?hmac=p_25g_z8n8u6702vBojLlRnFknMV9t-34uEKyhk1NVs")
        imageList.add("https://i.picsum.photos/id/136/1920/1080.jpg?hmac=p_25g_z8n8u6702vBojLlRnFknMV9t-34uEKyhk1NVs")
        imageList.add("https://i.picsum.photos/id/1060/200/300.jpg?hmac=xYtFmeYcfydIF3-Qybnra-tMwklX69T52EtGd-bacBI")
        imageList.add("https://i.picsum.photos/id/65/1920/1080.jpg?hmac=906OwjpX9_xvs1WWE2FOcXxg_a9e3DufNz8XIomLAS4")
        imageList.add("https://picsum.photos/seed/picsum/1000/500")
        imageList.add("https://picsum.photos/seed/picsum/600/900")
        imageList.add("https://i.picsum.photos/id/27/200/300.jpg?hmac=cxfyms4Ce9ExYqZqSCKEppGQpmi8rRNNaf46Lwr5iqA")
        imageList.add("https://picsum.photos/seed/picsum/720/1600")
        imageList.add("https://picsum.photos/seed/picsum/800/1600")
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
                it.showRateDialog("com.valora.indianartical", this)
            }
        }


        binding.btnUpdate.setOnClickListener {
            UpdateDialog.build(this)?.let {
                it.checkUpdate("5", "1.0", "android")
            }
        }

        binding.run {
            btnPrivacy.setOnClickListener {
                WebViewHandler.Builder(this@MainActivity).openPrivacy("5", "1.0", "android")
            }
        }

        binding.run {
            btnTerms.setOnClickListener {
                WebViewHandler.Builder(this@MainActivity).openTerms("5", "1.0", "android")
            }
        }
        binding.openOffer.setOnClickListener {
//            binding.offerSlider.setData(imageList)
            SliderHandler.getData("5", "1.0", "android")?.let {
                it.observe(this) {
                    Log.e("main activity", it!![0])
                    binding.offerSlider.setData(it.toMutableList())
                }

            }
        }

    }
}