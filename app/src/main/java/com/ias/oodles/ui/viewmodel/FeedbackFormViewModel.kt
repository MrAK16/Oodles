package com.ias.oodles.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.ias.oodles.R
import com.ias.oodles.databinding.ActivityTakeTestBinding
import com.ias.oodles.ui.activity.TakeTestActivity
import com.ias.oodles.ui.bottomsheet.QuestionPanelBottomSheet
import androidx.fragment.app.FragmentManager
import com.ias.oodles.databinding.ActivityFeedbackFormBinding
import com.ias.oodles.databinding.ActivityTestResultBinding
import com.ias.oodles.utils.SingletonClass

class FeedbackFormViewModel(binding: ViewDataBinding) : ViewModel() {
    var binding = binding as ActivityFeedbackFormBinding

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun clickBack() {
        (context as Activity).finish()
    }

    fun clickQuestionPdf() {
        Toast.makeText(
            context,
            "Question PDF",
            Toast.LENGTH_SHORT
        )
            .show()
    }

}