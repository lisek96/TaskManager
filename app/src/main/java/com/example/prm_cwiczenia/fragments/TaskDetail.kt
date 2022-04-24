package com.example.prm_cwiczenia.fragments

import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.prm_cwiczenia.activities.TaskAppController
import com.example.prm_cwiczenia.databinding.TaskDetailViewBinding
import com.example.prm_cwiczenia.model.Task

class TaskDetail : Fragment() {

    private lateinit var binding: TaskDetailViewBinding
    private lateinit var controller: TaskAppController

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
        return TaskDetailViewBinding.inflate(inflater, container, false).also {
            binding = it
            bindChosenTaskDetailsToView()
            prepareProgressSeekBar()
            binding.editTaskButton.setOnClickListener{
                goEditTask()
            }
        }.root
    }

    fun goEditTask(){
        val id = binding.taskId.text.toString()
        controller.goEditTask(id)
    }

    fun bindChosenTaskDetailsToView() {
        arguments?.apply {
            binding.taskDetailDeadline.text = get("deadline").toString()
            binding.taskDetailLogo.setImageResource(get("logo").toString().toInt())
            binding.taskDetailPriority.text = get("priority").toString()
            binding.taskDetailProgress.progress = get("progress").toString().toInt()
            binding.taskDetailName.text = get("name").toString()
            binding.taskDetailProgressChart.setPercentage(get("progress").toString().toInt())
            binding.taskId.text = get("id").toString()
        }
    }

    fun prepareProgressSeekBar(){
        binding.taskDetailProgress.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                binding.taskDetailProgressChart.setPercentage(progress)
                binding.taskDetailProgressChart.invalidate()
                (activity as TaskAppController).apply {
                    println(arguments?.get("id").toString())
                    updateTaskProgress(arguments?.get("id").toString(), progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?){}
            override fun onStopTrackingTouch(p0: SeekBar?){}
        })
    }
}