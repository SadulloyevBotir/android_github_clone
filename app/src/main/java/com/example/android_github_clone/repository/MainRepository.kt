package com.example.android_github_clone.repository

import com.example.android_github_clone.network.ApiService
import javax.inject.Inject


class MainRepository @Inject constructor(private var apiService: ApiService) {
    suspend fun getUserData(token: String) = apiService.getUserData()
    suspend fun getUserRepositories(token: String, username: String) = apiService.getUserRepositories(username)
    suspend fun getUsers(query: String) = apiService.searchUsers(query)
    suspend fun getRepositories(query: String) = apiService.searchRepositories(query)


}