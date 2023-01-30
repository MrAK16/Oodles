package com.ias.oodles.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ias.oodles.DownloadBrochureInterface
import com.ias.oodles.R
import com.ias.oodles.databinding.RowMainTestsBinding
import com.ias.oodles.network.response.myaccount.MainsTest
import com.ias.oodles.ui.activity.MainTestRankDetailsActivity
import com.ias.oodles.ui.activity.MainTestVideoDetailActivity
import com.ias.oodles.ui.activity.MaterialReportDetailsActivity
import com.ias.oodles.utils.Helpers


class MainTestListAdapter(
    val context: Context,
    val list: ArrayList<MainsTest>,
    private val downloadBrochureInterface:DownloadBrochureInterface
) :
    RecyclerView.Adapter<MainTestListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowMainTestsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            RowMainTestsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = list[position].title
            iconPdf.background =
                ContextCompat.getDrawable(context, R.drawable.ic_rectangle_green)
            ivPdfVideo.visibility = View.VISIBLE
            btQuestionPaper.setOnClickListener {
               /* val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("title", list[position].title)
                intent.putExtra("url", list[position].questionPdfUrl)
                context.startActivity(intent)*/
               // Helpers.downloadPdf(context, list[position].questionPdfUrl, list[position].title)
                downloadBrochureInterface.downLoadFile(list[position].questionPdfUrl!!,list[position].title!!,position)
            }
            downloadHint.setOnClickListener {
              /*  val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("title", list[position].title)
                intent.putExtra("url", list[position].answerPdfUrl)
                context.startActivity(intent)*/
              //  Helpers.downloadPdf(context, list[position].answerPdfUrl,  list[position].title)
                downloadBrochureInterface.downLoadFile(list[position].answerPdfUrl!!,  list[position].title!!,position)
            }
            ivMore.setOnClickListener {
                val intent = Intent(context, MainTestRankDetailsActivity::class.java)
                intent.putExtra("title", list[position].title)
                intent.putExtra("testId", list[position].testId)
                intent.putExtra("hasDiscussionVideo", list[position].hasDiscussionVideo)
                intent.putExtra("breakup", list[position].breakup)
                intent.putExtra("whereFrom", "TopperRank")
                context.startActivity(intent)
            }
            iconPdf.setOnClickListener {
                val intent = Intent(context, MainTestRankDetailsActivity::class.java)
                intent.putExtra("title", list[position].title)
                intent.putExtra("testId", list[position].testId)
                intent.putExtra("hasDiscussionVideo", list[position].hasDiscussionVideo)
                intent.putExtra("breakup", list[position].breakup)
                intent.putExtra("whereFrom", "pdfIcon")
                context.startActivity(intent)
            }
            uploadAnswer.setOnClickListener {
                Toast.makeText(
                    context,
                    "Coming soon",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    interface UploadAnswerInterface{
        fun uploadAnswer(data:MainsTest)
    }
}