package com.valora.promotions.ui.carousal

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.valora.promotions.R
import java.util.*


class OfferSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var adapter: SliderAdapter
    private var rview: RecyclerView
    private var ITEM_DELAY:Long = 600  //default delay

    init {
        inflate(context, R.layout.offer_slider, this)
        rview = findViewById(R.id.rvSlider)
        adapter = SliderAdapter()
        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.OfferSlider)
            ITEM_DELAY = attributes.getInteger(R.styleable.OfferSlider_offer_show_delay, 600).toLong()
            attributes.recycle()
        }
        setSliderAdapter()
    }

    private fun setSliderAdapter() {
        val radius = resources.getDimensionPixelSize(R.dimen._3sdp)
        val dotsHeight = resources.getDimensionPixelSize(R.dimen._7sdp)
        val color = ContextCompat.getColor(context, R.color.black)
        val colorIn = ContextCompat.getColor(context, R.color.white)
        rview.addItemDecoration(
            DotsIndicatorDecoration(
                radius,
                radius * 3,
                dotsHeight,
                color,
                color
            )
        )
        val linearSnapHelper = LinearSnapHelper()
        val linearLayoutManager =
            SpeedyLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rview.layoutManager = linearLayoutManager
        linearSnapHelper.attachToRecyclerView(rview)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                try {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < adapter.itemCount - 1) {
                        linearLayoutManager.smoothScrollToPosition(
                            rview,
                            RecyclerView.State(),
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1
                        )
                    } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        linearLayoutManager.smoothScrollToPosition(
                            rview,
                            RecyclerView.State(),
                            0
                        )
                    }
                } catch (e: Exception) {
                }
            }
        }, 0, ITEM_DELAY)
        rview.adapter = adapter
    }


    fun setData(offerList: MutableList<String>) {
        adapter.updateItems(offerList)
    }

}