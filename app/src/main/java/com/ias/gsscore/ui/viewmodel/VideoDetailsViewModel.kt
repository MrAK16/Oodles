package com.ias.gsscore.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import coil.load
import com.example.example.PackageProgramDetails
import com.example.example.VideoList
import com.ias.gsscore.databinding.ActivityVideoDetailsBinding
import com.ias.gsscore.network.ApiInterface
import com.ias.gsscore.network.RetrofitHelper
import com.ias.gsscore.network.response.myaccount.VideoDetails
import com.ias.gsscore.network.response.myaccount.VideoDetailsResponse
import com.ias.gsscore.ui.activity.VideoWebViewActivity
import com.ias.gsscore.ui.adapter.OtherVideosListAdapter
import com.ias.gsscore.utils.Preferences
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@SuppressLint("StaticFieldLeak")
class VideoDetailsViewModel(binding: ViewDataBinding) : ViewModel() {
    lateinit var childFragmentManager: FragmentManager
    var binding = binding as ActivityVideoDetailsBinding
    var youtubeId = "n261iHgD1rs"

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    var isVideoYoutube: ObservableInt = ObservableInt(0)
    var otherVideosListAdapter = OtherVideosListAdapter()
    lateinit var loadingDialog: AlertDialog
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val apiService = RetrofitHelper.getInstance().create(ApiInterface::class.java)
    private var videoDetails: VideoDetails? = null
    lateinit var lifecycle: Lifecycle
    fun backPress() {
        (context as Activity).onBackPressed()
    }

    fun videoDetailsApiCall(videoId: String, type: String, packageId: String, programId: String) {
        var request: HashMap<String, String> = HashMap()
        request["user_id"] = Preferences.getInstance(context).userId
        request["video_id"] = videoId
        request["package_id"] = packageId
        request["program_id"] = programId
        coroutineScope.launch {
            loadingDialog.show()
            val result = apiService.videoDetails(request)
            val response: VideoDetailsResponse = result.body()!!
            if (response.status) {
                videoDetails = response.videoDetails
                setDataVideoDetails(
                    videoDetails!!,
                    response.relatedVideo,
                    type,
                    response.packageProgramDetails
                )
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

    fun videoDownload() {
        if (!videoDetails!!.videoUrl!!.contains("http")) {
            Toast.makeText(
                context,
                "URL not found",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun setDataVideoDetails(
        videoDetails: VideoDetails,
        relatedVideo: ArrayList<VideoList>,
        type: String,
        packageProgramDetails: ArrayList<PackageProgramDetails>
    ) {
        binding.tvTitle.text = videoDetails.title
        binding.tvDescription.text = videoDetails.description
        binding.tvDuration.text = videoDetails.duration
        binding.start.text = videoDetails.startDate
        binding.tvThoughtBy.text = videoDetails.toughtBy
        otherVideosListAdapter.update(relatedVideo, context, type, packageProgramDetails)
        binding.videoThumbnail.load(videoDetails.thumbnail)
         when {
             videoDetails!!.videoType.equals("1") -> {
                 //Youtube
                 isVideoYoutube.set(0)
                 watchYoutubeVideo(videoDetails.videoUrlId!!)
             }
             videoDetails!!.videoType.equals("2") -> {
                 //Vimeo
                 isVideoYoutube.set(1)
                 setupVimeoView(videoDetails.videoUrlId)
             }
             else -> {
                 //Edugyaan
                 binding.videoThumbnail.load(videoDetails.thumbnail)
                 isVideoYoutube.set(2)
             }
         }
    }

    fun clickPlay() {
       /* val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse(videoDetails!!.videoUrl)
        context.startActivity(openURL)*/
        // Video Type 3
        val intent = Intent(context, VideoWebViewActivity::class.java)
        intent.putExtra("title", videoDetails!!.title)
       // intent.putExtra("url", "https://iasscore.edugyaan.com/video/externalViewGS/uid/170885/username/nitesh@basix.in/videoId/313481/sessionId/f75ce31ec0a2cbda/ip_address/223.233.79.52")
          intent.putExtra("url",videoDetails!!.videoUrl)
        context.startActivity(intent)
    }

    private fun setupVimeoView(videoUrlId: String?) {
        //  https://player.vimeo.com/external/684553812.m3u8?s=942a7042191491c500c7a0a62bc8500c1bbf9370
        lifecycle.addObserver(binding.vimeoPlayerView)
        binding.vimeoPlayerView.initialize(true, videoUrlId!!.toInt()/*59777392*/)
        binding.vimeoPlayerView.setFullscreenVisibility(true)
    }

    fun watchYoutubeVideo(videoUrlId: String) {
        /*  binding.youtubeView.enterFullScreen();
          binding.youtubeView.toggleFullScreen();*/
        lifecycle.addObserver(binding.youtubeView)
        binding.youtubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                // loading the selected video into the YouTube Player
                youTubePlayer.cueVideo(videoUrlId, 0F)
            }
        })
    }
}