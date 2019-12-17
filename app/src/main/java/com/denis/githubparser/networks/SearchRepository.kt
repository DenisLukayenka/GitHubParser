package com.denis.githubparser.networks

import retrofit2.Call

class SearchRepository(private val apiService: GithubApiService) {
    fun searchRepositories(authorName: String): Call<List<ReposResponseModel>> {
        return apiService.search(authorName)
    }
}