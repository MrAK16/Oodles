package com.ias.oodles.ui.adapter

import android.annotation.SuppressLint
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
import com.ias.oodles.databinding.LayoutCarouselBinding
import com.ias.oodles.databinding.LayoutCourseListBinding
import com.ias.oodles.databinding.LayoutDeliverItemBinding
import com.ias.oodles.databinding.LayoutDigitalItemBinding
import com.ias.oodles.network.response.cart.CartItems


class CartDigitalItemAdapter() :
        RecyclerView.Adapter<CartDigitalItemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutDigitalItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            LayoutDigitalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return 1

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(postList: List<CartItems>) {
       // list = postList
        notifyDataSetChanged()
    }
}