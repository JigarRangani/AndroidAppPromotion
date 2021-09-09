package com.valora.promotions.ui.request

import com.google.gson.annotations.SerializedName

class PromotionRequest(
    @SerializedName("app_id")
    var app_Id: String? = null,
)