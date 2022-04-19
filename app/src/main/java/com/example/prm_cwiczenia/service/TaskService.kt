package com.example.prm_cwiczenia.service

import android.os.Bundle
import com.example.prm_cwiczenia.model.Task

class TaskService {

    fun createBundleFromTask(task: Task): Bundle {
        val bundleToReturn = Bundle().apply {
            putString("name", task.name)
            putString("progress", task.progress.toString())
            putString("priority", task.priority.toString())
            putString("deadline", task.deadLine.toString())
            putString("logo", task.logoDrawableId.toString())
            putString("id", task.taskId)
        }
        return bundleToReturn
    }
}