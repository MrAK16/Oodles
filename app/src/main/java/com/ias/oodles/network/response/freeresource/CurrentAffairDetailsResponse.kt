package com.ias.oodles.network.response.freeresource

import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class CurrentAffairDetailsResponse (

  @SerializedName("details" ) var details : CurrentDetails? = CurrentDetails(),



):OodlesApiResponse()