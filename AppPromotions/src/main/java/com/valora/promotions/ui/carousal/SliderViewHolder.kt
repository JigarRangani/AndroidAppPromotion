package com.valora.promotions.ui.carousal

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.valora.promotions.databinding.ImageSliderLayoutItemBinding

class SliderViewHolder(var binding: ImageSliderLayoutItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(item: String) {
        item.run {
            binding.run {
                Glide.with(ivAutoImageSlider.context)
                    .load(item)
                    .centerInside()
                    .into(ivAutoImageSlider)
            }
        }
    }

}