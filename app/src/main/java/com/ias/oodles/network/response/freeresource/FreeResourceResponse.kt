package com.ias.oodles.network.response.freeresource

import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class FreeResourceResponse (

  @SerializedName("free_resources_tabs" ) var free_resources_tabs : ArrayList<FreeResoureTab> = arrayListOf(),
  @SerializedName("list" ) var list  : ArrayList<FreeResourceList> = arrayListOf(),
  @SerializedName("cat_list" ) var cat_list  : ArrayList<CatList> = arrayListOf()

):OodlesApiResponse()