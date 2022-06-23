package com.example.android_github_clone.frgament

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.android_github_clone.activity.BaseActivity
import com.example.android_github_clone.adapter.RepositoriesAdapter
import com.example.android_github_clone.database.PrefsManager
import com.example.android_github_clone.databinding.FragmentRepositoriesBinding
import com.example.android_github_clone.utils.Extensions.fireToast
import com.example.android_github_clone.utils.Logger
import com.example.android_github_clone.viewModel.RepositoriesViewModel
import com.example.githubclone.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesFragment : BaseFragment(),OnRefreshListener {
    lateinit var binding: FragmentRepositoriesBinding
    private val repositoriesAdapter by lazy { RepositoriesAdapter() }

    private val viewModel by viewModels<RepositoriesViewModel>()
    private lateinit var _viewLifecycleOwner: LifecycleOwner
    private lateinit var baseActivity: BaseActivity

    @Inject
    lateinit var sharedPrefs: PrefsManager

    companion object {
        val TAG: String = RepositoriesFragment::class.java.simpleName.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRepositoriesBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        baseActivity = requireActivity() as BaseActivity
        binding.apply {
            _viewLifecycleOwner = viewLifecycleOwner

            viewModel.getUserRepositories(sharedPrefs.accessToken!!)
            setupObserver()

            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }

            rvRepositories.apply {
                adapter = repositoriesAdapter
            }


        }
    }

    private fun setupObserver() {
        viewModel.userRepositories.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.hide()
                    binding.swipeRefreshLayout.isRefreshing = false
                    it.data?.let { response ->
                        repositoriesAdapter.addItems(response)
                    }
                }
                Status.LOADING -> {
                    baseActivity.show()

                }
                Status.ERROR -> {
                    //Handle Error
                    baseActivity.hide()
                    Logger.d(TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    override fun onRefresh() {
        binding.apply {
            Handler().postDelayed({
                swipeRefreshLayout.setRefreshing(false)
                viewModel.getUserRepositories(sharedPrefs.accessToken!!)
            }, 2000)
        }
    }


}