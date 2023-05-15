package com.ias.gsscore.ui.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ias.gsscore.R
import com.ias.gsscore.databinding.ActivityMainBinding
import com.ias.gsscore.ui.viewmodel.MainViewModel
import com.ias.gsscore.utils.Preferences
import com.ias.gsscore.utils.SingletonClass
import com.ias.gsscore.viewmodelfactory.ActivityViewModelFactory

class MainActivity : AppCompatActivity(), Listener {
    lateinit var binding: ActivityMainBinding
    companion object {
        lateinit var viewModel: MainViewModel
    }
    var fromEdit=false
    var pos=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(
            this,
            ActivityViewModelFactory(binding, application)
        )[MainViewModel::class.java]
        binding.viewmodel = viewModel
        viewModel.context = this
        fromEdit=intent.getBooleanExtra("fromEdit",false)
        pos= intent.getStringExtra("pos").toString()
        SingletonClass.instance.setCart(binding.cartCount,binding.countLayout)
     //  Preferences.getInstance(this).userId = "170885"
        val navController = findNavController(R.id.myNavHostFragment)
        SingletonClass.instance.setCustomNavController(navController)
        SingletonClass.instance.setSupportManager(supportFragmentManager)

        if(fromEdit){
            if(pos=="1"){
                Preferences.getInstance(applicationContext).frTitle=""
                MainViewModel.setHeaderTitle(0, "")
                viewModel.isSelected.set(1)
                val bundle = bundleOf("id" to "")
                navController.navigate(R.id.courseFragment,bundle)
            }else if(pos=="2"){
                Preferences.getInstance(applicationContext).frTitle=""
                MainViewModel.setHeaderTitle(0, "")
                viewModel.isSelected.set(2)
                navController.navigate(R.id.studyNotesFragment)
            }else if(pos=="3"){
                Preferences.getInstance(applicationContext).frTitle=""
                MainViewModel.setHeaderTitle(0, "")
                viewModel.isSelected.set(0)
                navController.navigate(R.id.homeFragment)
            } else if(pos=="4"){
                Preferences.getInstance(applicationContext).frTitle=""
                MainViewModel.setHeaderTitle(0, "")
                viewModel.isSelected.set(3)
                navController.navigate(R.id.materialFragment)
            }else if(pos=="5"){
                Preferences.getInstance(applicationContext).frTitle=""
                MainViewModel.setHeaderTitle(0, "")
                viewModel.isSelected.set(4)
                navController.navigate(R.id.myAccountFragment)
            }
        }

        binding.homeFragment.setOnClickListener {
            binding.btCart.visibility = View.GONE
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(0)
            navController.navigate(R.id.homeFragment)
        }
        binding.courseFragment.setOnClickListener {
            binding.btCart.visibility = View.GONE
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(1)
            val bundle = bundleOf("id" to "")
            navController.navigate(R.id.courseFragment,bundle)
        }

        binding.studyNotesFragment.setOnClickListener {
            binding.btCart.visibility = View.VISIBLE
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(2)
            navController.navigate(R.id.studyNotesFragment)
        }
        binding.materialFragment.setOnClickListener {
            binding.btCart.visibility = View.GONE
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(3)
            navController.navigate(R.id.materialFragment)
        }

        binding.myAccountFragment.setOnClickListener {
            binding.btCart.visibility = View.GONE
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(4)
            navController.navigate(R.id.myAccountFragment)
        }

        binding.myCourse.setOnClickListener{
            binding.btCart.visibility = View.GONE
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(4)
            navController.navigate(R.id.myAccountFragment)
            binding.studyNotes.setBackgroundResource(0)
            binding.myCourse.setBackgroundResource(R.drawable.nav_selec_bg)
            binding.resourse.setBackgroundResource(0)
            binding.currentAffair.setBackgroundResource(0)
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.tvPlans.setOnClickListener{
            binding.studyNotes.setBackgroundResource(R.drawable.nav_selec_bg)
            binding.myCourse.setBackgroundResource(0)
            binding.resourse.setBackgroundResource(0)
            binding.currentAffair.setBackgroundResource(0)
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(2)
            navController.navigate(R.id.studyNotesFragment)
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.tvHistory.setOnClickListener{
            binding.studyNotes.setBackgroundResource(0)
            binding.myCourse.setBackgroundResource(0)
            binding.resourse.setBackgroundResource(R.drawable.nav_selec_bg)
            binding.currentAffair.setBackgroundResource(0)
            Preferences.getInstance(applicationContext).frTitle=""
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(3)
            navController.navigate(R.id.materialFragment)
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.tvSupport.setOnClickListener{
            binding.studyNotes.setBackgroundResource(0)
            binding.myCourse.setBackgroundResource(0)
            binding.resourse.setBackgroundResource(0)
            binding.currentAffair.setBackgroundResource(R.drawable.nav_selec_bg)
            Preferences.getInstance(this).frTitle="PIB Compilation"+";"+"true"+";"+"22"
            MainViewModel.setHeaderTitle(0, "")
            viewModel.isSelected.set(3)
            navController.navigate(R.id.materialFragment)
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.tvNotification.setOnClickListener{
            startActivity(Intent(this,NotificationActivity::class.java))
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }

    override fun selectPDF() {
        selectPdf()
    }

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {

                //         pdfUri = data?.data!!
                val uri: Uri = data?.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        myCursor =
                            applicationContext!!.contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName =
                                myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            Log.d("***** ContainerActivity >>>", "" + pdfName)
                            //         pdfTextView.text = pdfName
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        MainViewModel.setHeaderTitle(0, "")
        super.onBackPressed()
    }
}