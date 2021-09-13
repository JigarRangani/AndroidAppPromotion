package com.valora.promotion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valora.promotion.databinding.ActivityMainBinding
import com.valora.promotions.ui.PromotionDialog
import com.valora.promotions.ui.callApi

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.button.setOnClickListener {
            PromotionDialog.build(this)?.let {



             it.callApi("com.valora.indianartical1")
            }
        }
    }


}