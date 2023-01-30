package com.ias.oodles.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ias.oodles.R
import com.ias.oodles.databinding.LayoutCarouselBinding
import com.ias.oodles.databinding.LayoutCourseListBinding
import com.ias.oodles.databinding.LayoutDeliverItemBinding
import com.ias.oodles.network.response.cart.CartItems
import com.ias.oodles.network.response.cart.CartList


class CartDeliveryItemAdapter() :
        RecyclerView.Adapter<CartDeliveryItemAdapter.ViewHolder>() {
    val list: MutableList<CartList> = mutableListOf()
    lateinit var removeCartItemInterface:RemoveCartItemInterface
    inner class ViewHolder(val binding: LayoutDeliverItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            LayoutDeliverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return list.size

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = list!![position].title
            tvAmount.text = "₹ "+ list!![position].payableAmount
            tvDeliverBy.text = "Delivery By : "
         /*  packageImage.load(list!![position].packageImg) {
                fallback(R.drawable.book1)
                error(R.drawable.book1)
                placeholder(R.drawable.book1)
            }*/
            removeCartItem.setOnClickListener {
                removeCartItemInterface.removeCartItem(list!![position].id!!,position)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(postList: List<CartList>, removeCartItemInterface: RemoveCartItemInterface) {
        this.removeCartItemInterface = removeCartItemInterface
        list.clear()
        list.addAll(postList)
        notifyDataSetChanged()
    }

    fun removeAndUpdateList(position:Int){
        list.remove(list[position])
        notifyDataSetChanged()
    }

    interface RemoveCartItemInterface{
        fun removeCartItem(productId: String,position:Int)
    }
}