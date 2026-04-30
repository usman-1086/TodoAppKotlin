package com.example.todofirebase.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todofirebase.models.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TaskRepository {

    private val database = FirebaseDatabase.getInstance().getReference("tasks")

    fun addTask(task: Task, onComplete: (Boolean) -> Unit) {
        val taskId = database.push().key ?: return
        val newTask = task.copy(id = taskId)

        database.child(taskId).setValue(newTask)
            .addOnCompleteListener { result ->
                onComplete(result.isSuccessful)
            }
    }

    fun getAllTasks(): LiveData<List<Task>> {
        val taskLiveData = MutableLiveData<List<Task>>()

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val taskList = mutableListOf<Task>()
                for (taskSnapshot in snapshot.children){
                    val task = taskSnapshot.getValue(Task :: class.java)
                    task?.let{taskList.add(it)}
                }
                taskLiveData.value = taskList
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return taskLiveData
    }

    fun updateTask(task: Task, onComplete: (Boolean) -> Unit) {
        val taskId = task.id ?: return

        val taskValues = mapOf(
            "taskDone" to task.taskDone
        )

        database.child(taskId).updateChildren(taskValues)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun deleteTask(taskId: String, onComplete: (Boolean) -> Unit) {
        database.child(taskId).removeValue()
            .addOnCompleteListener { result ->
                onComplete(result.isSuccessful)
            }
    }
}