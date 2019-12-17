package com.denis.githubparser.ui

data class RepositoryModel (val id: Long,
                            val apiId: Long,
                            val authorName: String,
                            val repositoryName: String,
                            val repositoryFullName: String,
                            val url: String)