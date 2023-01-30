package com.ias.oodles.network.response.courses

import com.google.gson.annotations.SerializedName


data class CourseInfos (

  @SerializedName("title"       ) var title       : String? = null,
  @SerializedName("description" ) var description : String? = null

)