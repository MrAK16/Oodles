package com.ias.oodles.network.response.myaccount

import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class StartTestResponse (
  @SerializedName("test_details" ) var testDetails : ArrayList<TestDetails> = arrayListOf(),
  @SerializedName("ResultId"     ) var resultId    : String?                = null,
  @SerializedName("Questions"    ) var questionsList   : ArrayList<Questions>   = arrayListOf()

):OodlesApiResponse()