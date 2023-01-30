package com.ias.oodles.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ias.oodles.databinding.*
import com.ias.oodles.network.notification.NotificationList
import com.ias.oodles.network.response.MaterialResponse
import com.ias.oodles.network.response.myaccount.EbookList
import com.ias.oodles.ui.activity.MaterialReportDetailsActivity


class NotificationAdapter(
    context: Context,
    private var materialTopList: List<NotificationList>
) :
        RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutNotificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            LayoutNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return materialTopList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(materialTopList[position].title==""){
            holder.binding.icNoti.visibility= View.GONE
            holder.binding.noti.text =""
        }else{
            holder.binding.noti.text = materialTopList[position].title
            holder.binding.icNoti.visibility= View.VISIBLE
        }

        holder.binding.date.text = materialTopList[position].date
        holder.binding.desc.loadDataWithBaseURL("",materialTopList[position].message!!,"text/html", "UTF-8","")
      /*  holder.binding.mainLayout.setOnClickListener {
            val intent = Intent(context, MaterialReportDetailsActivity::class.java)
            context.startActivity(intent)
        }*/


    }
}