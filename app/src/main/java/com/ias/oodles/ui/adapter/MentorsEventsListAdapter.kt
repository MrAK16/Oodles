package com.ias.oodles.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ias.oodles.R
import com.ias.oodles.databinding.RowAssignedListBinding
import com.ias.oodles.databinding.RowChatListBinding
import com.ias.oodles.databinding.RowEventsListBinding
import com.ias.oodles.databinding.RowMentorsEventsListBinding

class MentorsEventsListAdapter : RecyclerView.Adapter<MentorsEventsListAdapter.ViewHolder>() {
    lateinit var iconName: List<String>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_mentors_events_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding!!.apply {
            tvName.text = iconName[position]
           // ivFunctionalityIcon.setImageResource(iconList[position])
        }
    }

    override fun getItemCount(): Int {
        return iconName.size
    }

    fun updateList(iconName: List<String>) {
        this.iconName = iconName
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemBinding: RowMentorsEventsListBinding? = DataBindingUtil.bind(itemView.rootView)
    }
}