package com.omkar.getitdone.date.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.omkar.getitdone.date.model.Task
import com.omkar.getitdone.date.model.TaskList

@Database(entities = [Task::class, TaskList::class], version = 4)
abstract class GetItDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    abstract fun getTaskListDao(): TaskListDao

    companion object {
        @Volatile
        private var DATABASE_INSTANCE: GetItDoneDatabase? = null

        private val Migration_2_to_3 = object : Migration(2, 3) {

            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS 'task_list' (
                        'task_list_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        'name' TEXT NOT NULL
                    )
                """.trimIndent()
                )
                db.execSQL("INSERT INTO task_list (name) VALUES ('Tasks')")
            }

        }

        fun getDatabase(context: Context): GetItDoneDatabase {
            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GetItDoneDatabase::class.java,
                    "get-it-done-database"
                )
                    .addMigrations(Migration_2_to_3)
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL("INSERT INTO task_list (name) VALUES ('Tasks')")
                        }
                    })
                    .build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}