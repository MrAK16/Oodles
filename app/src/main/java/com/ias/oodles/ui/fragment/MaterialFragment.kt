package com.ias.oodles.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ias.oodles.R
import com.ias.oodles.databinding.FragmentMaterialBinding
import com.ias.oodles.network.ApiInterface
import com.ias.oodles.network.RetrofitHelper
import com.ias.oodles.network.response.freeresource.*
import com.ias.oodles.ui.adapter.FreeResourceTopAdapter
import com.ias.oodles.utils.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MaterialFragment : Fragment(), FreeResourceTopAdapter.ClickListener {
    lateinit var binding: FragmentMaterialBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    var materialTopList = ArrayList<FreeResoureTab>();
    private var materialTopListAdapter: FreeResourceTopAdapter? = null
    private var clickListener: FreeResourceTopAdapter.ClickListener? = null
    lateinit var loadingDialog: AlertDialog
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val apiService = RetrofitHelper.getInstance().create(ApiInterface::class.java)
    var spin_bol = false
    var pos_click = false

    var c: Date? = null
    var df: SimpleDateFormat? = null
    private var datePickerDialog: DatePickerDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_material, container, false)
        initView()
        loadingDialog = RetrofitHelper.loadingDialog(requireActivity())
        //freeClickListener=this
        return binding.root
    }

    private fun initView() {
        clickListener = this
        binding.mmLay.visibility = View.VISIBLE
        c = Calendar.getInstance().time
        df = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
       /* binding.startDate.setText(df!!.format(c))
        binding.endDate.setText(df!!.format(c))
        start=binding.startDate.text.toString()
        end=binding.endDate.text.toString()*/
        if (!Preferences.getInstance(context).frTitle.equals("")) {
            var arr = Preferences.getInstance(context).frTitle.split(";")
            if (arr[1] == "false") {
                freeResource("4")
            } else {
               currentAffair("22")
            }
        }else{
            freeResource("4")
            binding.topLayout.visibility = View.VISIBLE
        }

        binding.filter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    if (!spin_bol) {
                        spin_bol = true
                        return
                    } else {
                        if(Preferences.getInstance(context).frTitle==""){
                            cat_slug=cat_id!![pos]
                            freeResource1(post_type, type, cat_id!![pos])
                        }else{
                            var arr1=Preferences.getInstance(context).frTitle.split(";")
                            if(arr1[1]=="false") {
                                cat_slug=cat_id!![pos]
                                freeResource1(post_type, type, cat_id!![pos])
                            }else{
                                currentAffair1(post_type, type, "","")
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.start.setOnClickListener(View.OnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, year, monthOfYear, dayOfMonth ->
                        binding.startDate.setText( dayOfMonth.toString() + "/" +(monthOfYear + 1).toString() + "/" + year)

                    }, year, month, day
                )
            }

            datePickerDialog!!.show()
        })

        binding.end.setOnClickListener(View.OnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, year, monthOfYear, dayOfMonth ->
                        binding.endDate.setText( dayOfMonth.toString() + "/" +(monthOfYear + 1).toString() + "/" + year)

                    }, year, month, day
                )
            }

            datePickerDialog!!.show()
        })

        binding.apply.setOnClickListener{
            start=binding.startDate.text.toString()
            end=binding.endDate.text.toString()
            currentAffair1(post_type, type, binding.startDate.text.toString(),binding.endDate.text.toString())
        }
    }


    companion object {
        fun addFragment(fragment: Fragment?, requireActivity: FragmentActivity) {
            if (fragment == null) return
            requireActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.rootContainer, fragment)
                .commitAllowingStateLoss()
        }

        var list = ArrayList<FreeResourceList>()
        var cat_id: ArrayList<String>? = null
        var post_type = ""
        var type = ""
        var cat_slug = ""
        var start = ""
        var end = ""


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(position: String) {
        for ((index, value) in materialTopList.withIndex()) {
            materialTopList[index].bool = false;
        }
        pos_click = true
        spin_bol = false
        var arr = position.split(";")
        binding.title.setText(materialTopList[arr[2].toInt()].title)
        materialTopList[arr[2].toInt()].bool = true
        materialTopListAdapter!!.notifyDataSetChanged()
        post_type = arr[1]
        type = arr[0]
        cat_slug=""
        if(Preferences.getInstance(context).frTitle==""){
            freeResource1(arr[1], arr[0], "")
        }else{
            var arr1=Preferences.getInstance(context).frTitle.split(";")
            if(arr1[1]=="false") {
                freeResource1(arr[1], arr[0], "")
            }else{
                currentAffair1(arr[1], arr[0], "","")
            }
        }



    }

    private fun freeResource(post: String) {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(context).userId
        request["post_type"] = post

        coroutineScope.launch {
            //loadingDialog.show()
            val result = apiService.freeResource(request)
            val response: FreeResourceResponse = result.body()!!
            if (response.status) {
                materialTopList = response.free_resources_tabs
                linearLayoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvMaterialTop.layoutManager = linearLayoutManager
                materialTopListAdapter = FreeResourceTopAdapter(clickListener, materialTopList)
                binding.rvMaterialTop.adapter = materialTopListAdapter

                var arr = Preferences.getInstance(context).frTitle.split(";")
                if (Preferences.getInstance(context).frTitle == "") {
                    materialTopList.get(0).bool = true
                    binding.title.setText(materialTopList[0].title)
                    freeResource1(
                        response.free_resources_tabs.get(0).id!!,
                        response.free_resources_tabs.get(0).title!!,
                        ""
                    )
                    post_type = response.free_resources_tabs.get(0).id!!
                    type = response.free_resources_tabs.get(0).title!!
                } else {
                        for (i in materialTopList.indices) {
                            if (arr[0] == materialTopList[i].title) {
                                materialTopList[0].bool = false

                                materialTopList[i].bool = true
                                binding.title.setText(materialTopList[i].title)
                                materialTopListAdapter!!.notifyDataSetChanged()
                                binding.rvMaterialTop.scrollToPosition(i)
                                freeResource1(
                                    response.free_resources_tabs.get(i).id!!,
                                    response.free_resources_tabs.get(i).title!!,
                                    ""
                                )
                                post_type = response.free_resources_tabs.get(i).id!!
                                type = response.free_resources_tabs.get(i).title!!
                                break
                            } else {
                                materialTopList.get(0).bool = true
                            }
                        }

                }

                pos_click = true


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

    private fun currentAffair(post: String) {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(context).userId
        request["post_type"] = post

        coroutineScope.launch {
            //loadingDialog.show()
            val result = apiService.currentAffair(request)
            val response: CurrentAffairResponse = result.body()!!
            if (response.status) {
                materialTopList = response.free_resources_tabs
                linearLayoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvMaterialTop.layoutManager = linearLayoutManager
                materialTopListAdapter = FreeResourceTopAdapter(clickListener, materialTopList)
                binding.rvMaterialTop.adapter = materialTopListAdapter
                var arr = Preferences.getInstance(context).frTitle.split(";")
                if (Preferences.getInstance(context).frTitle == "") {

                    materialTopList.get(0).bool = true
                    binding.title.setText(materialTopList[0].title)
                    freeResource1(
                        response.free_resources_tabs.get(0).id!!,
                        response.free_resources_tabs.get(0).title!!,
                        ""
                    )
                    post_type = response.free_resources_tabs.get(0).id!!
                    type = response.free_resources_tabs.get(0).title!!
                } else {

                            for (i in materialTopList.indices) {
                                if (arr[2] == materialTopList[i].id) {
                                    materialTopList[0].bool = false

                                    materialTopList[i].bool = true
                                    binding.title.setText(materialTopList[i].title)
                                    materialTopListAdapter!!.notifyDataSetChanged()
                                    binding.rvMaterialTop.scrollToPosition(i)
                                    currentAffair1(
                                        response.free_resources_tabs.get(i).id!!,
                                        response.free_resources_tabs.get(i).title!!,
                                        "",""
                                    )
                                    post_type = response.free_resources_tabs.get(i).id!!
                                    type = response.free_resources_tabs.get(i).title!!
                                    break
                                } else {
                                    materialTopList.get(0).bool = true
                                }
                            }

                }

                pos_click = true


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


    private fun freeResource1(post_type: String, type: String, cat_slug: String) {

        if (post_type.equals("20")) {
            addFragment(PrelimsFragment(requireActivity()), requireActivity())
        } else if (post_type.equals("5")) {
            addFragment(BooksDownloadFragment(requireActivity(), type), requireActivity())
        }  else if (post_type.equals("13")) {
            addFragment(StrategyVideoFragment(requireActivity()), requireActivity())
        } else if (post_type.equals("12") || post_type.equals("14") || post_type.equals("10")|| post_type.equals("9")) {
            addFragment(MaterialBlogsFragment(requireActivity()), requireActivity())
        } else if (post_type.equals("2")) {
            addFragment(BooksDownloadFragment(requireActivity(), type), requireActivity())
        } else {
            addFragment(
                ReportMagazinesFragment(requireContext()),
                context as FragmentActivity
            )
        }
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(context).userId
        request["post_type"] = post_type
        request["cat_slug"] = cat_slug


        coroutineScope.launch {
            loadingDialog.show()
            val result = apiService.freeResource(request)
            val response: FreeResourceResponse = result.body()!!
            if (response.status) {

             //   list = response.list
                if (pos_click) {
                    if (response.cat_list.size > 0) {
                        binding.filterLayout.visibility = View.VISIBLE
                        var cat = ArrayList<String>()
                        cat_id = ArrayList<String>()
                        for (i in response.cat_list.indices) {
                            cat_id!!.add(response.cat_list[i].cat_id)
                            cat.add(response.cat_list[i].cat_title)
                        }
                        val timingAdapter = ArrayAdapter<Any?>(
                            requireActivity(), android.R.layout.simple_spinner_dropdown_item,
                            cat as List<Any?>
                        )
                        binding.filter.adapter = timingAdapter
                        pos_click = false
                    } else {
                        binding.filterLayout.visibility = View.GONE
                    }
                }



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

    private fun currentAffair1(post_type: String, type: String, start: String,end:String) {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(context).userId
        request["post_type"] = post_type
        request["from_date"] = start
        request["to_date"] = end


        coroutineScope.launch {
            loadingDialog.show()
            val result = apiService.currentAffair(request)
            val response: CurrentAffairResponse = result.body()!!
            Log.d("*** MaterialFragment >>>", ""+response.cat_list)
            if (response.status) {

               // list = response.list
                if (pos_click) {
                    if (response.cat_list.size > 0) {
                        binding.filterLayout.visibility = View.GONE
                        var cat = ArrayList<String>()
                        cat_id = ArrayList<String>()
                        for (i in response.cat_list.indices) {
                            cat_id!!.add(response.cat_list[i].cat_id)
                            cat.add(response.cat_list[i].cat_title)
                        }
                        val timingAdapter = ArrayAdapter<Any?>(
                            requireActivity(), android.R.layout.simple_spinner_dropdown_item,
                            cat as List<Any?>
                        )
                        binding.filter.adapter = timingAdapter
                        pos_click = false
                    } else {
                        binding.filterLayout.visibility = View.GONE
                    }
                }

                if ( post_type.equals("9") || post_type.equals("21") || post_type.equals("19") || post_type.equals(
                        "17")|| type.equals("18")) {
                    addFragment(MaterialBlogsFragment(requireActivity()), requireActivity())
                    binding.dateLayout.visibility=View.GONE
                }else if(post_type.equals("15") ){
                    addFragment(MaterialBlogsFragment(requireActivity()), requireActivity())
                    binding.dateLayout.visibility=View.VISIBLE
                } else {
                    addFragment(
                        ReportMagazinesFragment(requireContext()),
                        context as FragmentActivity
                    )
                    binding.dateLayout.visibility=View.GONE
                }

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


}