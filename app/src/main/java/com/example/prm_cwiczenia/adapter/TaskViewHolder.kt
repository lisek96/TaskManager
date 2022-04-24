package com.example.prm_cwiczenia.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_cwiczenia.databinding.TaskItemViewBinding
import com.example.prm_cwiczenia.model.Task

class TaskViewHolder (
    private val layoutBinding: TaskItemViewBinding) : RecyclerView.ViewHolder(layoutBinding.root){


    fun bind(task: Task) = with(layoutBinding) {
        recyclerViewItemLogo.setImageResource(task.logoDrawableId)
        recyclerViewItemPriorityValue.text = task.priority.toString()
        recyclerViewItemProgressValue.text = task.progress.toString()
        recyclerViewItemDeadlineValue.text = task.deadLine.toString()
        recyclerViewItemNameValue.text = task.name
        recyclerViewItemId.text = task.taskId

        val redColor = Color.rgb(139, 0, 0)
        val yellowColor = Color.rgb(246, 160, 0)
        val greenColor = Color.rgb(1, 50, 32)
        val mapOfColorsForPriorities = mapOf(
            Task.TaskPriority.LOW to greenColor,
            Task.TaskPriority.MEDIUM to yellowColor,
            Task.TaskPriority.HIGH to redColor
        )
        mapOfColorsForPriorities[task.priority]?.let {
            recyclerViewItemPriorityValue.setTextColor(it)
        }

        if(task.progress > 50){
            recyclerViewItemProgressValue.setTextColor(greenColor)
            return
        }
        recyclerViewItemProgressValue.setTextColor(redColor)
    }
}