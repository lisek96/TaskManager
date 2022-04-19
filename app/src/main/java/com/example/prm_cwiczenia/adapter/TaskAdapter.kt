package com.example.prm_cwiczenia.adapter

import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_cwiczenia.databinding.TaskItemViewBinding
import com.example.prm_cwiczenia.model.Task
import java.time.LocalDate
import java.time.temporal.ChronoField

class TaskAdapter(val onClickListener : (String) -> Unit,
            val onLongClickListener: (String) -> Unit): RecyclerView.Adapter<TaskViewHolder>() {
    private val handler = HandlerCompat.createAsync(Looper.getMainLooper())
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
        tasks[position].taskViewHolder = holder
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    fun add(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun remove(position: Int) {
        if (position >= 0 && position < tasks.size) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getNameById(id: String) : String {
        return getById(id)?.name ?: ""
    }

    fun removeById(id: String) {
        var taskToRemove = getById(id)
        var position = tasks.indexOf(taskToRemove)
        tasks.remove(taskToRemove)
        notifyItemRemoved(position)
    }

    fun getById(id: String) : Task?{
        return tasks.find { task -> task.taskId == id }
    }

    fun getPosition(task: Task) = tasks.indexOf(task)

    fun getTasks() : List<Task> = tasks

    fun getNumberOfTasksCurrentWeek(priority: Task.TaskPriority) : Int {
        return tasks.filter{
            task -> task.priority == priority && isDateCurrentWeek(task.deadLine)
        }.count()
    }

    fun isDateCurrentWeek(checkedDate: LocalDate) : Boolean {
        return LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR) == checkedDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR)
    }
}