package com.valora.promotions.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @Expose
    @SerializedName("promotion")
    var promotionAppList: MutableList<Promotion>? = null

    @Expose
    @SerializedName("app_name")
    var appName: String? = null

    @Expose
    @SerializedName("url")
    var appUrl: String? = null

    @Expose
    @SerializedName("app_id")
    var appPackageName: String? = null

    @Expose
    @SerializedName("img")
    var appImgUrl: String? = null

}