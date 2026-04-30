package com.example.todofirebase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todofirebase.models.Task
import com.example.todofirebase.repositories.TaskRepository

class TaskViewModel : ViewModel() {

    private val repository = TaskRepository()

    val allTasks: LiveData<List<Task>> = repository.getAllTasks()

    fun addTask(task: Task, onResult: (Boolean) -> Unit) {
        repository.addTask(task) { isSuccess ->
            onResult(isSuccess)
        }
    }

    fun updateTask(task: Task, onResult: (Boolean) -> Unit) {
        repository.updateTask(task) { isSuccess ->
            onResult(isSuccess)
        }
    }

    fun deleteTask(taskId: String, onResult: (Boolean) -> Unit) {
        repository.deleteTask(taskId) { isSuccess ->
            onResult(isSuccess)
        }
    }
}