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

    @SerializedName("offers")
    val offers: List<String>? = null

    @SerializedName("privacy_policy")
    val privacyPolicy: String? = null

    @SerializedName("terms_and_conditions")
    val termsAndConditions: String? = null

    @Expose
    @SerializedName("app_id")
    var appPackageName: String? = null

    @Expose
    @SerializedName("App_Update__Android")
    var appUpdateAndroid: Boolean? = null

    @Expose
    @SerializedName("App_ForcefullyUpdate_Android")
    var appForcefullyUpdateAndroid: Boolean? = null

    @Expose
    @SerializedName("App_Update__Ios")
    var appUpdateIos: Boolean? = null

    @Expose
    @SerializedName("App_ForcefullyUpdate_Ios")
    var appForcefullyUpdateIos: Boolean? = null

}