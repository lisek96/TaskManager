package com.example.prm_cwiczenia.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.activities.TaskAppController
import com.example.prm_cwiczenia.adapter.TaskAdapter
import com.example.prm_cwiczenia.databinding.TaskListViewBinding
import com.example.prm_cwiczenia.model.Task
import java.time.LocalDate
import java.util.*
import kotlin.math.log

class TaskList : Fragment() {

    companion object {
        fun newInstance() = TaskList()
    }

    private lateinit var binding: TaskListViewBinding
    private lateinit var controller : TaskAppController

    private val taskAdapter by lazy {
        TaskAdapter(onClickListener = {
            controller.goDetailsAfterListElementClick(it)
        }, onLongClickListener = {
            controller.askForTaskDeleteConfirmation(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = requireActivity() as TaskAppController
        taskAdapter.addAll(TestDataGenerator.generateData())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TaskListViewBinding.inflate(inflater, container, false).also {
            binding = it
            binding.addTaskButton.setOnClickListener {
                controller.goAddTask()
            }
            binding.recyclerView.setHasFixedSize(false)
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        sortByDeadLine()
        getEndOfTheWeekSummary()
    }

    fun sortByDeadLine() = taskAdapter.sortByDeadLine()

    fun removeTask(id: String) {
        taskAdapter.removeById(id)
        getEndOfTheWeekSummary()
    }

    fun notifyUpdated(position: Int) {
        taskAdapter.notifyItemChanged(position)
    }

    fun createNewTask(task: Task){
        taskAdapter.createNewTask(task)
    }

    fun updateTask(taskDataHolder: Task){
        taskAdapter.updateTask(taskDataHolder)
    }

    //getters
    fun getTaskById(id: String): Task {
        return taskAdapter.getById(id)!!
    }

    fun getEndOfTheWeekSummary() {
        binding.priorityHighTasksNumber.text =
            getNumberOfTasksCurrentWeek(Task.TaskPriority.HIGH).toString()
        binding.priorityLowTasksNumber.text =
            getNumberOfTasksCurrentWeek(Task.TaskPriority.LOW).toString()
        binding.priorityMediumTasksNumber.text =
            getNumberOfTasksCurrentWeek(Task.TaskPriority.MEDIUM).toString()
    }
    fun getName(id: String) = taskAdapter.getNameById(id)

    fun getNumberOfTasksCurrentWeek(priority: Task.TaskPriority): Int {
        return taskAdapter.getNumberOfTasksCurrentWeek(priority)
    }

    fun getPosition(task: Task): Int = taskAdapter.getPosition(task)
}