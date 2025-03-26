package com.omkar.getitdone.date.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = TaskList::class,
            parentColumns = ["task_list_id"],
            childColumns = ["list_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @ColumnInfo(name = "task_id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    @ColumnInfo(name = "is_starred") val isStarred: Boolean = false,
    @ColumnInfo(name = "is_complete") val isComplete: Boolean = false,
    @ColumnInfo(name = "list_id") val listId: Int
)
