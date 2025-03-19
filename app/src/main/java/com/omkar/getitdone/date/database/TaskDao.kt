package com.omkar.getitdone.date.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.omkar.getitdone.date.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(task: Task)

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}