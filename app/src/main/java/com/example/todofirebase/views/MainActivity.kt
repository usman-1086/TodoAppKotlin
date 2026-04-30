package com.example.todofirebase.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.todofirebase.R
import com.example.todofirebase.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        setupRecyclerView()

        viewModel.allTasks.observe(this) { tasks ->
            val sortedList = tasks.sortedBy { it.taskDone }
            adapter.updateData(sortedList)
        }

        val fabAdd: FloatingActionButton = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener {
            showAddTaskBottomSheet()
        }
    }

    private fun setupRecyclerView() {
        val rvTasks: RecyclerView = findViewById(R.id.rvTasks)

        adapter = TaskAdapter(
            emptyList(),
            onDeleteClick = { task ->
                task.id?.let { id ->
                    viewModel.deleteTask(id) { success ->
                        if (success) Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onCompleteClick = { updatedTask ->
                 viewModel.updateTask(updatedTask) { success ->
                    if (success) {
                        Toast.makeText(this, "Task Completed!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Firebase Update Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        rvTasks.adapter = adapter
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskFragment()
        addTaskSheet.show(supportFragmentManager, "AddTaskTag")
    }
}