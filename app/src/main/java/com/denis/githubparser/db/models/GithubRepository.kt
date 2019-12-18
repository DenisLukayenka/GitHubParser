package com.denis.githubparser.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "repositories")
data class GithubRepository(

    @NotNull
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "api_id")
    val apiId: Long = 0,

    @ColumnInfo(name = "author_name")
    var authorName: String,

    @ColumnInfo(name = "repository_name")
    val repositoryName: String = "",

    @ColumnInfo(name = "repository_full_name")
    val repositoryFullName: String = "",

    @ColumnInfo(name = "repository_url")
    val url: String = "",

    @ColumnInfo(name = "main_language")
    val language: String? = ""
)