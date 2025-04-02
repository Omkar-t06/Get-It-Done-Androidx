package com.omkar.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.omkar.getitdone.databinding.FragmentTaksBinding
import com.omkar.getitdone.date.model.Task
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TasksFragment(private val taskListId: Int) : Fragment(), TasksAdapter.TaskItemClickListener {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var binding: FragmentTaksBinding
    private val adapter = TasksAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleView.adapter = adapter
        fetchAllTask()
    }

    fun fetchAllTask() {
        lifecycleScope.launch {
            viewModel.fetchTasks(taskListId).collectLatest { tasks ->
                adapter.setTask(tasks)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        viewModel.updateTask(task)
    }

    override fun onTaskDelete(task: Task) {
        viewModel.deleteTask(task)
    }
}