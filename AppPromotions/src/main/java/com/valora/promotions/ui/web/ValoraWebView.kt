package com.valora.promotions.ui.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.valora.promotions.databinding.ActivityValoraWebViewBinding


class ValoraWebView : AppCompatActivity() {

    var privacyUrl: String? = null
    var isPrivacy = false
    var isTerms = false
    var url: String? = null
    var version: String? = null
    var platform: String? = null
    var pName: String? = null
    var head: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValoraWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isPrivacy = intent.getBooleanExtra("isPrivacy", false)
        pName = intent.getStringExtra("packageName")
        url = intent.getStringExtra("url")
        version = intent.getStringExtra("version")
        platform = intent.getStringExtra("platform")
        isTerms = intent.getBooleanExtra("isTerms", false)
        setUpView()
        backScreen()
    }

    private fun backScreen() {
        binding.backbtn.setOnClickListener {
            finish()
        }
    }

    private fun setUpView() {
        if (isPrivacy) {
            setHeader("Privacy and Policy")
        } else if (isTerms) {
            setHeader("Terms & Conditions")
        }
        binding.run {
            // Get the web view settings instance
            val settings = webView.settings
            // Enable java script in web view
            settings.javaScriptEnabled = true
            // Enable and setup web view cache
            settings.setAppCacheEnabled(true)
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.setAppCachePath(cacheDir.path)


            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url!!)
                    return true
                }
            }
            url?.let {
                webView.loadUrl(it)
            }

        }
    }

    private fun setHeader(title: String) {
        binding.tvTitle.text = title
    }

    companion object {
        val TAG = "ValoraWebView"
        val obj: ValoraWebView = ValoraWebView()
        lateinit var binding: ActivityValoraWebViewBinding
        fun getIntent(context: Context): Intent {
            return Intent(context, ValoraWebView::class.java)
        }
    }
}
