package com.valora.promotions.ui.web

import android.app.Activity
import android.util.Log
import com.valora.promotions.network.ApiServices
import com.valora.promotions.response.PromotionResponse
import com.valora.promotions.ui.promotion.PromotionDialog
import retrofit2.Call
import retrofit2.Response

class WebViewHandler {

    class Builder(private val activity: Activity) {


        fun openPrivacy(
            packageName: String,
            version: String,
            platform: String
        ) {
            val url: String? = getData(packageName, version, platform, "privacy")
        }

        fun openTerms(
            packageName: String,
            version: String,
            platform: String
        ) {
            val url: String? = getData(packageName, version, platform, "terms")
        }

        fun getData(
            packageName: String,
            version: String,
            platform: String,
            urlOf: String
        ): String? {
            var url: String? = null
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
                                        if (urlOf == "privacy") {
                                            url = privacyPolicy
                                            activity.startActivity(
                                                ValoraWebView.getIntent(activity)
                                                    .putExtra("url", url)
                                                    .putExtra("isPrivacy", true)
                                                    .putExtra("packageName", packageName)
                                                    .putExtra("version", version)
                                                    .putExtra("platform", platform)
                                            )

                                        } else if (urlOf == "terms") {
                                            url = termsAndConditions
                                            activity.startActivity(
                                                ValoraWebView.getIntent(activity)
                                                    .putExtra("url", termsAndConditions)
                                                    .putExtra("isTerms", true)
                                                    .putExtra("packageName", packageName)
                                                    .putExtra("version", version)
                                                    .putExtra("platform", platform)
                                            )

                                        }
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
                    Log.e(PromotionDialog.TAG, "onFailure == ${t.printStackTrace()}")
                }

            })
            return url
        }

    }
}