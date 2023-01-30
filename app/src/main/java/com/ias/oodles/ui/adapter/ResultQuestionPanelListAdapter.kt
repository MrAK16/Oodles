package com.ias.oodles.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.ias.oodles.R
import com.ias.oodles.databinding.RowQuestionPanelListBinding
import com.ias.oodles.network.request.QuestionRequest
import com.ias.oodles.network.response.myaccount.StartTestResponse
import com.ias.oodles.network.response.testresult.Questions
import com.ias.oodles.network.response.testresult.Report
import com.ias.oodles.ui.activity.TakeTestActivity
import com.ias.oodles.ui.activity.TestResultActivity
import com.ias.oodles.ui.fragment.ResultDetailedAnalysisFragment
import com.ias.oodles.ui.fragment.TestQuestionFragment
import com.ias.oodles.ui.viewmodel.TestResultViewModel


class ResultQuestionPanelListAdapter(
    private val context: Context,
    private val contextActivity: FragmentActivity,
    private val questionList: ArrayList<Questions>,
    private val reportData: Report
) :
    RecyclerView.Adapter<ResultQuestionPanelListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowQuestionPanelListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            RowQuestionPanelListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionData = questionList[position]
        holder.binding.apply {
           tvQuestionNo.text = "" + (position + 1)
           layoutMain.setOnClickListener {
               TestResultActivity.getInstance().viewModel.addFragment(
                    ResultDetailedAnalysisFragment(
                        questionList[position],
                        reportData,
                        position,
                        questionList.size
                    ), contextActivity,position,"questionPanel"
                )
            }

        /*    var colorCode = "#EFEFEF"
            when (questionData.attemptType) {
                "answered" -> {
                    colorCode = "#4CAF50"
                    holder.binding.tvQuestionNo.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
                "markForReview" -> {
                    colorCode = "#005DA8"
                    holder.binding.tvQuestionNo.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
                "notAnswered" ->{
                    colorCode = "#E43B4E"
                    holder.binding.tvQuestionNo.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
                else -> {
                    holder.binding.tvQuestionNo.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.black
                        )
                    )
                }
            }
            holder.binding.layoutMain.background.setColorFilter(
                Color.parseColor(colorCode), PorterDuff.Mode.SRC_ATOP
            )*/
        }
    }
}