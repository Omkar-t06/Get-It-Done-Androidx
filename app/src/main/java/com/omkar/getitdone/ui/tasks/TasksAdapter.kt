package com.omkar.getitdone.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkar.getitdone.databinding.ItemTaskBinding
import com.omkar.getitdone.date.Task

class TasksAdapter(val listener: TaskUpdatedListener) :
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
            binding.checkBox.isChecked = task.isComplete
            binding.toggleStar.isChecked = task.isStarred

            if (task.isComplete) {
                binding.textViewTitle.paintFlags =
                    binding.textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewDetails.paintFlags =
                    binding.textViewDetails.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewTitle.paintFlags = 0
                binding.textViewDetails.paintFlags = 0
            }

            binding.textViewTitle.text = task.title
            binding.textViewDetails.text = task.description

            binding.checkBox.setOnClickListener {
                val updatedTask = task.copy(isComplete = binding.checkBox.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
            binding.toggleStar.setOnClickListener {
                val updatedTask = task.copy(isStarred = binding.toggleStar.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}