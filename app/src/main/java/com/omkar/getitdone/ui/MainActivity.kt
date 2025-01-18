package com.omkar.getitdone.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.omkar.getitdone.R
import com.omkar.getitdone.databinding.ActivityMainBinding
import com.omkar.getitdone.databinding.DialogAddTaskBinding
import com.omkar.getitdone.date.GetItDoneDatabase
import com.omkar.getitdone.date.Task
import com.omkar.getitdone.date.TaskDao
import com.omkar.getitdone.ui.tasks.TasksFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val database: GetItDoneDatabase by lazy { GetItDoneDatabase.getDatabase(this) }
    private val taskDao: TaskDao by lazy { database.getTaskDao() }
    private val taskFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            pager.adapter = PagerAdapter(this@MainActivity)
            TabLayoutMediator(tabs, pager) { tabs, _ ->
                tabs.text = "Tasks"
            }.attach()

            fab.setOnClickListener {
                showAddTasksDialog()
            }
            setContentView(root)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    private fun showAddTasksDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            buttonShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            buttonSave.setOnClickListener {
                val task = Task(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString(),
                )
                thread {
                    taskDao.createTask(task)
                }
                dialog.dismiss()
                taskFragment.fetchAllTask()
            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return taskFragment
        }

    }
}