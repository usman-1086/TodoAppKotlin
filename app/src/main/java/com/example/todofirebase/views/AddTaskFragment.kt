package com.example.todofirebase.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todofirebase.R
import com.example.todofirebase.models.Task
import com.example.todofirebase.viewmodels.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()

            if (title.isNotEmpty()) {

                val newTask = Task(
                    id = null,
                    title = title,
                    timestamp = System.currentTimeMillis(),
                    taskDone = false
                )

                viewModel.addTask(newTask) { success ->
                    if (success) {
                        Toast.makeText(context, "Task Added Successfully", Toast.LENGTH_SHORT).show()
                        dismiss()
                    } else {
                        Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}