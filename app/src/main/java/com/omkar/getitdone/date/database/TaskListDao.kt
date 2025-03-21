package com.omkar.getitdone.date.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.omkar.getitdone.date.model.Task
import com.omkar.getitdone.date.model.TaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDao {

    @Insert
    suspend fun createTaskList(list: TaskList)

    @Query("SELECT * FROM task_list")
    fun getAllList(): Flow<List<TaskList>>

    @Update
    suspend fun updateTaskList(task: Task)

    @Delete
    suspend fun deleteTaskList(task: Task)
}