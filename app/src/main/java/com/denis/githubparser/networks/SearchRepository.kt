package com.denis.githubparser.networks

import io.reactivex.Observable

class SearchRepository(private val apiService: GithubApiService) {
    fun searchRepositories(): Observable<List<RepositoryModel>> {
        return apiService.search()
    }
}