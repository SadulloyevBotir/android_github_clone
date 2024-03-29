package com.example.android_github_clone.frgament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_github_clone.adapter.ExploreAdapter
import com.example.android_github_clone.databinding.FragmentExploreBinding
import com.example.android_github_clone.model.Explore

class ExploreFragment : BaseFragment() {
    lateinit var binding: FragmentExploreBinding
    private val exploreAdapter by lazy { ExploreAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    private fun initViews() {
        refreshExploreRecyclerview()
    }

    private fun refreshExploreRecyclerview() {
        binding.rvExplore.apply {
            adapter = exploreAdapter
        }
        exploreAdapter.addItems(prepareModelsList())
    }
}