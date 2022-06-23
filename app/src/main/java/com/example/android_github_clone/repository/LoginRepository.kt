package com.example.android_github_clone.repository

import com.example.android_github_clone.network.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAccessToken(clientID: String, clientSecret: String, code: String) =
        apiService.getAccessToken(clientID, clientSecret, code)
}