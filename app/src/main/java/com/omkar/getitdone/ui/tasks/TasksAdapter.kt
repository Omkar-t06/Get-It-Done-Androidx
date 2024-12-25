package com.omkar.getitdone.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkar.getitdone.databinding.ItemTaskBinding
import com.omkar.getitdone.date.Task

class TasksAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TasksAdapter.ViewModel>() {

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        return ViewModel(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        holder.bind(tasks[position])
    }

    inner class ViewModel(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tasks: Task) {
            binding.textViewTitle.text = tasks.title
            binding.textViewDetails.text = tasks.description
        }
    }
}