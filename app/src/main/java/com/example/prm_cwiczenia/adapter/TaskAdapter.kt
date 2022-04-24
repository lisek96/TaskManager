package com.example.prm_cwiczenia.adapter

import android.os.Build
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_cwiczenia.databinding.TaskItemViewBinding
import com.example.prm_cwiczenia.model.Task
import java.time.LocalDate
import java.time.temporal.ChronoField

class TaskAdapter(
    val onClickListener: (String) -> Unit,
    val onLongClickListener: (String) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {
    private var tasks = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            TaskItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding).also { holder ->
            binding.root.setOnClickListener {
                onClickListener.invoke(binding.recyclerViewItemId.text.toString())
            }
            binding.root.setOnLongClickListener {
                onLongClickListener.invoke(binding.recyclerViewItemId.text.toString())
                true
            }
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    fun getNameById(id: String): String = getById(id)?.name ?: ""

    fun removeById(id: String) {
        var taskToRemove = getById(id)
        var position = tasks.indexOf(taskToRemove)
        tasks.remove(taskToRemove)
        notifyItemRemoved(position)
    }

    fun getById(id: String): Task? = tasks.find { task -> task.taskId == id }

    fun sortByDeadLine() = tasks.sortBy { task -> task.deadLine }

    fun getPosition(task: Task) = tasks.indexOf(task)

    fun addAll(tasksList: List<Task>) = tasks.addAll(tasksList)

    fun updateTask(taskDataHolder: Task){
        val updated = getById(taskDataHolder.taskId)!!
        updated.name = taskDataHolder.name
        updated.deadLine = taskDataHolder.deadLine
        updated.priority = taskDataHolder.priority
        updated.logoDrawableId = taskDataHolder.logoDrawableId
        val position = getPosition(updated)
        notifyItemChanged(position)
    }

    fun createNewTask(task: Task){
        if(task.deadLine < LocalDate.now()){
            return
        }
        tasks.add(task)
        notifyItemInserted(tasks.size-1)
    }

    fun getNumberOfTasksCurrentWeek(priority: Task.TaskPriority): Int = tasks
        .filter { task -> task.priority == priority && isDateCurrentWeek(task.deadLine) }
        .count()

    fun isDateCurrentWeek(checkedDate: LocalDate): Boolean =
        LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR) == checkedDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR)
}