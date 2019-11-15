package com.denis.githubparser.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class GithubRepository(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "repository_name")
    val name: String = "",

    @ColumnInfo(name = "repository_full_name")
    val fullName: String = ""
)