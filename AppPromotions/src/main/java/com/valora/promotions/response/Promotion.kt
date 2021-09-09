package com.valora.promotions.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Promotion: Serializable {
    @SerializedName("promotion_app_name")
    var promotionAppName: String? = null

    @SerializedName("promotion_app_id")
    var promotionAppPackageName: String? = null

    @SerializedName("priority")
    var promotionAppPriority: String? = null

    @SerializedName("promotion_app_url")
    var promotionAppUrl: String? = null

    @SerializedName("promotion_app_img")
    var promotionAppImgUrl: String? = null

    @SerializedName("title")
    var promotionTitle: String? = null

    @SerializedName("description")
    var promotionDescription: String? = null

    @SerializedName("button_name")
    var promotionDialogButtonName: String? = null

}