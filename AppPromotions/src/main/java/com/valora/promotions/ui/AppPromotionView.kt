//package com.valora.promotions.ui
//
//import android.content.ActivityNotFoundException
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.util.AttributeSet
//import android.util.TypedValue
//import android.view.View
//import android.widget.Button
//import android.widget.FrameLayout
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.cardview.widget.CardView
//import androidx.constraintlayout.widget.ConstraintLayout
//import com.valora.promotions.R
//
//
//class AppPromotionView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
//    private val tvTitle: TextView
//    private val tvSubtitle: TextView
//    private val imgRecommendedApp: ImageView
//    private val btnDownload: Button
//    private val btnCancel: Button
//    private var constraintLayout: ConstraintLayout
//    private var cardApp: CardView
//    private var promotionAppPackageName: String? = null
//
//    //    private var packageNames: String? = null
//    var onCancelClickListener: OnCancelClickListener? = null
//    var onDownloadClickListener: OnDownloadClickListener? = null
//
//    init {
//        inflate(context, R.layout.app_promotion_dialog, this)
//        tvTitle = findViewById(R.id.tvTitle)
//        tvSubtitle = findViewById(R.id.tvSubtitle)
//        imgRecommendedApp = findViewById(R.id.imgRecommendedApp)
//        btnDownload = findViewById(R.id.btnPlayStore)
//        constraintLayout = findViewById(R.id.constraintView)
//        cardApp = findViewById(R.id.cardApp)
//        btnCancel = findViewById(R.id.btnCancel)
//
//
//        val attributes = context.obtainStyledAttributes(attrs, R.styleable.AppPromotionView)
////        promotionAppPackageName = attributes.getString(R.styleable.AppPromotionView_APV_promotion_package_name)
//        tvTitle.text = attributes.getString(R.styleable.AppPromotionView_APV_title)
////        tvTitle.textSize = attributes.getInt(R.styleable.AppPromotionView_APV_title_textSize, 15).toFloat()
//        imgRecommendedApp.setImageDrawable(attributes.getDrawable(R.styleable.AppPromotionView_APV_image_app))
//        tvTitle.text = attributes.getString(R.styleable.AppPromotionView_APV_title)
//        tvSubtitle.text = attributes.getString(R.styleable.AppPromotionView_APV_subTitle)
//
//
//        btnDownload.setOnClickListener {
//            try {
//                rateApp()
//            } catch (e: NullPointerException) {
//                e.printStackTrace()
//                val uri: Uri = Uri.parse("market://details?id=${context.packageName}")
//                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
//                // To count with Play market backstack, After pressing back button,
//                // to taken back to our application, we need to add following flags to intent.
//                goToMarket.addFlags(
//                    Intent.FLAG_ACTIVITY_NO_HISTORY or
//                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
//                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//                )
//                try {
//                    context.startActivity(goToMarket)
//                } catch (e: ActivityNotFoundException) {
//                    context.startActivity(
//                        Intent(
//                            Intent.ACTION_VIEW,
//                            Uri.parse("http://play.google.com/store/apps/details?id=${context.packageName}")
//                        )
//                    )
//                }
//            }
//
////            try {
////                startActivity(context,Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
////            } catch (e: ActivityNotFoundException) {
////                startActivity(context,Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
////            }
//        }
//        btnCancel.setOnClickListener {
//
//        }
////        cardApp.elevation = attributes.getInt(R.styleable.AppPromotionView_APV_title_textSize,8).toFloat()
//        attributes.recycle()
//    }
//
//    private fun rateApp() {
//        promotionAppPackageName?.let {
//            val uri: Uri = Uri.parse("market://details?id=$it")
//            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
//            // To count with Play market backstack, After pressing back button,
//            // to taken back to our application, we need to add following flags to intent.
//            goToMarket.addFlags(
//                Intent.FLAG_ACTIVITY_NO_HISTORY or
//                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
//                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//            )
//            try {
//                context.startActivity(goToMarket)
//            } catch (e: ActivityNotFoundException) {
//                context.startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse("http://play.google.com/store/apps/details?id=$it")
//                    )
//                )
//            }
//        }
//        if (promotionAppPackageName == null) {
////            onDownloadClickListener!!.onDownloadClick()
//        }
//    }
//
//    interface OnDownloadClickListener {
//        fun onDownloadClick(appPackageName: String)
//    }
//
//    interface OnCancelClickListener {
//        fun onCancelClick()
//    }
//
//    fun setDownloadListener(onDownloadClickListener: OnDownloadClickListener) {
//        this.onDownloadClickListener = onDownloadClickListener
//    }
//
//    fun setCancelListener(onCancelClickListener: OnCancelClickListener) {
//        this.onCancelClickListener = onCancelClickListener
//    }
//
//    fun setPromotionPackageName(packageName: String) {
//        promotionAppPackageName = packageName
//    }
//
//    fun getDownloadButton(): View {
//        return btnDownload
//    }
//
//    fun getCancelButton(): View {
//        return btnCancel
//    }
//
//
//    fun setupCancelListener() {
//
//    }
//
//    fun setUpDownloadListener() {
//
//    }
//
//    fun setTitle(title: String) {
//        tvTitle.text = title
//    }
//
//    fun setTitleTextSize(textSize: Float) {
//        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
//    }
//
//    fun setCardElevation() {
//
//    }
//
//    fun setDescription(description: String) {
//        tvSubtitle.text = description
//    }
//
//    fun setSubTitleTextSize(textSize: Float) {
//        tvSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
//    }
//
//
//    fun setCardBackgroundColor(color: Int) {
//        constraintLayout.setBackgroundColor(color)
//    }
//
//
//}