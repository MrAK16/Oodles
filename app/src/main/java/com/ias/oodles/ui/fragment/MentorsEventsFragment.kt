package com.ias.oodles.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentMentorsEventsBinding
import com.ias.oodles.ui.viewmodel.MentorsEventsFragmentViewModel
import com.ias.oodles.utils.SingletonClass
import com.ias.oodles.viewmodelfactory.ActivityViewModelFactory

class MentorsEventsFragment() : Fragment() {
    lateinit var binding: FragmentMentorsEventsBinding
    lateinit var viewModel: MentorsEventsFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mentors_events, container, false)
        viewModel = ViewModelProvider(this, ActivityViewModelFactory(binding,SingletonClass.instance))[MentorsEventsFragmentViewModel::class.java]
        binding.viewModel = viewModel
        initialData()
        return binding.root
    }

    private fun initialData() {
        viewModel.context = requireContext()
        viewModel.binding = binding
        binding.tvName.text = "Active Events (2)"
        viewModel.getChatData()
    }
}