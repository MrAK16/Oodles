package com.ias.oodles.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ias.oodles.databinding.ActivityPersonalProfileBinding
import com.ias.oodles.network.ApiInterface
import com.ias.oodles.network.RetrofitHelper
import com.ias.oodles.network.response.OodlesApiResponse
import com.ias.oodles.network.response.myaccount.AllTestResponse
import com.ias.oodles.network.response.myaccount.ProfileResponse
import com.ias.oodles.network.response.myaccount.UpdateProfileResponse
import com.ias.oodles.ui.adapter.VideosListAdapter
import com.ias.oodles.ui.viewmodel.HeaderBackViewModel
import com.ias.oodles.ui.viewmodel.PersonalProfileViewModel
import com.ias.oodles.utils.Preferences
import com.ias.oodles.viewmodelfactory.ActivityViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PersonalProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalProfileBinding;
    lateinit var viewModel: PersonalProfileViewModel
    lateinit var headerBackViewModel: HeaderBackViewModel
    private val viewModelJob = Job()
    lateinit var loadingDialog: AlertDialog
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val apiService = RetrofitHelper.getInstance().create(ApiInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            ActivityViewModelFactory(binding, application)
        )[PersonalProfileViewModel::class.java]
        headerBackViewModel = ViewModelProvider(
            this,
            ActivityViewModelFactory(binding, application)
        )[HeaderBackViewModel::class.java]
        setContentView(binding.root)
        loadingDialog = RetrofitHelper.loadingDialog(this)
        binding.viewmodel = viewModel
        binding.headerBack.backHeaderViewModel = headerBackViewModel
        viewModel.context = this
        headerBackViewModel.context = this
        binding.headerBack.tvTitle.text = "Personal Profile"
        getProfile()

        binding.update.setOnClickListener{
            updateProfile()
        }

        binding.editName.setOnClickListener{
            binding.name.requestFocus()
            binding.name.isCursorVisible=true
        }
        binding.disEdit.setOnClickListener{
            binding.disName.requestFocus()
            binding.disName.isCursorVisible=true
        }

    }

    private fun getProfile() {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(this).userId
        coroutineScope.launch {
            loadingDialog.show()
            val result = apiService.userProfile(request)
            val response: ProfileResponse = result.body()!!
            if (response.status) {
                binding.name.setText(response.user_profile.fullname)
                binding.email.setText(response.user_profile.email)
                binding.disName.setText(response.user_profile.fullname)
                if(response.user_profile.contact!=null) {
                    binding.mobile.setText(response.user_profile.contact)
                }else{
                    binding.mobile.setText(response.user_profile.mobile)
                }
                }
             else {
                Toast.makeText(
                    applicationContext,
                    response.error,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            loadingDialog.dismiss()
        }
    }

    private fun updateProfile() {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(this).userId
        request["fullname"] = binding.name.text.toString()
        request["display_name"] = binding.disName.text.toString()
        coroutineScope.launch {
            loadingDialog.show()
            val result = apiService.updateUserProfile(request)
            val response: OodlesApiResponse = result.body()!!
            if (response.status) {
                Preferences.getInstance(applicationContext).userName=binding.name.text.toString()
                Toast.makeText(applicationContext,response.message,Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(
                    applicationContext,
                    response.error,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            loadingDialog.dismiss()
        }
    }
}