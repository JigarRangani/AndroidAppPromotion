package com.valora.promotions.ui.carousal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.valora.promotions.databinding.ImageSliderLayoutItemBinding

class SliderAdapter : RecyclerView.Adapter<SliderViewHolder>() {

    private lateinit var binding: ImageSliderLayoutItemBinding

    var data: MutableList<String>? = arrayListOf()

    fun updateItems(newItems: List<String>?) {
        val oldItems = ArrayList(this.data!!)
        this.data!!.clear()
        if (newItems != null) {
            this.data!!.addAll(newItems)
        }
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return data!!.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems!![newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems!![newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ImageSliderLayoutItemBinding.inflate(inflater, parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        data?.get(position)?.let {
            holder.setData(it)
        }
    }

    override fun getItemCount(): Int = (data?.size) ?: 0

}