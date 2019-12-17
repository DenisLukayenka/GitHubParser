package com.denis.githubparser.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "repositories")
data class GithubRepository(

    @NotNull
    @PrimaryKey
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "author_name")
    var authorName: String,

    @ColumnInfo(name = "repository_name")
    @SerializedName("name")
    val repositoryName: String = "",

    @ColumnInfo(name = "repository_url")
    @SerializedName("url")
    val url: String = "",

    @ColumnInfo(name = "repository_language")
    val language: String = ""
)