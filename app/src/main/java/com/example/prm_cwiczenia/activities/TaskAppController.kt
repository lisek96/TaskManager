package com.example.prm_cwiczenia.activities

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.databinding.TaskAppControllerViewBinding
import com.example.prm_cwiczenia.fragments.TaskDeleteConfirmation
import com.example.prm_cwiczenia.fragments.TaskDetail
import com.example.prm_cwiczenia.fragments.TaskList
import com.example.prm_cwiczenia.fragments.TaskUpsert
import com.example.prm_cwiczenia.model.Task
import com.example.prm_cwiczenia.service.TaskService
import java.time.LocalDate
import kotlin.concurrent.thread

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

    fun goAddTask(){
        val bundle = Bundle().apply{
            putString("actionTitle", "Add Task")
            putInt("logo", R.drawable.no_logo)
        }
        val taskUpsert = TaskUpsert().apply {
            arguments = bundle
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, taskUpsert, TaskUpsert.javaClass.name)
            .addToBackStack(TaskUpsert.javaClass.name)
            .commit()
    }

    fun goEditTask(id: String){
        val task = taskList.getTaskById(id)
        println(task)
        val bundle = taskService.createBundleFromTask(task).apply{
            putString("actionTitle", "Edit Task")
        }
        val taskUpsert = TaskUpsert().apply {
            arguments = bundle
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, taskUpsert, TaskUpsert.javaClass.name)
            .addToBackStack(TaskUpsert.javaClass.name)
            .commit()
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

    fun createNewTask(taskName: String, taskPriority: Task.TaskPriority, deadline: LocalDate, logoId: Int){
        taskList.createNewTask(Task(taskName, taskPriority, deadline, 0, logoId))
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, taskList, TaskList.javaClass.name)
            .commit()
        val messageText = if(deadline >= LocalDate.now()) {"Task: $taskName created successfully!" } else { "Past Task cannot be added!" }
        showToast(messageText)
    }

    fun updateExistingTask(taskDataHolder: Task){
        taskList.updateTask(taskDataHolder)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, taskList, TaskList.javaClass.name)
            .commit()
        showToast("Task: ${taskDataHolder.name} updated successfully!")
    }

    fun removeTask(taskId: String){
        taskList.removeTask(taskId)
    }

    private fun showToast(messageText: String){
        val duration = Toast.LENGTH_LONG
        var toast = Toast.makeText(applicationContext, messageText, duration)
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show()
    }
}