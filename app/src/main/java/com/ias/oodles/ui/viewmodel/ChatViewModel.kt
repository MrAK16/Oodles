package com.ias.oodles.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.ias.oodles.databinding.ActivityChatBinding
import com.ias.oodles.databinding.ActivityPersonalProfileBinding
import com.ias.oodles.databinding.ActivityProfileSettingBinding
import com.ias.oodles.network.response.MaterialResponse

class ChatViewModel(binding: ViewDataBinding) : ViewModel() {
    var binding = binding as ActivityChatBinding
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
}