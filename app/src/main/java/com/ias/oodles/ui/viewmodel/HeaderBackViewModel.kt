package com.ias.oodles.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.ias.oodles.databinding.ActivityPersonalProfileBinding
import com.ias.oodles.databinding.ActivityProfileSettingBinding
import com.ias.oodles.databinding.HeaderBackBindingImpl

class HeaderBackViewModel(binding: ViewDataBinding) : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun clickBackPress(){
        (context as Activity).onBackPressed()
    }
}