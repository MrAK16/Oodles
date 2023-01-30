package com.ias.oodles.network.response.studynotes

import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class ProductListResponse (
  @SerializedName("product_list"  ) var productList  : ArrayList<ProductList>  = arrayListOf(),
  @SerializedName("category_list" ) var categoryList : ArrayList<CategoryList> = arrayListOf()

):OodlesApiResponse()