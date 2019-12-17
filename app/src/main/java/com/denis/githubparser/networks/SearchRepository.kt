package com.denis.githubparser.networks

import com.denis.githubparser.db.models.GithubRepository
import retrofit2.Call

class SearchRepository(private val apiService: GithubApiService) {
    fun searchRepositories(authorName: String): Call<List<GithubRepository>> {
        return apiService.search(authorName)
    }
}