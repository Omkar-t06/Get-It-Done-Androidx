package com.omkar.getitdone

import android.app.Application
import com.omkar.getitdone.date.GetItDoneDatabase
import com.omkar.getitdone.date.TaskDao

class GetItDoneApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        database = GetItDoneDatabase.getDatabase(this)
        taskDao = database.getTaskDao()
    }

    companion object {
        lateinit var database: GetItDoneDatabase
        lateinit var taskDao: TaskDao
    }
}