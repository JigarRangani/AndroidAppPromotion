package com.valora.promotions.ui.rate

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.valora.promotions.PromotionSharedPreferenceManager
import com.valora.promotions.R
import com.valora.promotions.databinding.DialogRateBinding
import com.valora.promotions.ui.rate.RateDialog.Companion.binding

class RateDialog {
    enum class POSITIONS {
        CENTER, BOTTOM
    }

    companion object {
        var TAG: String = "RateDialog"
        private lateinit var layoutInflater: LayoutInflater
        lateinit var binding: DialogRateBinding
        lateinit var preferenceManager: PromotionSharedPreferenceManager

        /***
         * main method For Promotion Dialog
         * */
        fun build(
            context: Activity
        ): AlertDialog {
            layoutInflater = LayoutInflater.from(context)
            binding = DialogRateBinding.inflate(layoutInflater)
            val alertDialog =
                AlertDialog.Builder(
                    context, R.style.rate_dialog
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
 * Positions of Alert Dialog
 * */
fun AlertDialog.position(
    position: RateDialog.POSITIONS = RateDialog.POSITIONS.BOTTOM
): AlertDialog {
    binding.run {
        val layoutParams = mainView.layoutParams as RelativeLayout.LayoutParams
        if (position == RateDialog.POSITIONS.CENTER) {
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        } else if (position == RateDialog.POSITIONS.BOTTOM) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        }
        mainView.layoutParams = layoutParams
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
        btnLater.show()
        btnLater.text = text.trim()
        if (textColor != null) {
            btnLater.setTextColor(textColor)
        }
        if (buttonBackgroundColor != null) {
            btnLater.setBackgroundResource(buttonBackgroundColor)
        }
        btnLater.setOnClickListener {
            action?.invoke()
            dismiss()
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
        btnRate.show()
        if (buttonBackgroundColor != null) {
            btnRate.setBackgroundResource(buttonBackgroundColor)
        }
        if (textColor != null) {
            btnRate.setTextColor(textColor)
        }
        btnRate.text = text.trim()
        btnRate.setOnClickListener {
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

fun AlertDialog.showRateDialog(packageName: String, context: Activity): AlertDialog {
    binding.run {
        this@showRateDialog.stateLaterButton(true)
        binding.btnRate.setTextColor(context.resources.getColor(R.color.colorPrimary))

        binding.rateBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating > 0) {
                this@showRateDialog.stateRateButton(true)
            } else {
                this@showRateDialog.stateRateButton(false)
            }
        }

        binding.btnLater.setOnClickListener {
//            preferenceManager.counterRate = 0
            dismiss()
        }

        binding.btnRate.setOnClickListener {
            if (binding.rateBar.rating >= 3) {
                Log.e("rating", "${binding.rateBar.rating}")
                if (!packageName.isEmpty()) {
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
                dismiss()
            } else if (binding.rateBar.rating > 0 && binding.rateBar.rating < 3) {
                Log.e("rating", "${binding.rateBar.rating}")
                val bundle = Bundle()
                bundle.putString("Rating", "Rating: ${binding.rateBar.rating}")
//                preferenceManager.counterRate = 0
                dismiss()
            }
        }
        this@showRateDialog.show()
    }
    return this
}
//
//fun onRateClick(packageName: String,context: Activity) {
//    val uri: Uri = Uri.parse("market://details?id=${packageName}")
//    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
//    // To count with Play market backstack, After pressing back button,
//    // to taken back to our application, we need to add following flags to intent.
//    goToMarket.addFlags(
//        Intent.FLAG_ACTIVITY_NO_HISTORY or
//                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
//                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//    )
//    try {
//        context.startActivity(goToMarket)
//    } catch (e: ActivityNotFoundException) {
//        context.startActivity(
//            Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("http://play.google.com/store/apps/details?id=${packageName}")
//            )
//        )
////        preferenceManager.isRated = true
//    }
//}


fun AlertDialog.stateLaterButton(state: Boolean): AlertDialog {
    binding.run {
        btnLater.run {
            isSelected = state
            if (state) {
                setTextColor(resources.getColor(R.color.white))
            } else {
                setTextColor(resources.getColor(R.color.colorPrimary))
            }
        }
    }
    return this
}

private fun AlertDialog.stateRateButton(state: Boolean): AlertDialog {
    binding.run {
        btnRate.run {
            isSelected = state
            if (state) {
                setTextColor(resources.getColor(R.color.white))
            } else {
                setTextColor(resources.getColor(R.color.colorPrimary))
            }
            stateLaterButton(!state)
        }
    }
    return this
}


private fun View.show() {
    this.visibility = View.VISIBLE
}