package com.omkar.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkar.getitdone.GetItDoneApplication
import com.omkar.getitdone.date.Task
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    private val taskDao = GetItDoneApplication.taskDao

     suspend fun fetchTasks(): List<Task> {
        val tasks = taskDao.getAllTask()
        return tasks
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

}