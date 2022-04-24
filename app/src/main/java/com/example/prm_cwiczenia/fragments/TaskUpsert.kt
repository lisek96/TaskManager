package com.example.prm_cwiczenia.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.activities.TaskAppController
import com.example.prm_cwiczenia.databinding.TaskUpsertViewBinding
import com.example.prm_cwiczenia.model.Task
import java.time.LocalDate

class TaskUpsert : Fragment() {
    private lateinit var binding: TaskUpsertViewBinding
    private lateinit var controller: TaskAppController
    private var currentLogoPage = 0
    private val logosArray = arrayOf(
        intArrayOf(R.drawable.doctor, R.drawable.home, R.drawable.pjwstk),
        intArrayOf(R.drawable.work, R.drawable.date, R.drawable.shopping),
        intArrayOf(R.drawable.blood_donation, R.drawable.bills, R.drawable.cleaning)
    )

    companion object {
        fun newInstance() = TaskDetail()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = requireActivity() as TaskAppController
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TaskUpsertViewBinding.inflate(inflater, container, false).also {
            binding = it
            currentLogoPage = 0
            setupArrowsThatChangeLogosToPick()
            setLogosOnClickListeners()
            setupPrioritySpinner()
            setupActionTitle()
            setLogos()
            bindArgumentsToViewElements()
        }.root
    }

    private fun bindArgumentsToViewElements(){
        binding.upsertTaskIdHolder.text = arguments?.getString("id") ?: ""
        binding.taskLogo.setImageResource(arguments?.getInt("logo").toString().toInt())
        binding.taskLogo.tag = arguments?.getInt("logo").toString().toInt()
        arguments?.getString("deadline")?.apply {
            val dateArray = this.split("-")
            setDueDate(dateArray[0].toInt(), dateArray[1].toInt(), dateArray[2].toInt())
        }
        binding.taskName.text =
            Editable.Factory.getInstance().newEditable(arguments?.getString("name") ?: "")
        datePickerOpeningAfterDateClick()
    }

    private fun setupArrowsThatChangeLogosToPick() {
        binding.next.apply {
            setImageResource(R.drawable.right_arrow)
            setOnClickListener {
                if (currentLogoPage != logosArray.size - 1) {
                    currentLogoPage++
                    setLogos()
                }
            }
        }
        binding.back.apply {
            setImageResource(R.drawable.left_arrow)
            setOnClickListener {
                if (currentLogoPage != 0) {
                    currentLogoPage--
                    setLogos()
                }
            }
        }
    }

    private fun setupActionTitle(){
        arguments?.getString("actionTitle").apply {
            setupUpsertButton(this!!)
            binding.actionTitle.text = this
        }
    }

    private fun setupUpsertButton(actionTitle: String){
        if (actionTitle == "Add Task") {
            setAddOnClickListener()
            binding.upsertButton.setImageResource(R.drawable.ic_baseline_add_circle_24)
        } else {
            setEditOnClickListener()
            binding.upsertButton.setImageResource(R.drawable.ic_baseline_edit_24)
        }
    }

    private fun setEditOnClickListener() {
        binding.upsertButton.setOnClickListener { applyEditTask() }
    }

    private fun applyEditTask() {
        controller.updateExistingTask(createTaskFromViewData())
    }

    private fun createTaskFromViewData(): Task {
        val taskId = binding.upsertTaskIdHolder.text.toString()
        val taskName = binding.taskName.text.toString()
        val taskPriority = determineTaskPriority(binding.prioritySpinner.selectedItem.toString())
        val taskDeadline = determineUpsertedTaskDeadline();
        val taskLogoId = binding.taskLogo.tag.toString().toInt()
        return Task(taskName, taskPriority!!, taskDeadline, 0, taskLogoId).apply {
            this.taskId = taskId
            println(this)
        }
    }

    private fun setLogos() {
        binding.logo1.apply {
            setImageResource(logosArray[currentLogoPage][0])
            setTag((logosArray[currentLogoPage][0]))
        }
        binding.logo2.apply {
            setImageResource(logosArray[currentLogoPage][1])
            setTag(logosArray[currentLogoPage][1])
        }
        binding.logo3.apply {
            setImageResource(logosArray[currentLogoPage][2])
            setTag(logosArray[currentLogoPage][2])
        }
    }

    private fun datePickerOpeningAfterDateClick() {
        binding.taskDeadline.setOnClickListener {
            val datePickerFragment = DatePickerFragment(this).apply {
                arguments = Bundle().apply {
                    if(binding.taskDeadline.text != "") {
                        val deadline = binding.taskDeadline.text.split('/')
                        putInt("year", deadline[2].toInt())
                        putInt("month", deadline[1].toInt()-1)
                        putInt("day", deadline[0].toInt())
                    }
                }
            }
            datePickerFragment.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }

    private fun setLogosOnClickListeners() {
        listOf(binding.logo1, binding.logo2, binding.logo3)
            .forEach { logo ->
                logo.setOnClickListener {
                    binding.taskLogo.setImageResource(logo.tag.toString().toInt())
                    binding.taskLogo.tag = logo.tag
                }
            }
    }

    private fun setAddOnClickListener() {
        binding.upsertButton.setOnClickListener {
            val taskName = binding.taskName.text.toString()
            val taskPriority =
                determineTaskPriority(binding.prioritySpinner.selectedItem.toString())
            var deadLine: LocalDate = determineUpsertedTaskDeadline()
            controller.createNewTask(
                taskName,
                taskPriority!!,
                deadLine,
                binding.taskLogo.tag.toString().toInt()
            )
        }
    }

    private fun determineUpsertedTaskDeadline(): LocalDate {
        if (binding.taskDeadline.text.toString() != "") {
            val deadLineArray = binding.taskDeadline.text.toString().split("/")
            return LocalDate.of(
                deadLineArray[2].toInt(),
                deadLineArray[1].toInt(),
                deadLineArray[0].toInt()
            )
        }
        return LocalDate.now()
    }

    private fun determineTaskPriority(priority: String): Task.TaskPriority? {
        val mapOfPriority = mapOf(
            "HIGH" to Task.TaskPriority.HIGH,
            "MEDIUM" to Task.TaskPriority.MEDIUM,
            "LOW" to Task.TaskPriority.LOW
        )
        return mapOfPriority[priority]
    }

    fun setDueDate(year: Int, month: Int, day: Int) {
        binding.taskDeadline.text = """$day/$month/$year"""
    }

    private fun setupPrioritySpinner() {
        val spinner = binding.prioritySpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_array,
            R.layout.spinner_style
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            val mapOfPriority = mapOf(
                "HIGH" to 2,
                "MEDIUM" to 1,
                "LOW" to 0
            )
            spinner.setSelection(mapOfPriority.get(arguments?.getString("priority"))?:0)
        }
    }
}