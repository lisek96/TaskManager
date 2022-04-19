package com.example.prm_cwiczenia.adapter

import android.content.res.ColorStateList
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

        if(task.priority == Task.TaskPriority.LOW){
            recyclerViewItemPriorityValue.setTextColor(Color.GREEN)
        }else if(task.priority == Task.TaskPriority.MEDIUM){
            recyclerViewItemPriorityValue.setTextColor(Color.YELLOW)
        }else {
            recyclerViewItemPriorityValue.setTextColor(Color.RED)
        }

        if(task.progress > 50){
            recyclerViewItemProgressValue.setTextColor(Color.argb(100, 0, 100, 0))
        }else{
            recyclerViewItemProgressValue.setTextColor(Color.RED)
        }
    }
}