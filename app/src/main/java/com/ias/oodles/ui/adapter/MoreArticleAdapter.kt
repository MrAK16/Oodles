package com.ias.oodles.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ias.oodles.R
import com.ias.oodles.databinding.RowMoreArticleBinding
import com.ias.oodles.databinding.RowStrategyVideoBinding
import com.ias.oodles.network.response.freeresource.FreeResourceList
import com.ias.oodles.network.response.freeresource.FreeResoureTab
import com.ias.oodles.ui.activity.MaterialBlogsDetailsActivity
import com.ias.oodles.ui.activity.StrategyVideoDetails
import com.ias.oodles.utils.Helpers
import java.util.*
import kotlin.collections.ArrayList


class MoreArticleAdapter(
    context1: Context,
    materialTopList1: ArrayList<FreeResourceList>
) :
        RecyclerView.Adapter<MoreArticleAdapter.ViewHolder>() {
    private var materialTopList = materialTopList1;
    private var context=context1


    inner class ViewHolder(val binding: RowMoreArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            RowMoreArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return materialTopList.size

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text= materialTopList[position].title!!
        Helpers.setWebViewText(holder.binding.desc,materialTopList[position].description!!)
    }
}