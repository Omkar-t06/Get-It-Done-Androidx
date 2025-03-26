package com.omkar.getitdone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkar.getitdone.GetItDoneApplication
import com.omkar.getitdone.date.model.Task
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val taskRepository = GetItDoneApplication.taskRepository

    fun getTaskLists() = taskRepository.getTaskLists()

    fun createTask(title: String, description: String?, listId: Int) {
        val task = Task(
            title = title,
            description = description,
            listId = listId
        )
        viewModelScope.launch {
            taskRepository.createTask(task)
        }
    }

}