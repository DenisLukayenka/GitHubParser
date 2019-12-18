package com.denis.githubparser.ui

import com.denis.githubparser.db.GithubDatabaseDao
import com.denis.githubparser.db.models.GithubRepository

class GithubDbRepository(private val dbDao: GithubDatabaseDao){
    suspend fun insertAll(reps: List<RepositoryModel>){
        val daoList = castListToDao(reps)

        dbDao.insertAll(daoList)
    }

    suspend fun insert(rep: RepositoryModel){
        dbDao.insert(castElementToDao(rep))
    }

    suspend fun getByAuthor(authorName: String) : List<RepositoryModel>{
        return castListFromDao(dbDao.getByAuthor(authorName))
    }

    suspend fun deleteByAuthor(authorName: String){
        dbDao.deleteByAuthor(authorName)
    }

    suspend fun getAllRepositories(): List<RepositoryModel>{
        return castListFromDao(dbDao.getAllRepositories())
    }

    private fun castListToDao(list: List<RepositoryModel> ): List<GithubRepository>{
        return list.map { castElementToDao(it) }
    }

    private fun castElementToDao(model: RepositoryModel): GithubRepository{
        return GithubRepository(
            model.id,
            model.apiId,
            model.authorName,
            model.repositoryName,
            model.repositoryFullName,
            model.url,
            model.language
        )
    }

    private fun castElementFromDao(model: GithubRepository): RepositoryModel{
        return RepositoryModel(
            model.id,
            model.apiId,
            model.authorName,
            model.repositoryName,
            model.repositoryFullName,
            model.url,
            model.language
        )
    }

    private fun castListFromDao(list: List<GithubRepository> ): List<RepositoryModel>{
        return list.map { castElementFromDao(it) }
    }
}