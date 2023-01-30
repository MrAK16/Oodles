package com.ias.oodles.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.ias.oodles.R
import com.ias.oodles.databinding.ActivityMainBinding
import com.ias.oodles.databinding.ActivitySplashBinding
import com.ias.oodles.utils.Preferences


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.videoView.setZOrderOnTop(true)
        binding.videoView.setVideoURI(
            Uri.parse(
                "android.resource://" + packageName + "/" +
                        R.raw.splash_anim
            )
        )
        binding.videoView.start()

        binding.videoView.setOnCompletionListener {
            if (Preferences.getInstance(this).isLogin){
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }

           // Animatoo.animateSlideLeft(this@SplashActivity)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}