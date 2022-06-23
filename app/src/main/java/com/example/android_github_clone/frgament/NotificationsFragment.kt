package com.example.android_github_clone.frgament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_github_clone.databinding.FragmentNotificationBinding

class NotificationsFragment : BaseFragment() {
    lateinit var binding: FragmentNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {

    }
}