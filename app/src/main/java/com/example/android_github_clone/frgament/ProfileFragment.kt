package com.example.android_github_clone.frgament

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bumptech.glide.Glide
import com.example.android_github_clone.activity.BaseActivity
import com.example.android_github_clone.adapter.ProfileRepositoriesAdapter
import com.example.android_github_clone.adapter.ProfileRepositoriesItemDecoration
import com.example.android_github_clone.database.PrefsManager
import com.example.android_github_clone.databinding.FragmentProfileBinding
import com.example.android_github_clone.model.User
import com.example.android_github_clone.model.UserRepositoriesResponseItem
import com.example.android_github_clone.model.UsersResponse
import com.example.android_github_clone.utils.Extensions.fireToast
import com.example.android_github_clone.utils.Logger
import com.example.android_github_clone.viewModel.ProfileViewModel
import com.example.githubclone.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment(), OnRefreshListener {
    lateinit var binding: FragmentProfileBinding
    lateinit var profileAdapter: ProfileRepositoriesAdapter

    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var baseActivity: BaseActivity


    private lateinit var _viewLifecycleOwner: LifecycleOwner

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName.toString()
    }

    @Inject
    lateinit var sharedPrefs: PrefsManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        intiViews()
        return binding.root
    }

    private fun intiViews() {
        _viewLifecycleOwner = viewLifecycleOwner
        baseActivity = requireActivity() as BaseActivity
        viewModel.getUserAllData(sharedPrefs.accessToken!!)
        setupObserver()

        refreshProfileRepositoriesRecyclerView()
    }

    private fun refreshProfileRepositoriesRecyclerView() {

        binding.rvRepositories.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            profileAdapter = ProfileRepositoriesAdapter()
            adapter = profileAdapter
            val itemDecoration = ProfileRepositoriesItemDecoration(15, 15, 15, 15)
            addItemDecoration(itemDecoration)
        }
    }

    private fun setupObserver() {
        viewModel.userAllData.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.hide()
                    binding.swipeRefreshLayout.isRefreshing = false
                    it.data?.let { triple -> setDataToUI(triple) }
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

    private fun setDataToUI(triple: Triple<UsersResponse, List<UserRepositoriesResponseItem>, User>) {
        val usersResponse = triple.first
        val userRepositoriesResponse = triple.second
        val user = triple.third
        val firstUser = usersResponse.items?.get(0)

        binding.apply {
            Glide.with(binding.root.context).load(firstUser?.avatarUrl).into(ivProfile)
            tvName.text = user.name
            tvNikname.text = firstUser?.login
            tvUserBio.text = user.bio
            tvLocation.text = user.location
            tvCountFollowers.text = user.followers.toString()
            tvCountFollowers.text = user.following.toString()
            tvRepositoryCount.text = userRepositoriesResponse.size.toString()
        }

        profileAdapter.addItems(userRepositoriesResponse)
    }

    override fun onRefresh() {
        binding.apply {
            Handler().postDelayed({
                swipeRefreshLayout.setRefreshing(false)
                viewModel.getUserAllData(sharedPrefs.accessToken!!)
            }, 2000)
        }
    }
}