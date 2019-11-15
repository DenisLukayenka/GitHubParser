package com.denis.githubparser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.denis.githubparser.db.models.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [GithubRepository::class], version = 2, exportSchema = false)
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

                        // Add sample words.
                        var repository = GithubRepository(0, "name1", "fullname1")
                        githubDao.insert(repository)

                        repository = GithubRepository(0, "name2", "fullname2")
                        githubDao.insert(repository)
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