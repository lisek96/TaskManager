package com.example.prm_cwiczenia.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.databinding.TaskAppControllerViewBinding
import com.example.prm_cwiczenia.fragments.TaskDeleteConfirmation
import com.example.prm_cwiczenia.fragments.TaskDetail
import com.example.prm_cwiczenia.fragments.TaskList
import com.example.prm_cwiczenia.model.Task
import com.example.prm_cwiczenia.service.TaskService

class TaskAppController : AppCompatActivity() {

    private val binding by lazy { TaskAppControllerViewBinding.inflate(layoutInflater) }
    private var taskList: TaskList = TaskList()
    private val taskService : TaskService = TaskService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showTasksList()
    }

    private fun showTasksList(){
        supportFragmentManager.beginTransaction()
            .add(R.id.container, taskList, TaskList.javaClass.name)
            .commit()
    }

    fun editTask(position: Int){
        val task = taskList.getTask(position)

    }

    fun goDetailsAfterListElementClick(id: String) {
        val task: Task = taskList.getTaskById(id)
        val taskDetail = TaskDetail().apply {
            arguments = taskService.createBundleFromTask(task)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, taskDetail, TaskDetail.javaClass.name)
            .addToBackStack(TaskDetail.javaClass.name)
            .commit()
    }

    fun askForTaskDeleteConfirmation(id: String){
        val bundle = Bundle().apply{
            putString("taskName", taskList.getName(id))
            putString("taskId", id)
        }
        TaskDeleteConfirmation().apply { arguments = bundle }
            .show(supportFragmentManager, TaskDeleteConfirmation.javaClass.name)
    }

    fun updateTaskProgress(taskId: String, progress: Int){
        var task = taskList.getTaskById(taskId)
        task.progress = progress
        taskList.notifyUpdated(taskList.getPosition(task))
    }

    fun removeTask(taskId: String){
        taskList.removeTask(taskId)
    }
}