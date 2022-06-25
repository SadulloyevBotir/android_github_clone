package com.example.android_github_clone.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_github_clone.model.UserRepositoriesResponseItem
import com.example.android_github_clone.repository.MainRepository
import com.example.githubclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(var repository: MainRepository) :ViewModel(){
    companion object {
        private val TAG: String = RepositoriesViewModel::class.java.simpleName.toString()
    }

    val userRepositories =
        MutableLiveData<Resource<List<UserRepositoriesResponseItem>>>()

    fun getUserRepositories(token:String){
        viewModelScope.launch {
            userRepositories.postValue(Resource.loading(null))
            try {
                val userData = repository.getUserData(token)
                val repositories = repository.getUserRepositories( userData.username)
                userRepositories.postValue(Resource.success(repositories))
            } catch (exception: Exception) {
                userRepositories.postValue(Resource.error("$exception", null))
            }
        }
    }
}