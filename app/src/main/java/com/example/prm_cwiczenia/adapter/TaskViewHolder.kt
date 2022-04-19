package com.example.prm_cwiczenia.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_cwiczenia.databinding.TaskItemViewBinding
import com.example.prm_cwiczenia.model.Task

class TaskViewHolder (
    private val layoutBinding: TaskItemViewBinding) : RecyclerView.ViewHolder(layoutBinding.root){

    fun bind(task: Task) = with(layoutBinding) {
        val redColor = Color.rgb(139, 0, 0)
        val yellowColor = Color.rgb(246, 160, 0)
        val greenColor = Color.rgb(1, 50, 32)
        recyclerViewItemLogo.setImageResource(task.logoDrawableId)
        recyclerViewItemPriorityValue.text = task.priority.toString()
        recyclerViewItemProgressValue.text = task.progress.toString()
        recyclerViewItemDeadlineValue.text = task.deadLine.toString()
        recyclerViewItemNameValue.text = task.name
        recyclerViewItemId.text = task.taskId

        if(task.priority == Task.TaskPriority.LOW){
            recyclerViewItemPriorityValue.setTextColor(greenColor)
        }else if(task.priority == Task.TaskPriority.MEDIUM){
            recyclerViewItemPriorityValue.setTextColor(yellowColor)
        }else {
            recyclerViewItemPriorityValue.setTextColor(redColor)
        }

        if(task.progress > 50){
            recyclerViewItemProgressValue.setTextColor(greenColor)
        }else{
            recyclerViewItemProgressValue.setTextColor(redColor)
        }
    }
}