package com.ias.oodles.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentAssignedBinding
import com.ias.oodles.databinding.FragmentChatBinding
import com.ias.oodles.databinding.FragmentEventsBinding
import com.ias.oodles.databinding.FragmentMentorsEventsBinding
import com.ias.oodles.ui.adapter.*

class MentorsEventsFragmentViewModel(binding: ViewDataBinding) : ViewModel() {
    var binding = binding as FragmentMentorsEventsBinding
    var mentorEventListAdapter: MentorsEventsListAdapter = MentorsEventsListAdapter()
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
            mentorEventListAdapter.updateList(iconName)

    }
}