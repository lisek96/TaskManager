package com.example.prm_cwiczenia.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.databinding.TaskAppControllerViewBinding
import com.example.prm_cwiczenia.fragments.TaskDeleteConfirmation
import com.example.prm_cwiczenia.fragments.TaskDetail
import com.example.prm_cwiczenia.fragments.TaskList
import com.example.prm_cwiczenia.model.Task

class TaskAppController : AppCompatActivity() {

    private val binding by lazy { TaskAppControllerViewBinding.inflate(layoutInflater) }
    private var taskList: TaskList = TaskList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, taskList, TaskList.javaClass.name)
            .commit()
    }

    fun goDetailsAfterListElementClick(id: String) {
        val bundle = Bundle()
        val task: Task = taskList.getTaskById(id)
        bundle.putString("name", task.name)
        bundle.putString("progress", task.progress.toString())
        bundle.putString("priority", task.priority.toString())
        bundle.putString("deadline", task.deadLine.toString())
        bundle.putString("logo", task.logoDrawableId.toString())
        bundle.putString("id", task.taskId)
        val taskDetail = TaskDetail().apply {
            arguments = bundle
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