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
import com.ias.oodles.databinding.RowFreeResourceBinding
import com.ias.oodles.databinding.RowMaterialBinding
import com.ias.oodles.databinding.RowTopMaterialBinding
import com.ias.oodles.network.response.freeresource.FreeResourceList
import com.ias.oodles.network.response.myaccount.PackageHasResponse
import com.ias.oodles.ui.activity.MaterialReportDetailsActivity
import java.util.*
import kotlin.collections.ArrayList


class FreeResourceListAdapter(var context: Context,
    materialTopList1: List<FreeResourceList>
) :
        RecyclerView.Adapter<FreeResourceListAdapter.ViewHolder>() {
    private var materialTopList = materialTopList1;

    inner class ViewHolder(val binding: RowFreeResourceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            RowFreeResourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return materialTopList.size

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text=materialTopList[position].title
        Glide.with(context).load(materialTopList[position].image_url).into(holder.binding.img)
        holder.binding.mainLayout.setOnClickListener{
            var intent = Intent(context,MaterialReportDetailsActivity::class.java)
            intent.putExtra("post_id",materialTopList[position].id)
            intent.putExtra("type_id",materialTopList[position].type)
            context.startActivity(intent)

        }

    }


}