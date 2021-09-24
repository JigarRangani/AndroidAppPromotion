package com.valora.promotions.ui.carousal

import android.content.Context
import android.widget.FrameLayout
import com.smarteist.autoimageslider.SliderView
import com.valora.promotions.R

class OfferSlider(context: Context) : FrameLayout(context) {

    private val offerSlider: SliderView
    init {
        inflate(context, R.layout.offer_slider, this)
        offerSlider = findViewById(R.id.imageSlider)
    }

    fun setData(list:MutableList<String>){
//        offerSlider.
    }
}