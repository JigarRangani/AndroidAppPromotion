package com.valora.promotions.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.valora.promotions.PromotionSharedPreferenceManager
import com.valora.promotions.R
import com.valora.promotions.databinding.AppPromotionDialogBinding
import com.valora.promotions.network.ApiServices
import com.valora.promotions.response.Promotion
import com.valora.promotions.response.PromotionResponse
import com.valora.promotions.ui.PromotionDialog.Companion.TAG
import com.valora.promotions.ui.PromotionDialog.Companion.binding
import com.valora.promotions.ui.PromotionDialog.Companion.preferenceManager
import com.valora.promotions.ui.PromotionDialog.Companion.priorityList
import com.valora.promotions.ui.PromotionDialog.Companion.result
import retrofit2.Call
import retrofit2.Response
import java.util.*


class PromotionDialog {

    /***
     * Positions For Promotion Dialog
     * */
    enum class POSITIONS {
        CENTER, BOTTOM
    }

    companion object {
        var TAG: String = "PromotionDialog"
        private lateinit var layoutInflater: LayoutInflater
        lateinit var binding: AppPromotionDialogBinding
        lateinit var preferenceManager: PromotionSharedPreferenceManager
        lateinit var result: List<String>
        lateinit var priorityList: MutableList<Promotion>

        /***
         * main method For Promotion Dialog
         * */
        fun build(
            context: Activity
        ): AlertDialog {
            layoutInflater = LayoutInflater.from(context)
            binding = AppPromotionDialogBinding.inflate(layoutInflater)
            val alertDialog =
                AlertDialog.Builder(
                    context, R.style.full_screen_dialog
                )
                    .setView(binding.root)
            val alert: AlertDialog = alertDialog.create()
            preferenceManager = PromotionSharedPreferenceManager(context)
            // Let's start with animation work. We just need to create a style and use it here as follows.
            //Pop In and Pop Out Animation yet pending
//            alert.window?.attributes?.windowAnimations = R.style.SlidingDialogAnimation
            return alert
        }
    }

}

/***
 * Title Properties For Alert Dialog
 * */
fun AlertDialog.title(
    title: String,
    fontStyle: Typeface? = null,
    titleColor: Int = 0
): AlertDialog {
    binding.run {
        tvTitle.text = title
        if (fontStyle != null) {
            tvTitle.typeface = fontStyle
        }
        if (titleColor != 0) {
            tvTitle.setTextColor(titleColor)
        }
        tvTitle.show()
    }
    return this
}

/***
 * Dialog Background properties For Alert Dialog
 * */
fun AlertDialog.background(
    dialogBackgroundColor: Int? = null
): AlertDialog {
    if (dialogBackgroundColor != null) {
        binding.mainView.setBackgroundResource(dialogBackgroundColor)
    }
    return this
}

/***
 * Positions of Alert Dialog
 * */
fun AlertDialog.position(
    position: PromotionDialog.POSITIONS = PromotionDialog.POSITIONS.BOTTOM
): AlertDialog {
    binding.run {
        val layoutParams = mainView.layoutParams as RelativeLayout.LayoutParams
        if (position == PromotionDialog.POSITIONS.CENTER) {
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        } else if (position == PromotionDialog.POSITIONS.BOTTOM) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        }
        mainView.layoutParams = layoutParams
    }
    return this
}

/***
 * Sub Title or Body of Alert Dialog
 * */
fun AlertDialog.subTitle(
    body: String,
    fontStyle: Typeface? = null,
    color: Int = 0
): AlertDialog {
    binding.run {
        tvSubtitle.text = body.trim()
        tvSubtitle.show()
        if (fontStyle != null) {
            tvSubtitle.typeface = fontStyle
        }
        if (color != 0) {
            tvSubtitle.setTextColor(color)
        }
    }
    return this
}

/***
 * Icon of  Alert Dialog
 * */
fun AlertDialog.icon(
    icon: Int,
    animateIcon: Boolean = false
): AlertDialog {
    binding.run {
        imgRecommendedApp.setImageResource(icon)
        imgRecommendedApp.show()
        // Pulse Animation for Icon
        if (animateIcon) {
            val pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse)
            imgRecommendedApp.startAnimation(pulseAnimation)
        }
    }

    return this
}

/***
 * Icon of  Alert Dialog
 * */
fun AlertDialog.networkImage(
    imgUrl: String?,
    animateIcon: Boolean = false
): AlertDialog {
    binding.run {

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide
            .with(context)
            .load(imgUrl)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(imgRecommendedApp)
        imgRecommendedApp.show()
        // Pulse Animation for Icon
        if (animateIcon) {
            val pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse)
            imgRecommendedApp.startAnimation(pulseAnimation)
        }
    }

    return this
}


/***
 * onPositive Button Properties For Alert Dialog
 *
 * No Need to call dismiss(). It is calling already
 * */
fun AlertDialog.onPositive(
    text: String,
    buttonBackgroundColor: Int? = null,
    textColor: Int? = null,
    action: (() -> Unit)? = null,
    isAction: Boolean? = false,
    packageName: String? = null
): AlertDialog {
    binding.run {
        yesButton.show()
        if (buttonBackgroundColor != null) {
            yesButton.setBackgroundResource(buttonBackgroundColor)
        }
        if (textColor != null) {
            yesButton.setTextColor(textColor)
        }
        yesButton.text = text.trim()
        yesButton.setOnClickListener {
            if (isAction == true) {
                action?.invoke()
            } else if (!packageName.isNullOrEmpty()) {
                val uri: Uri = Uri.parse("market://details?id=$packageName")
                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(
                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                )
                try {
                    context.startActivity(goToMarket)
                    setDataInArrayList(packageName)
                    dismiss()
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,

                            Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                    dismiss()
                }
            }
        }
    }
    return this
}

/***
 * onNegative Button Properties For Alert Dialog
 *
 * No Need to call dismiss(). It is calling already
 * */
fun AlertDialog.onNegative(
    text: String,
    buttonBackgroundColor: Int? = null,
    textColor: Int? = null,
    action: (() -> Unit)? = null
): AlertDialog {
    binding.run {
        noButton.show()
        noButton.text = text.trim()
        if (textColor != null) {
            noButton.setTextColor(textColor)
        }
        if (buttonBackgroundColor != null) {
            noButton.setBackgroundResource(buttonBackgroundColor)
        }
        noButton.setOnClickListener {
            action?.invoke()
            dismiss()
        }
    }
    return this
}

fun AlertDialog.callApi(packageName: String): AlertDialog {
    val apiServices = ApiServices.create().getAppPromotionData(packageName)
    apiServices.enqueue(object : retrofit2.Callback<PromotionResponse> {

        override fun onResponse(
            call: Call<PromotionResponse>,
            response: Response<PromotionResponse>
        ) {
            Log.e(TAG, "onResponse")
            var isAlertShow = false
            when {
                response.code() in 200..399 -> {
                    if (!response.body()?.data.isNullOrEmpty()) {
                        Log.e(TAG, "00onResponse code==${response.body()!!.data!![0].promotionAppList!![0].promotionAppImgUrl}")
                        response.body()!!.data!![0].let {
                            if (preferenceManager.promotionList.trim().isEmpty()) {
                                result = mutableListOf()
                                it.promotionAppList!!.sortBy { it.promotionAppPriority }
                                priorityList = it.promotionAppList!!
                                isAlertShow= true
                            } else {
                                result = preferenceManager.promotionList.split(",").map { it -> it.trim() }
                                it.promotionAppList!!.sortBy { it.promotionAppPriority }
                                priorityList = mutableListOf()
                                out@ for (i in result.indices) {
                                    for (j in it.promotionAppList!!.indices) {
                                        if (result[i] == it.promotionAppList!![j].promotionAppPackageName) {
                                            Log.e("i =$i  j=$j","result[$i]= ${result[i]}")
                                            Log.e("i =$i  j=$j","promotionAppList[$j].promotionAppPackageName= ${it.promotionAppList!![j].promotionAppPackageName}")
                                        break
                                        } else {
                                            Log.e("i =$i  j=$j","result[$i]= ${result[i]}")
                                            Log.e("i =$i  j=$j","promotionAppList[$j].promotionAppPackageName= ${it.promotionAppList!![j].promotionAppPackageName}")
                                            priorityList.add(it.promotionAppList!![j])
                                            isAlertShow = true
                                        }
                                    }
                                }
                            }


                            if (isAlertShow) {
                                //  if (it.promotionAppList!!.size >= result.size) {
                                Log.e(TAG, "11onResponse code==${response.code()}")
                                binding.run {
                                    Log.e(TAG, "22onResponse code==${response.code()}")
                                    if (!it.promotionAppList.isNullOrEmpty()) {
                                        if (preferenceManager.firstRun) {
                                            this@callApi.onPositive(
                                                priorityList[0].promotionDialogButtonName
                                                    ?: "Download",
                                                packageName = priorityList[0].promotionAppPackageName
                                            )
                                        } else {
                                            preferenceManager.firstRun = true
                                        }
                                        val index = 0
                                        if (!priorityList.isNullOrEmpty()) {
                                            this@callApi.title(
                                                priorityList[index].promotionTitle
                                                    ?: "Recommended"
                                            )
                                            this@callApi.subTitle(
                                                priorityList[index].promotionDescription
                                                    ?: "Please, checkout our other interesting apps"
                                            )

                                            this@callApi.networkImage(priorityList[index].promotionAppImgUrl)

                                            this@callApi.onPositive(
                                                priorityList[index].promotionDialogButtonName
                                                    ?: "Download",
                                                packageName = priorityList[0].promotionAppPackageName
                                            )
                                            this@callApi.onNegative("Cancel")
                                            this@callApi.show()
                                        }
                                    }
                                }
//                                    } else {
//                                        this@callApi.hide()
//                                return this@callApi.dismiss()
                            }

                        }

//                    onSuccess(response.body())
//                    sharedPrefsHelper.checkAndChangeTocken(response.headers())
                    }
                }
                response.code() == 404 -> {
                    this@callApi.hide()
//                    binding.noButton.setOnClickListener {
//                        dismiss()
//                    }
//                    val gson = Gson()
//                    val errorEntity: ErrorEntity? = gson.fromJson(
//                        response.errorBody()!!.charStream(),
//                        ErrorEntity::class.java
//                    )
//                    errorEntity?.let { notFound(it) }
                }
                response.code() == 401 -> {
//                    binding.noButton.setOnClickListener {
//                        dismiss()
//                    }
                    this@callApi.hide()
//                    if (!sharedPrefsHelper.getToken().isNullOrEmpty()) {
//                        BaseApplication.currentActivity()?.let {
//                            CommonLogoutDialog().show(
//                                it.supportFragmentManager,
//                                CommonLogoutDialog.TAG
//                            )
//                        }
//                    }
                }
                else -> {
                    this@callApi.hide()
//                    binding.noButton.setOnClickListener {
//                        dismiss()
//                    }
//                    val gson = Gson()
//                    val errorEntity: ErrorEntity? = gson.fromJson(
//                        response.errorBody()!!.charStream(),
//                        ErrorEntity::class.java
//                    )
//                    errorEntity?.let { onError(it) }
                }
            }

        }

        override fun onFailure(call: Call<PromotionResponse>, t: Throwable) {
            Log.e(TAG, "onFailure == ${t.printStackTrace()}")
            this@callApi.hide()
//            break this@callApi;
//            binding.run {
//                    tvTitle.text = "fgdfgfdgd"
//
//                tvSubtitle.text = "ssdfsfsf"
////                            imgRecommendedApp.background = it.promotionAppList!![0].
//                yesButton.text = "download"
//
//                Glide
//                    .with(context)
//                    .load(R.drawable.ic_placeholder)
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_placeholder)
//                    .into(imgRecommendedApp)
//
//                binding.yesButton.setOnClickListener {
//                    val uri: Uri = Uri.parse("market://details?id=$packageName")
//                    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
//                    // To count with Play market backstack, After pressing back button,
//                    // to taken back to our application, we need to add following flags to intent.
//                    goToMarket.addFlags(
//                        Intent.FLAG_ACTIVITY_NO_HISTORY or
//                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
//                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//                    )
//                    try {
//                        context.startActivity(goToMarket)
//                    } catch (e: ActivityNotFoundException) {
//                        context.startActivity(
//                            Intent(
//                                Intent.ACTION_VIEW,
//                                Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
//                            )
//                        )
//                    }
//                }
//            }

        }

    })
    return this
}

fun setDataInArrayList(appId: String) {
    preferenceManager.promotionList += "$appId,"
    preferenceManager.counter += 1
//    if (!data.isNullOrEmpty() && !data[0].promotionAppList.isNullOrEmpty()) {
//        for (i in data[0].promotionAppList!!.indices) {
//            val list = arrayListOf<String>()
//            list.add(data[0].promotionAppList!![i].promotionAppPackageName!!)
//        }
//    }
}

//fun saveArrayList(list: ArrayList<String>?, key: String?, activity: Activity) {
//    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
//    val editor: SharedPreferences.Editor = prefs.edit()
//    val gson = Gson()
//    val json: String = gson.toJson(list)
//    editor.putString(key, json)
//    editor.apply()
//}
//
//fun getArrayList(key: String?, activity: Activity): ArrayList<String>? {
//    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
//    val gson = Gson()
//    val json: String? = prefs.getString(key, null)
//    val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
//    return gson.fromJson(json, type)
//}

private fun View.show() {
    this.visibility = View.VISIBLE
}