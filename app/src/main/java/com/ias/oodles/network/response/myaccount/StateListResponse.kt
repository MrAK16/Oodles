package com.ias.oodles.network.response.myaccount

import com.example.example.PackageProgramDetails
import com.example.example.VideoList
import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class StateListResponse (
  @SerializedName("state_list" ) var state_list : ArrayList<StateList> = arrayListOf()



):OodlesApiResponse()