package com.omkar.getitdone.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.omkar.getitdone.R
import com.omkar.getitdone.databinding.ActivityMainBinding
import com.omkar.getitdone.databinding.DialogAddTaskBinding
import com.omkar.getitdone.databinding.DialogAddTaskListBinding
import com.omkar.getitdone.databinding.TabButtonBinding
import com.omkar.getitdone.date.model.TaskList
import com.omkar.getitdone.ui.tasks.StarredTasksFragment
import com.omkar.getitdone.ui.tasks.TasksFragment
import com.omkar.getitdone.util.InputValidator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var currentTaskList: List<TaskList> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {

            lifecycleScope.launch {
                viewModel.getTaskLists().collectLatest { taskLists ->
                    currentTaskList = taskLists
                    pager.adapter = PagerAdapter(
                        this@MainActivity,
                        numberOfPages = taskLists.size + 2
                    )
                    pager.currentItem = 1
                    TabLayoutMediator(tabs, pager) { tabs, position ->
                        when (position) {
                            0 -> tabs.icon =
                                ContextCompat.getDrawable(
                                    this@MainActivity,
                                    R.drawable.ic_star_filled
                                )

                            taskLists.size + 1 -> {
                                val buttonBinding = TabButtonBinding.inflate(layoutInflater)
                                tabs.customView = buttonBinding.root.apply {
                                    this.setOnClickListener {
                                        showAddTaskListDialog()
                                    }
                                }
                            }

                            else -> tabs.text = taskLists[position - 1].name
                        }
                    }.attach()
                }
            }

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

    private fun showAddTaskListDialog() {
        val dialogBinding = DialogAddTaskListBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.add_new_list_dialog_title))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.create_button_text)) { dialog, _ ->
                viewModel.addNewTaskList(dialogBinding.editTextListName.text?.toString())
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel_button_test)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showAddTasksDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            editTextTaskTitle.addTextChangedListener { input ->
                buttonSave.isEnabled = InputValidator.isInputValid(input?.toString())
            }

            buttonShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
            }

            buttonSave.setOnClickListener {
                val selectedTaskId = currentTaskList[binding.pager.currentItem - 1].id
                viewModel.createTask(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString(),
                    listId = selectedTaskId
                )
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity, private val numberOfPages: Int) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = numberOfPages

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StarredTasksFragment()
                else -> TasksFragment()
            }
        }
    }
}