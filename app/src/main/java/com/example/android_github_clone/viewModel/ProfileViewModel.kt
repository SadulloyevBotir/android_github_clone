package com.example.android_github_clone.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_github_clone.model.User
import com.example.android_github_clone.model.UserRepositoriesResponse
import com.example.android_github_clone.model.UserRepositoriesResponseItem
import com.example.android_github_clone.model.repositories_search_response.RepositoriesResponse
import com.example.android_github_clone.model.repositories_search_response.UserResponse
import com.example.android_github_clone.repository.MainRepository
import com.example.android_github_clone.utils.Logger
import com.example.githubclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    companion object {
        private val TAG: String = ProfileViewModel::class.java.simpleName.toString()
    }

    val userAllData =
        MutableLiveData<Resource<Triple<UserResponse, List<UserRepositoriesResponseItem>, User>>>()

    fun getUserAllData(token: String) {
        viewModelScope.launch {
            userAllData.postValue(Resource.loading(null))
            try {
                val userData = repository.getUserData(token)
                Logger.e("tag", "logger error30")
                val userAll = repository.getUsers(userData.username)
                Logger.e("tag", "logger error32")
                val userRepositories = repository.getUserRepositories(userData.username)
                Logger.e("tag", "logger error34")

                val triple = Triple(userAll, userRepositories, userData)
                userAllData.postValue(Resource.success(triple))
            } catch (exception: Exception) {
                userAllData.postValue(Resource.error("$exception", null))
            }
        }
    }
}


