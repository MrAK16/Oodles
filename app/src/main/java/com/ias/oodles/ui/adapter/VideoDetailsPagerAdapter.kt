package com.ias.oodles.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ias.oodles.network.response.courses.CourseInfos
import com.ias.oodles.network.response.testresult.TestResultResponse
import com.ias.oodles.ui.fragment.*

class VideoDetailsPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle?, val context: Context,
    private val whereFrom: String,
    private val numTab: Int,
    private val courseInfoList: ArrayList<CourseInfos>?,
    private val response: TestResultResponse?
) :
    FragmentStateAdapter(fm, lifecycle!!) {
    override fun createFragment(i: Int): Fragment {
        return when (whereFrom) {
            "videoDetailsNew" -> {
                ShowWebViewTextFragment(context, courseInfoList!![i].description!!)
            }
            "mainVideoDetails" -> {
                when (i) {
                    0 -> {
                        var desc = ""
                        if (response!!.videoDetails.size > 0)
                            desc = response!!.videoDetails[0].solution.toString()
                        ShowWebViewTextFragment(context, desc)
                    }
                    1 -> {
                        MainTestVideoListFragment(context, response!!.relatedVideo)
                    }
                    else -> {
                        ShowWebViewTextFragment(context, "")
                    }
                }

            }
            else -> {
                when (i) {
                    0 -> {
                        CourseTestFragment(context, "Other", true, "")
                    }
                    1 -> {
                        CourseTestFragment(context, "Other"/*"pt-test"*/, true, "")
                    }
                    else -> {
                        CourseTestFragment(context, "Other"/*"e-books"*/, true, "")
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return numTab
    }
}