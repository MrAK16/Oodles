package com.ias.oodles.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.ias.oodles.network.response.address.AddressList
import com.ias.oodles.R
import com.ias.oodles.databinding.ActivityOrderSummaryBinding
import com.ias.oodles.network.ApiInterface
import com.ias.oodles.network.RetrofitHelper
import com.ias.oodles.network.response.address.AddressResponse
import com.ias.oodles.ui.activity.AddAddressActivity
import com.ias.oodles.ui.activity.ConfirmOrderActivity
import com.ias.oodles.ui.adapter.AddressListAdapter
import com.ias.oodles.utils.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderSummaryViewModel(binding: ViewDataBinding) : ViewModel() {
    lateinit var amount: String
    lateinit var gst: String
    var count=""

    val binding = binding as ActivityOrderSummaryBinding
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    var adapter = AddressListAdapter()
    lateinit var loadingDialog: AlertDialog
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val apiService = RetrofitHelper.getInstance().create(ApiInterface::class.java)
    private var addressList : ArrayList<AddressList> = arrayListOf()
    private var selectedAddress:AddressList?=null
    var fromCart:Boolean=false
    fun backPress() {
        (context as Activity).onBackPressed()
    }

    @SuppressLint("SetTextI18n")
    fun addressListApi() {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(context).userId
        request["coupon_code"] = ""
        coroutineScope.launch {
            loadingDialog.show()
            val result = apiService.addressList(request)
            if (result.body() == null) {
                Toast.makeText(
                    context,
                    context.getString(R.string.error_msg),
                    Toast.LENGTH_SHORT
                )
                    .show()
                loadingDialog.dismiss()
                return@launch
            }
            val response: AddressResponse = result.body()!!
            if (response.status) {
                addressList = response.addressList
                if(response.addressList.size==1){
                funDefaultAddressSet(0)
                }else if(response.addressList.size==0){
                    selectedAddress=null
                }
                adapter.update(addressList,context)
            } else {
                Toast.makeText(
                    context,
                    response.error,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            loadingDialog.dismiss()
        }
    }

    fun deleteAddress(id: String) {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(context).userId
        request["id"] = id
        coroutineScope.launch {
            loadingDialog.show()
            val result = apiService.deleteAddress(request)
            if (result.body() == null) {
                Toast.makeText(
                    context,
                    context.getString(R.string.error_msg),
                    Toast.LENGTH_SHORT
                )
                    .show()
                loadingDialog.dismiss()
                return@launch
            }
            val response: AddressResponse = result.body()!!
            if (response.status) {
                addressListApi()
                Toast.makeText(
                    context,
                    response.error,
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    context,
                    response.error,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            loadingDialog.dismiss()
        }
    }

    fun btnContinue(){
        if (selectedAddress==null){
            Toast.makeText(context,"Select Address",Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(context, ConfirmOrderActivity::class.java)
        val gson = Gson()
        intent.putExtra("amount",amount)
        intent.putExtra("gst",gst)
        intent.putExtra("count",count)
        intent.putExtra("address",gson.toJson(selectedAddress))
        intent.putExtra("fromcart",fromCart)
        context.startActivity(intent)
    }

    fun editAddress(id: String) {
        val intent = Intent(context, AddAddressActivity::class.java)
        intent.putExtra("id",id)
        context.startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun funDefaultAddressSet(position: Int) {
        for (i in addressList.indices){
            addressList[i].defaultAddress="0"
        }
        addressList[position].defaultAddress = "1"
        selectedAddress = addressList[position]
        adapter.notifyDataSetChanged()
    }

}