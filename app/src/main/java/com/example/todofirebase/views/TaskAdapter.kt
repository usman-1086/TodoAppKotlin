package com.example.todofirebase.views

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todofirebase.R
import com.example.todofirebase.models.Task

class TaskAdapter(
    private var taskList: List<Task>,
    private val onDeleteClick: (Task) -> Unit,
    private val onCompleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val cbComplete: CheckBox = itemView.findViewById(R.id.cbComplete)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.title.text = currentTask.title

        holder.cbComplete.setOnCheckedChangeListener(null)
        holder.cbComplete.isChecked = currentTask.taskDone

        applyStrikethrough(holder.title, currentTask.taskDone)

        holder.cbComplete.setOnClickListener {

            val newStatus = holder.cbComplete.isChecked

            applyStrikethrough(holder.title, newStatus)

            val updatedTask = currentTask.copy(taskDone = newStatus)

            onCompleteClick(updatedTask)
        }
        holder.btnDelete.setOnClickListener {
            onDeleteClick(currentTask)
        }
    }

    override fun getItemCount(): Int = taskList.size

    fun updateData(newList: List<Task>) {
        this.taskList = newList
        notifyDataSetChanged()
    }

    private fun applyStrikethrough(textView: TextView, isCompleted: Boolean) {
        if (isCompleted) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.setTextColor(ContextCompat.getColor(textView.context, android.R.color.darker_gray))
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.setTextColor(ContextCompat.getColor(textView.context, android.R.color.white))
        }
    }
}