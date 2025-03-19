package com.omkar.getitdone.date

import com.omkar.getitdone.date.database.TaskDao
import com.omkar.getitdone.date.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun createTask(task: Task) {
        taskDao.createTask(task)
    }

    fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllTask()
    }

    fun getStarredTasks(): Flow<List<Task>> {
        return  taskDao.getStarredTask()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

}