package com.example.prm_cwiczenia.model

import com.example.prm_cwiczenia.adapter.TaskViewHolder
import java.time.LocalDate
import java.util.*

data class Task(
    val name: String,
    val priority: TaskPriority,
    val deadLine: LocalDate,
    var progress: Int,
    val logoDrawableId: Int
) {
    var taskId : String = ""

    init {
        taskId = UUID.randomUUID().toString()
    }

    lateinit var taskViewHolder : TaskViewHolder

    enum class TaskPriority {
        LOW, MEDIUM, HIGH
    }
}