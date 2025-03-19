package com.omkar.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkar.getitdone.GetItDoneApplication
import com.omkar.getitdone.date.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StarredTasksViewModel : ViewModel() {

    private val taskRepository = GetItDoneApplication.taskRepository

    fun fetchTasks(): Flow<List<Task>> {
        return taskRepository.getStarredTasks()
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}