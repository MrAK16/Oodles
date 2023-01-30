package com.ias.oodles.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.ias.oodles.R
import com.ias.oodles.databinding.ActivityConfirmOrderBinding
import com.ias.oodles.network.RetrofitHelper
import com.ias.oodles.network.response.address.AddressList
import com.ias.oodles.ui.viewmodel.ConfirmOrderViewModel
import com.ias.oodles.ui.viewmodel.OrderSummaryViewModel
import com.ias.oodles.viewmodelfactory.ActivityViewModelFactory


class ConfirmOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfirmOrderBinding
    lateinit var amount: String
    lateinit var gst: String
    var fromcart: Boolean? = null

    @SuppressLint("SetTextI18n")
//    var merchantId = "7137591"
//    private var merchantkey  = "hZVc2mV1"
//    var merchantSalt = "ClzwWKoB2q"
    var merchantId = "5492541"
    private var merchantkey = "kTJpmHeo"
    var merchantSalt = "W4fWQdToSa"
    var payuresponse = ""
    lateinit var viewModel: ConfirmOrderViewModel
    private var selectedAddress: AddressList = AddressList()
    var count=""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_order)
        viewModel = ViewModelProvider(
            this,
            ActivityViewModelFactory(binding, application)
        )[ConfirmOrderViewModel::class.java]
        binding.viewmodel = viewModel
        viewModel.context = this
        val gson = Gson()
        amount = intent.getStringExtra("amount").toString()
        viewModel.amount=amount
        gst = intent.getStringExtra("gst").toString()
        count = intent.getStringExtra("count").toString()
        fromcart = intent.getBooleanExtra("fromcart", false);
        viewModel.fromCart = fromcart!!
        viewModel.loadingDialog = RetrofitHelper.loadingDialog(this)
        selectedAddress = gson.fromJson(intent.getStringExtra("address"), AddressList::class.java)
        viewModel.selectedAddress = selectedAddress
        binding.totItems.setText("Price Details (${count} Items)")
        binding.tvTotalMrp.text = "₹$amount"
        if (!fromcart!!) {
            if (gst.toFloat() > 0) {
                binding.gst.text = "GST(${gst})%"
                binding.gstAmount.text = "₹" + ((amount.toFloat() * gst.toFloat()) / 100).toString()
                binding.tvTotalAmount.text =
                    "₹" + (amount.toFloat() + ((amount.toFloat() * gst.toFloat()) / 100))
                binding.tvFinalAmount.text =
                    "₹" + (amount.toFloat() + ((amount.toFloat() * gst.toFloat()) / 100))
            } else {
                binding.gst.text = "gst included"
                binding.gstAmount.text = "₹0"
                binding.tvTotalAmount.text =
                    "₹" + (amount.toFloat() )
                binding.tvFinalAmount.text =
                    "₹" + (amount.toFloat())
            }
        } else {
            binding.gst.text = "gst included"
            binding.gstAmount.text = "₹0"
            binding.tvTotalAmount.text =
                "₹" + (amount.toFloat() )
            binding.tvFinalAmount.text =
                "₹" + (amount.toFloat())
        }


        binding.headerTitle.text = "Confirm Order"
        binding.tvName.text = selectedAddress.fullname
        binding.tvAddress.text = selectedAddress.address
        binding.tvCity.text = selectedAddress.city
        binding.tvState.text = "${selectedAddress.stateName} , ${selectedAddress.zipCode}"
        binding.tvMobile.text = "Mobile: ${selectedAddress.mobile}"
        binding.btBackPress.setOnClickListener {
            finish()
        }

    }


}