package com.ias.gsscore.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ct7ct7ct7.androidvimeoplayer.model.PlayerState
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerActivity
import com.example.example.VideoList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ias.gsscore.R
import com.ias.gsscore.databinding.ActivityVideoDetailsBinding
import com.ias.gsscore.network.RetrofitHelper
import com.ias.gsscore.ui.adapter.VideoDetailsPagerAdapter
import com.ias.gsscore.ui.adapter.VideosListAdapter
import com.ias.gsscore.ui.viewmodel.VideoDetailsViewModel
import com.ias.gsscore.utils.SingletonClass
import com.ias.gsscore.viewmodelfactory.ActivityViewModelFactory


class VideoDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoDetailsBinding
    lateinit var viewModel: VideoDetailsViewModel
    private val RECOVERY_DIALOG_REQUEST = 1
    var type = ""
    var videoId = ""
    private var packageId = ""
    var programId = ""
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var videosListAdapter: VideosListAdapter? = null
    private var tabName = arrayOf(
        "Comments",
        "Test & Quiz",
        "Resources"
    )
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                var playAt =
                    data!!.getFloatExtra(VimeoPlayerActivity.RESULT_STATE_VIDEO_PLAY_AT, 0f)
                binding.vimeoPlayerView.seekTo(playAt)

                var playerState =
                    PlayerState.valueOf(data.getStringExtra(VimeoPlayerActivity.RESULT_STATE_PLAYER_STATE)!!)
                when (playerState) {
                    PlayerState.PLAYING -> binding.vimeoPlayerView.play()
                    PlayerState.PAUSED -> binding.vimeoPlayerView.pause()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_details)
        viewModel = ViewModelProvider(this,ActivityViewModelFactory(binding,application))[VideoDetailsViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.context = this
        viewModel.lifecycle = lifecycle
        type = intent.getStringExtra("whereFrom").toString()
        videoId = intent.getStringExtra("videoId").toString()
        packageId = intent.getStringExtra("packageId").toString()
        programId = intent.getStringExtra("programId").toString()
        title = intent.getStringExtra("title").toString()
        binding.headerTitle.text = title
      //  binding.youtubeView.initialize(RetrofitHelper.YOUTUBE_API_KEY, this)
        initialData()
    }

    override fun onDestroy() {
        binding.youtubeView.release()
        super.onDestroy()
    }

    private fun initialData() {
        viewModel.loadingDialog = RetrofitHelper.loadingDialog(this)

        if (type == "My Downloads") {
            binding.layoutTabLayout.visibility = View.GONE
            binding.tvOtherVideo.text = "Other Downloaded Videos"
            binding.tvDelete.text = "Delete"
            binding.icDownloads.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_delete_grey
                )
            )
            linearLayoutManager = LinearLayoutManager(this)
            binding.recyclerView.layoutManager = linearLayoutManager
        } else {
            linearLayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
            viewModel.videoDetailsApiCall(videoId!!, type, packageId, programId)
            setTabLayout()
            binding.vimeoPlayerView.setFullscreenClickListener {
                var requestOrientation = VimeoPlayerActivity.REQUEST_ORIENTATION_LANDSCAPE
                resultLauncher.launch(
                    VimeoPlayerActivity.createIntent(
                        this,
                        requestOrientation,
                        binding.vimeoPlayerView
                    )
                )
            }
        }
        setVideosAdapter(type)
    }

    private fun setVideosAdapter(type: String) {
        if (type == "My Downloads") {
            var videoList: ArrayList<VideoList> = arrayListOf()
            videosListAdapter =
                VideosListAdapter(
                   this,
                    videoList,
                    type,
                    null,
                    packageId
                )
            binding.recyclerView.adapter = videosListAdapter
        }
    }

    private fun setTabLayout() {
        var viewPagerAdapter =
            VideoDetailsPagerAdapter(
                SingletonClass.instance.supportFragmentManager!!,
                lifecycle,
                this,
                "videoDetails",
                3,
                null,
                null
            )
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.tabRippleColor = null
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            Log.e("TAG", "setTabLayout: $position")
            tab.text = tabName[position]
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

        })
    }

  /*  override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player!!.cueVideo("https://www.youtube.com/watch?v=0jqikf3IX3g")

            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult?
    ) {
        if (errorReason!!.isUserRecoverableError) {
            errorReason!!.getErrorDialog(
               this,
                RECOVERY_DIALOG_REQUEST
            ).show()
        } else {
            val errorMessage = String.format(
                getString(R.string.error_player), errorReason.toString()
            )
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }*/


}