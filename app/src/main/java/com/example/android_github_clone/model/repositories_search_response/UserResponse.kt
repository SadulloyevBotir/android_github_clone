package com.example.android_github_clone.model.repositories_search_response

data class UserResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)