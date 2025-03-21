package com.omkar.getitdone

import android.app.Application
import com.omkar.getitdone.date.TaskRepository
import com.omkar.getitdone.date.database.GetItDoneDatabase

class GetItDoneApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val database = GetItDoneDatabase.getDatabase(this)
        val taskDao = database.getTaskDao()
        val taskListDao = database.getTaskListDao()
        taskRepository = TaskRepository(taskDao, taskListDao)
    }

    companion object {
        lateinit var taskRepository: TaskRepository
    }
}