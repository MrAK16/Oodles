package com.ias.oodles.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.ias.oodles.databinding.RowBooksDownloadBinding
import com.ias.oodles.downloadfileswithworkmanager.CHANNEL_DESC
import com.ias.oodles.downloadfileswithworkmanager.NOTIFICATION_ID
import com.ias.oodles.network.response.MaterialResponse
import com.ias.oodles.network.response.freeresource.FreeResourceList
import com.ias.oodles.ui.activity.MaterialReportDetailsActivity
import com.ias.oodles.ui.fragment.CourseDetailsFragment
import com.ias.oodles.utils.Helpers


class BooksDownloadListAdapter(
    context1: Context,
    materialTopList1: List<FreeResourceList>,private val downloadClick:DownloadClick
) :
        RecyclerView.Adapter<BooksDownloadListAdapter.ViewHolder>() {
    private var materialTopList = materialTopList1;
    private var context= context1;
    inner class ViewHolder(val binding: RowBooksDownloadBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            RowBooksDownloadBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return materialTopList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text=materialTopList[position].title
        if(materialTopList[position].file_url!!.equals("")){
            holder.binding.download.visibility= View.GONE
        }else{
            holder.binding.download.visibility= View.VISIBLE
        }
        holder.binding.download.setOnClickListener{
            downloadClick.onDownloadClick(materialTopList[position].file_url!!,materialTopList[position].title!!)
        }

//        holder.binding.mainLayout.setOnClickListener {
//            context.startActivity(Intent(context, MaterialBlogsDetailsActivity::class.java))
//        }


    }

    interface DownloadClick{
        fun onDownloadClick(position:String,title:String)
    }


}