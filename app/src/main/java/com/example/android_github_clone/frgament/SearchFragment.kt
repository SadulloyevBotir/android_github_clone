package com.example.android_github_clone.frgament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_github_clone.activity.BaseActivity
import com.example.android_github_clone.adapter.RepositoriesSearchAdapter
import com.example.android_github_clone.adapter.UsersSearchAdapter
import com.example.android_github_clone.database.PrefsManager
import com.example.android_github_clone.databinding.FragmentSearchBinding
import com.example.android_github_clone.utils.Extensions.fireToast
import com.example.android_github_clone.utils.Logger
import com.example.android_github_clone.viewModel.SearchViewModel
import com.example.githubclone.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var baseActivity: BaseActivity
    private lateinit var _viewLifecycleOwner: LifecycleOwner
    lateinit var usersSearchAdapter: UsersSearchAdapter
    lateinit var repositoriesSearchAdapter: RepositoriesSearchAdapter

    @Inject
    lateinit var sharedPrefs: PrefsManager

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.etSearch.requestFocus()
        baseActivity = requireActivity() as BaseActivity
        _viewLifecycleOwner = viewLifecycleOwner

        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }


            etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    return@OnEditorActionListener true
                }
                false
            })

        }

    }

    private fun refreshUserSearchRecyclerView() {
        binding.rvSearch.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            usersSearchAdapter = UsersSearchAdapter()
            adapter = usersSearchAdapter
        }
    }

    private fun refreshRepositoriesRecyclerView() {
        binding.rvSearch.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            repositoriesSearchAdapter = RepositoriesSearchAdapter()
            adapter = repositoriesSearchAdapter
        }
    }

    private fun performSearch() {
        val query = binding.etSearch.text.toString()

        if (query.isNotEmpty() && query.isNotBlank()) {
            when (binding.radioGroup.checkedRadioButtonId) {
                binding.radioButton1.id -> {
                    if (binding.radioButton1.isChecked == true) {
                        baseActivity.show()
                        viewModel.getRepositories(query)
                        refreshRepositoriesRecyclerView()
                        setupRepositoriesObserver()
                    }
                }
                binding.radioButton2.id -> {
                    if (binding.radioButton2.isChecked == true) {
                        baseActivity.show()
                        viewModel.getUsers(query)
                        setupUsersObserver()
                        refreshUserSearchRecyclerView()
                    }
                }
                else -> {
                    baseActivity.show()
                    viewModel.getRepositories(query)
                    setupRepositoriesObserver()
                    refreshRepositoriesRecyclerView()
                }
            }
        }
    }

    private fun setupUsersObserver() {
        viewModel.usersResponse.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.hide()
                    binding.rvSearch.visibility = View.VISIBLE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    it.data?.let { response ->
                        usersSearchAdapter.addItems(response)
                    }
                }
                Status.LOADING -> {
                    baseActivity.show()
                    binding.rvSearch.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    baseActivity.hide()
                    binding.rvSearch.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setupRepositoriesObserver() {
        viewModel.repositoriesResponse.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.hide()
                    binding.rvSearch.visibility = View.VISIBLE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    it.data?.let { response ->
                        repositoriesSearchAdapter.addItems(response)
                        Logger.d("@@@", response.items.toString())
                        fireToast(response.items.toString())
                    }
                }
                Status.LOADING -> {
                    baseActivity.show()
                    binding.rvSearch.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    baseActivity.hide()
                    binding.rvSearch.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                    Logger.d(ProfileFragment.TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

}