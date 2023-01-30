package com.ias.oodles.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentAssignedBinding
import com.ias.oodles.ui.viewmodel.AssignedFragmentViewModel
import com.ias.oodles.utils.SingletonClass
import com.ias.oodles.viewmodelfactory.ActivityViewModelFactory

class AssignedFragment() : Fragment() {
    lateinit var binding: FragmentAssignedBinding
    lateinit var viewModel: AssignedFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_assigned, container, false)
        viewModel = ViewModelProvider(this, ActivityViewModelFactory(binding,SingletonClass.instance))[AssignedFragmentViewModel::class.java]
        binding.viewModel = viewModel
        initialData()
        return binding.root
    }

    private fun initialData() {
        viewModel.context = requireContext()
        viewModel.binding = binding
        viewModel.getChatData()
    }
}