package com.ias.oodles.network.response.confirmorder

import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class TransactionResponse (
  var status: Boolean = false,
  var error: String = "Something went wrong",

 )