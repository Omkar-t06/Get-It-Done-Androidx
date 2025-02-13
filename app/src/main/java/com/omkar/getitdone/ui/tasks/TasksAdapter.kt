package com.omkar.getitdone.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkar.getitdone.databinding.ItemTaskBinding
import com.omkar.getitdone.date.Task

class TasksAdapter(val listener: TaskItemClickListener) :
    RecyclerView.Adapter<TasksAdapter.ViewModel>() {

    override fun getItemCount() = tasks.size
    private var tasks: List<Task> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        return ViewModel(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTask(tasks: List<Task>) {
        this.tasks = tasks.sortedBy { it.isComplete }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        holder.bind(tasks[position])
    }

    inner class ViewModel(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                root.setOnLongClickListener {
                    listener.onTaskDelete(task)
                    true
                }
                checkBox.isChecked = task.isComplete
                toggleStar.isChecked = task.isStarred

                if (task.isComplete) {
                    textViewTitle.paintFlags =
                        textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textViewDetails.paintFlags =
                        textViewDetails.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTitle.paintFlags = 0
                    textViewDetails.paintFlags = 0
                }

                textViewTitle.text = task.title
                if (task.description.isNullOrEmpty()) {
                    textViewDetails.visibility = View.GONE
                } else {
                    textViewDetails.visibility = View.VISIBLE
                    textViewDetails.text = task.description
                }

                checkBox.setOnClickListener {
                    val updatedTask = task.copy(isComplete = checkBox.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
                toggleStar.setOnClickListener {
                    val updatedTask = task.copy(isStarred = toggleStar.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
            }
        }
    }

    interface TaskItemClickListener {
        fun onTaskUpdated(task: Task)

        fun onTaskDelete(task: Task)
    }
}