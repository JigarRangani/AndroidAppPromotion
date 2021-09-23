package com.valora.promotions.ui.update

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
import com.valora.promotions.databinding.AppUpdateDialogBinding
import com.valora.promotions.network.ApiServices
import com.valora.promotions.response.PromotionResponse
import com.valora.promotions.ui.*
import com.valora.promotions.ui.update.UpdateDialog.Companion.TAG
import com.valora.promotions.ui.update.UpdateDialog.Companion.binding
import retrofit2.Call
import retrofit2.Response

class UpdateDialog {

    /***
     * Positions For Alert Dialog
     * */
    enum class POSITIONS {
        CENTER, BOTTOM
    }

    companion object {
        var TAG: String = "UpdateDialog"
        private lateinit var layoutInflater: LayoutInflater
        lateinit var binding: AppUpdateDialogBinding
        lateinit var preferenceManager: PromotionSharedPreferenceManager

        /***
         * core method For Alert Dialog
         * */
        fun build(
            context: Activity
        ): AlertDialog {
            layoutInflater = LayoutInflater.from(context)
            binding = AppUpdateDialogBinding.inflate(layoutInflater)
            val alertDialog =
                AlertDialog.Builder(
                    context, R.style.update_dialog
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
    position: UpdateDialog.POSITIONS = UpdateDialog.POSITIONS.BOTTOM
): AlertDialog {
    binding.run {
        val layoutParams = mainView.layoutParams as RelativeLayout.LayoutParams
        if (position == UpdateDialog.POSITIONS.CENTER) {
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        } else if (position == UpdateDialog.POSITIONS.BOTTOM) {
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
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
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

fun AlertDialog.checkUpdate(packageName: String, version: String, platform: String): AlertDialog {
    val apiServices = ApiServices.create().getAppPromotionData(packageName, version, platform)
    apiServices.enqueue(object : retrofit2.Callback<PromotionResponse> {

        override fun onResponse(
            call: Call<PromotionResponse>,
            response: Response<PromotionResponse>
        ) {
            Log.e(TAG, "onResponse")
            when {
                response.code() in 200..399 -> {
                    if (!response.body()?.data.isNullOrEmpty()) {
                        Log.e(
                            TAG,
                            "00onResponse code==${response.body()!!.data!![0].promotionAppList!![0].promotionAppImgUrl}"
                        )
                        response.body()!!.data!![0].let {
                            it.run {
                                if (appUpdateAndroid != null && appForcefullyUpdateAndroid != null) {
                                    if (appForcefullyUpdateAndroid!! && appUpdateAndroid!!) {
                                        this@checkUpdate.setCancelable(false)
                                        this@checkUpdate.setCanceledOnTouchOutside(false)
                                        this@checkUpdate.title(
                                            "Update required"
                                        )
                                        this@checkUpdate.subTitle(
                                            "Please, update this app to get better experience"
                                        )

                                        this@checkUpdate.onPositive(
                                            "Download", packageName = packageName
                                        )
                                        binding.noButton.visibility = View.GONE
                                        this@checkUpdate.show()
                                    } else if (appForcefullyUpdateAndroid!!) {
                                        this@checkUpdate.setCancelable(false)
                                        this@checkUpdate.setCanceledOnTouchOutside(false)
                                        this@checkUpdate.title(
                                            "Update required"
                                        )
                                        this@checkUpdate.subTitle(
                                            "Please, update this app to get better experience"
                                        )

                                        this@checkUpdate.onPositive(
                                            "Download", packageName = packageName
                                        )
                                        binding.noButton.visibility = View.GONE
                                        this@checkUpdate.show()
                                    } else if (appUpdateAndroid!!) {
                                        this@checkUpdate.setCancelable(true)
                                        this@checkUpdate.setCanceledOnTouchOutside(true)
                                        this@checkUpdate.title(
                                            "New update available"
                                        )
                                        this@checkUpdate.subTitle(
                                            "Hey, we recommends that you update to latest version."
                                        )

                                        this@checkUpdate.onPositive(
                                            "Download", packageName = packageName
                                        )
                                        binding.noButton.setOnClickListener {
                                            dismiss()
                                        }
                                        binding.noButton.visibility = View.VISIBLE
                                        this@checkUpdate.show()
                                    }
                                } else {
                                    this@checkUpdate.hide()
                                }

                            }
                        }
                    }
                }
                response.code() == 404 -> {
                    this@checkUpdate.hide()
                }
                response.code() == 401 -> {
                    this@checkUpdate.hide()
                }
                else -> {
                    this@checkUpdate.hide()
                }
            }

        }

        override fun onFailure(call: Call<PromotionResponse>, t: Throwable) {
            Log.e(TAG, "onFailure == ${t.printStackTrace()}")
            this@checkUpdate.hide()

        }

    })
    return this
}


private fun View.show() {
    this.visibility = View.VISIBLE
}

