package com.example.android_github_clone.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.example.android_github_clone.database.PrefsManager
import com.example.android_github_clone.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    lateinit var binding: ActivitySplashBinding
    private var timer: CountDownTimer? = null

    @Inject
    lateinit var sharedPrefs: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        timer()
    }

    private fun timer() {
        timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (sharedPrefs.accessToken.isNullOrBlank()) callLoginActivity(this@SplashActivity) else callMainActivity(this@SplashActivity)
            }
        }
        timer!!.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            timer = null
        }
    }

}