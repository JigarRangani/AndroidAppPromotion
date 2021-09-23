package com.valora.promotions.request

import com.google.gson.annotations.SerializedName

class PromotionRequest(
    @SerializedName("app_id")
    var app_Id: String? = null,
    @SerializedName("version")
    var app_version: String? = null,
    @SerializedName("plateform")
    var plateform: String? = null,
)