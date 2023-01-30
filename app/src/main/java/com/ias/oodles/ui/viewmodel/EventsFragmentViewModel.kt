package com.ias.oodles.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentAssignedBinding
import com.ias.oodles.databinding.FragmentChatBinding
import com.ias.oodles.databinding.FragmentEventsBinding
import com.ias.oodles.ui.adapter.AssignedListAdapter
import com.ias.oodles.ui.adapter.BooksDownloadListAdapter
import com.ias.oodles.ui.adapter.ChatListAdapter
import com.ias.oodles.ui.adapter.EventsListAdapter

class EventsFragmentViewModel(binding: ViewDataBinding) : ViewModel() {
    var binding = binding as FragmentEventsBinding
    var chatListAdapter: EventsListAdapter = EventsListAdapter()
//    @SuppressLint("StaticFieldLeak")
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun getChatData() {
        var iconName =
            listOf(
                "Essay Writing Session by Manoj K Jha",
                "Essay Writing Session by Manoj K Jha",
                "Essay Writing Session by Manoj K Jha",
                "Essay Writing Session by Manoj K Jha",
                "Essay Writing Session by Manoj K Jha",
                "Essay Writing Session by Manoj K Jha",
            )
        chatListAdapter.updateList(iconName)
    }
}