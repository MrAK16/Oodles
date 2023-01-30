package com.ias.oodles.network.response.freeresource

import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class FreeResoureTab (

  @SerializedName("id" ) var id      : String? = null,
  @SerializedName("title" ) var title      : String? = null,
  @SerializedName("icon" ) var icon      : String? = null,
  var bool:Boolean
)