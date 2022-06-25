package com.example.android_github_clone.model.repositories_search_response

data class RepositoriesResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)