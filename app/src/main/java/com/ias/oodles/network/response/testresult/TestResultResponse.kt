package com.ias.oodles.network.response.testresult

import com.example.example.VideoList
import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse
import com.ias.oodles.network.response.myaccount.QuestionData
import com.ias.oodles.network.response.myaccount.RankList
import com.ias.oodles.network.response.myaccount.TopperData
import com.ias.oodles.network.response.myaccount.VideoDetails


data class TestResultResponse (
  @SerializedName("list") var rankList   : ArrayList<RankList> = arrayListOf(),
  @SerializedName("report" ) var report : Report?  = Report(),
  //Main Test Rank List
  @SerializedName("toppers_list") var toppersList   : ArrayList<TopperData> = arrayListOf(),
  @SerializedName("question_list") var questionList   : ArrayList<QuestionData> = arrayListOf(),
  //Main Test Video Details
  @SerializedName("video_details") var videoDetails: ArrayList<QuestionData> = arrayListOf(),
  @SerializedName("related_videos") var relatedVideo: ArrayList<QuestionData> = arrayListOf()
):OodlesApiResponse()