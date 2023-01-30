package com.ias.oodles.network.response.cart

import com.google.gson.annotations.SerializedName
import com.ias.oodles.network.response.OodlesApiResponse


data class CartItemResponse (
  @SerializedName("cart_items" ) var cartItems : CartItems?        = CartItems(),
  @SerializedName("total_items") var totalItems:Int = 0
  ):OodlesApiResponse()