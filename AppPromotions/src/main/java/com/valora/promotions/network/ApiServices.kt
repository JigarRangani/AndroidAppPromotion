package com.valora.promotions.network

import com.valora.promotions.response.PromotionResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServices {

    @GET(ApiEndPoint.PROMOTION)
    fun getAppPromotionData(
        @Query("app_id") app_id:String
    ): Call<PromotionResponse>

    companion object {
        var BASE_URL = "http://192.168.0.108:8080/api/application/"

        fun create(): ApiServices {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiServices::class.java)

        }
    }
}