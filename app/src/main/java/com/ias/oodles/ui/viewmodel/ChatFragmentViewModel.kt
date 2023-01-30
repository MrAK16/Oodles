package com.ias.oodles.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentChatBinding
import com.ias.oodles.ui.adapter.BooksDownloadListAdapter
import com.ias.oodles.ui.adapter.ChatListAdapter

class ChatFragmentViewModel(binding: ViewDataBinding) : ViewModel() {
    var binding = binding as FragmentChatBinding
    var chatListAdapter: ChatListAdapter = ChatListAdapter()
//    @SuppressLint("StaticFieldLeak")
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun getChatData() {
        var iconName =
            listOf(
                "Piyush Bokolia",
                "Abhishek verma",
                "Anurag Basu",
                "Siddharth Kuradia",
                "Piyush Bokolia",
                "Abhishek verma",
                "Anurag Basu",
                "Siddharth Kuradia",
                "Piyush Bokolia",
                "Abhishek verma",
                "Anurag Basu",
                "Siddharth Kuradia",
                "Piyush Bokolia",
                "Abhishek verma",
                "Anurag Basu",
                "Siddharth Kuradia",
                "Piyush Bokolia"

            )
        chatListAdapter.updateList(iconName)
    }
}