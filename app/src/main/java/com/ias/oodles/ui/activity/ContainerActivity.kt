package com.ias.oodles.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import butterknife.ButterKnife
import com.ias.oodles.R
import com.ias.oodles.ui.fragment.CourseTestFragment
import com.ias.oodles.ui.fragment.CourseDetailsFragment
import com.ias.oodles.utils.SingletonClass

class ContainerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        ButterKnife.bind(this)
        hideFooter(true)
        if (intent.getStringExtra("whereFrom")=="viewAllBatches"){
            addFragment(
                CourseTestFragment(this,"Upcoming Batches",false,""),this)
            setToolBar("All Batches")
            SingletonClass.instance.setContainerManager(supportFragmentManager)
        }else{
            setToolBar("IAS Foundation 2023")
            addFragment(
                CourseDetailsFragment(),this)
        }

    }

    companion object{
        fun addFragment(fragment: Fragment?, requireActivity: FragmentActivity) {
            if (fragment == null) return
            requireActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.rootContainer, fragment)
                .commitAllowingStateLoss()
        }
    }

}