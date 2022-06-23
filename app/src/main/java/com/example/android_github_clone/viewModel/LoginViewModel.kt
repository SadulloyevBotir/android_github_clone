package com.example.android_github_clone.viewModel

import android.nfc.Tag
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_github_clone.model.AccessToken
import com.example.android_github_clone.repository.LoginRepository
import com.example.android_github_clone.utils.ApiConstans.clientID
import com.example.android_github_clone.utils.ApiConstans.clientSecret
import com.example.android_github_clone.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    companion object {
        private val TAG: String = LoginViewModel::class.java.simpleName
    }

    val accessToken = MutableLiveData<AccessToken>()

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            try {
                accessToken.value = repository.getAccessToken(clientID, clientSecret, code)
                Logger.d(TAG, "AccessToken : ${accessToken.value!!.accessToken}")
            } catch (exception: Exception) {
                Logger.e(TAG, exception.message.toString())
            }
        }

    }
}