package com.valora.promotions.ui.carousal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.valora.promotions.network.ApiServices
import com.valora.promotions.response.PromotionResponse
import com.valora.promotions.ui.web.ValoraWebView
import retrofit2.Call
import retrofit2.Response

class SliderHandler {
    companion object {
        fun getData(
            packageName: String,
            version: String,
            platform: String,
        ): LiveData<List<String>?> {
            val offerList=  MutableLiveData<List<String>?>()
            val apiServices =
                ApiServices.create().getAppPromotionData(packageName, version, platform)
            apiServices.enqueue(object : retrofit2.Callback<PromotionResponse> {
                override fun onResponse(
                    call: Call<PromotionResponse>,
                    response: Response<PromotionResponse>
                ) {
                    when {
                        response.code() in 200..399 -> {
                            if (!response.body()?.data.isNullOrEmpty()) {
                                Log.e(
                                    ValoraWebView.TAG,
                                    "onResponse code==${response.body()!!.data!![0].promotionAppList!![0].promotionAppImgUrl}"
                                )
                                response.body()!!.data!![0].let {
                                    it.run {
                                        Log.e(ValoraWebView.TAG, "Privacy Policy==${privacyPolicy}")
                                        Log.e(ValoraWebView.TAG, "Terms ==${termsAndConditions}")
                                        Log.e(ValoraWebView.TAG, "offer ==${offers!![0]}")
                                        offerList.postValue(offers)
                                    }
                                }
                            }
                        }
                        response.code() == 404 -> {
                        }
                        response.code() == 401 -> {
                        }
                        else -> {
                        }
                    }

                }

                override fun onFailure(call: Call<PromotionResponse>, t: Throwable) {
                    Log.e("SliderHandler", "onFailure == ${t.printStackTrace()}")
                }

            })
            return offerList
        }
    }
}