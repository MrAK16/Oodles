package com.ias.oodles.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.ias.oodles.R
import com.ias.oodles.ui.adapter.OrderItemAdapter
import com.ias.oodles.ui.adapter.OrderListAdapter

class OrderDetailsActivity : AppCompatActivity() {

    @BindView(R.id.rv_order_items)
    lateinit var rv_order_items: RecyclerView
    private var orderItemAdapter: OrderItemAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        ButterKnife.bind(this)

        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_order_items.layoutManager = linearLayoutManager
        orderItemAdapter = OrderItemAdapter()
        rv_order_items.adapter = orderItemAdapter


    }


}