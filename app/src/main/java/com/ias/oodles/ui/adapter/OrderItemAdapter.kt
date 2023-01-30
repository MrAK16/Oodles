package com.ias.oodles.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ias.oodles.databinding.LayoutAddressListBinding
import com.ias.oodles.databinding.LayoutCarouselBinding
import com.ias.oodles.databinding.LayoutCourseListBinding
import com.ias.oodles.databinding.LayoutOrderItemBinding


class OrderItemAdapter() :
        RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            LayoutOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return 3

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }
}