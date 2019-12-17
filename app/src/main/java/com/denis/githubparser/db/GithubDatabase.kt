package com.denis.githubparser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.denis.githubparser.db.models.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [GithubRepository::class], version = 6, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDatabaseDao(): GithubDatabaseDao

    private class RepositoryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            if(INSTANCE == null){
                INSTANCE.let { database ->
                    scope.launch {
                        val githubDao = database!!.githubDatabaseDao()

                        // Delete all content here.
                        githubDao.clear()
                    }
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): GithubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "reposDb"
                )
                    .addCallback(RepositoryDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}