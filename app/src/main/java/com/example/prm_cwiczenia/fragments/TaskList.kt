package com.example.prm_cwiczenia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.activities.TaskAppController
import com.example.prm_cwiczenia.adapter.TaskAdapter
import com.example.prm_cwiczenia.databinding.TaskListViewBinding
import com.example.prm_cwiczenia.model.Task
import java.time.LocalDate

class TaskList : Fragment() {

    private lateinit var binding: TaskListViewBinding
    private val taskAdapter by lazy {
        TaskAdapter(onClickListener = {
            goTaskDetailsOnCardViewClick(it)
        }, onLongClickListener = {
            askForTaskDeleteConfirmation(it)
        })
    }

    private fun askForTaskDeleteConfirmation(id: String) {
        var controller = requireActivity() as TaskAppController
        controller.askForTaskDeleteConfirmation(id)
    }

    private fun goTaskDetailsOnCardViewClick(id: String) {
        var controller = requireActivity() as TaskAppController
        controller.goDetailsAfterListElementClick(id);
    }

    companion object {
        fun newInstance() = TaskList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateTestData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TaskListViewBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        getEndOfTheWeekSummary()
    }

    fun getTaskById(id: String): Task {
        return taskAdapter.getById(id)!!
    }

    fun generateTestData() {
        (1..2).forEach {
            taskAdapter.add(
                Task(
                    "Projekt PRM 1", Task.TaskPriority.HIGH,
                    LocalDate.of(2022, 4, 17), 25, R.drawable.pjwstk
                )
            )
        }
        (1..2).forEach {
            taskAdapter.add(
                Task(
                    "Projekt PRM 2", Task.TaskPriority.LOW,
                    LocalDate.of(2022, 4, 22), 75, R.drawable.work
                )
            )
        }

        (1..2).forEach {
            taskAdapter.add(
                Task(
                    "Projekt PRM 3", Task.TaskPriority.MEDIUM,
                    LocalDate.of(2022, 4, 18), 0, R.drawable.doctor
                )
            )
        }
        (1..2).forEach {
            taskAdapter.add(
                Task(
                    "Projekt PRM 3", Task.TaskPriority.HIGH,
                    LocalDate.of(2022, 4, 11), 100, R.drawable.doctor
                )
            )
        }
    }

    fun getTasks(): List<Task> = taskAdapter.getTasks()

    fun getName(id: String) = taskAdapter.getNameById(id)

    fun removeTask(id: String) {
        taskAdapter.removeById(id)
        getEndOfTheWeekSummary()
    }

    fun getPosition(task: Task): Int = taskAdapter.getPosition(task)

    fun getTask(position: Int) = taskAdapter.getTask(position)

    fun notifyUpdated(position: Int) {
        taskAdapter.notifyItemChanged(position)
    }

    fun getEndOfTheWeekSummary() {
        binding.priorityHighTasksNumber.text =
            getNumberOfTasksCurrentWeek(Task.TaskPriority.HIGH).toString()
        binding.priorityLowTasksNumber.text =
            getNumberOfTasksCurrentWeek(Task.TaskPriority.LOW).toString()
        binding.priorityMediumTasksNumber.text =
            getNumberOfTasksCurrentWeek(Task.TaskPriority.MEDIUM).toString()
    }

    fun getNumberOfTasksCurrentWeek(priority: Task.TaskPriority): Int {
        return taskAdapter.getNumberOfTasksCurrentWeek(priority)
    }
}