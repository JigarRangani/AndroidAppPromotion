package com.valora.promotions.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valora.promotions.response.Data
import java.io.Serializable

class PromotionResponse : Serializable {
    @Expose
    @SerializedName("RESPONSE_CODE")
    val RESPONSE_CODE: String? = null

    @Expose
    @SerializedName("data")
    val data: List<Data>? = null

    @Expose
    @SerializedName("message")
    val message: String? = null
}