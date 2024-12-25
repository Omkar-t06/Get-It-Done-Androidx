package com.omkar.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omkar.getitdone.databinding.FragmentTaksBinding
import com.omkar.getitdone.date.GetItDoneDatabase
import com.omkar.getitdone.date.Task
import com.omkar.getitdone.date.TaskDao
import kotlin.concurrent.thread

class TasksFragment : Fragment(), TasksAdapter.TaskUpdatedListener {

    private lateinit var binding: FragmentTaksBinding
    private val taskDao: TaskDao by lazy {
        GetItDoneDatabase.getDatabase(requireContext()).getTaskDao()
    }
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
        thread {
            val tasks = taskDao.getAllTask()

            requireActivity().runOnUiThread {
                adapter.setTask(tasks)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        thread {
            taskDao.updateTask(task)
            fetchAllTask()
        }
    }
}