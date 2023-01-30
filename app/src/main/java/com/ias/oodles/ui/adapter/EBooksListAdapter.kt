package com.ias.oodles.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ias.oodles.databinding.RowEbooksBinding
import com.ias.oodles.databinding.RowMainTestsBinding
import com.ias.oodles.databinding.RowMaterialBinding
import com.ias.oodles.databinding.RowPtTestBinding
import com.ias.oodles.network.response.MaterialResponse
import com.ias.oodles.network.response.myaccount.EbookList
import com.ias.oodles.ui.activity.MaterialReportDetailsActivity


class EBooksListAdapter(
    context: Context,
    private var materialTopList: ArrayList<EbookList>
) :
        RecyclerView.Adapter<EBooksListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowEbooksBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            RowEbooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
           return materialTopList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = materialTopList[position].ebookTitle
      /*  holder.binding.mainLayout.setOnClickListener {
            val intent = Intent(context, MaterialReportDetailsActivity::class.java)
            context.startActivity(intent)
        }*/


    }
}