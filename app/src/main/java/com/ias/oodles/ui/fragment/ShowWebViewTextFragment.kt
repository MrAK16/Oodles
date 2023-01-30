package com.ias.oodles.ui.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentShowWebviewTextBinding
import com.ias.oodles.utils.Helpers

class ShowWebViewTextFragment(context: Context, private val description: String) : Fragment() {
    lateinit var binding: FragmentShowWebviewTextBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_webview_text, container, false)
       // val view: View = inflater.inflate(R.layout.fragment_pt_test, container, false)
        initialData()
        return binding.root;

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initialData() {
        if (description == ""){
            binding.mainLayout.visibility = View.GONE
            binding.tvNoDataFound.visibility = View.VISIBLE
        }else{
            binding.mainLayout.visibility = View.VISIBLE
            binding.tvNoDataFound.visibility = View.GONE
            Helpers.setWebViewText(binding.tvTitle,description)
        }
    }

}
