package com.example.android_github_clone.frgament

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.android_github_clone.R
import com.example.android_github_clone.databinding.FargmentHomeBinding

class HomeFragment : BaseFragment(), OnRefreshListener {
    lateinit var binding: FargmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FargmentHomeBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener(this@HomeFragment)

            itemRepositories.setOnClickListener {
                findNavController().navigate(R.id.repositoriesFragment)
            }
            ivSearch.setOnClickListener {
                findNavController().navigate(R.id.searchFragment)
            }
        }
    }

    override fun onRefresh() {
        Handler().postDelayed(Runnable {
            binding.swipeRefreshLayout.setRefreshing(false)
        }, 2000)
    }

}