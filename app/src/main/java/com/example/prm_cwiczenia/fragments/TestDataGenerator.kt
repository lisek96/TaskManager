package com.example.prm_cwiczenia.fragments

import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.model.Task
import java.time.LocalDate
import java.util.*

class TestDataGenerator {

    companion object {

        fun generateData(): List<Task> {
            val random = Random()
            val tasksList = mutableListOf<Task>()
//            (1..2).forEach {
//                tasksList.add(
//                    Task(
//                        "Projekt PRM 1",
//                        Task.TaskPriority.HIGH,
//                        LocalDate.now().plusDays(random.nextInt(100).toLong()),
//                        random.nextInt(100),
//                        R.drawable.pjwstk
//                    )
//                )
//            }

            tasksList.add(
                Task(
                    "Projekt PRM 2",
                    Task.TaskPriority.LOW,
                    LocalDate.now().plusDays(random.nextInt(100).toLong()),
                    random.nextInt(100),
                    R.drawable.work
                )
            )
//
//            (1..2).forEach {
//                tasksList.add(
//                    Task(
//                        "Projekt PRM 3",
//                        Task.TaskPriority.MEDIUM,
//                        LocalDate.now().plusDays(random.nextInt(3).toLong()),
//                        random.nextInt(100),
//                        R.drawable.doctor
//                    )
//                )
//            }
            (1..2).forEach {
                tasksList.add(
                    Task(
                        "Projekt PRM 4",
                        Task.TaskPriority.HIGH,
                        LocalDate.now().plusDays(random.nextInt(5).toLong()),
                        random.nextInt(100),
                        R.drawable.pjwstk
                    )
                )
            }
            (1..2).forEach {
                tasksList.add(
                    Task(
                        "Projekt PRM 5",
                        Task.TaskPriority.MEDIUM,
                        LocalDate.now().plusDays(random.nextInt(7).toLong()),
                        random.nextInt(100),
                        R.drawable.doctor
                    )
                )
            }
            return tasksList
        }
    }
}