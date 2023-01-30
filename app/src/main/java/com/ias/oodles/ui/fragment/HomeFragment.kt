package com.ias.oodles.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentHomeBinding
import com.ias.oodles.network.RetrofitHelper
import com.ias.oodles.ui.activity.WebViewActivity
import com.ias.oodles.ui.bottomsheet.NoInternetBottomSheetFragment
import com.ias.oodles.ui.viewmodel.HomeFragmentViewModel
import com.ias.oodles.utils.Helpers
import com.ias.oodles.utils.Preferences
import com.ias.oodles.utils.SingletonClass
import com.ias.oodles.viewmodelfactory.ActivityViewModelFactory

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeFragmentViewModel
    var bottomSheet= NoInternetBottomSheetFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        //Clear View Model
        viewModelStore.clear()
        viewModel = ViewModelProvider(this,ActivityViewModelFactory(binding, SingletonClass.instance))[HomeFragmentViewModel::class.java]
        binding.viewmodel = viewModel
        viewModel.context = requireActivity()
        viewModel.loadingDialog = RetrofitHelper.loadingDialog(requireActivity())
        viewModel.sliderPagerSet()
        if(Helpers.isNetworkAvailable(requireActivity())) {
            viewModel.fcm()
            viewModel.homeApi()
            viewModel.deliveryItemListApiCall()
        }else{
            bottomSheet!!.show(requireActivity().supportFragmentManager, "ModalBottomSheet")
        }
        Preferences.getInstance(context).frTitle=""

        binding.interviews.setOnClickListener {
            startActivity(Intent(requireActivity(), WebViewActivity::class.java).putExtra("url", "https://iasscore.in/toppers-interview"))
        }

        binding.copies.setOnClickListener {
            startActivity(Intent(requireActivity(), WebViewActivity::class.java).putExtra("url", "https://iasscore.in/toppers-copy"))
        }

        binding.results.setOnClickListener {
            startActivity(Intent(requireActivity(), WebViewActivity::class.java).putExtra("url", "https://iasscore.in/our-results"))
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}