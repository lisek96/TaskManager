package com.example.prm_cwiczenia.model

import com.example.prm_cwiczenia.adapter.TaskViewHolder
import java.time.LocalDate
import java.util.*

data class Task(
    var name: String,
    var priority: TaskPriority,
    var deadLine: LocalDate,
    var progress: Int,
    var logoDrawableId: Int
) {
    var taskId : String = ""

    init {
        taskId = UUID.randomUUID().toString()
    }

    enum class TaskPriority {
        LOW, MEDIUM, HIGH
    }
}