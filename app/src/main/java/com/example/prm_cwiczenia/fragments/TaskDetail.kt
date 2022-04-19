package com.example.prm_cwiczenia.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.prm_cwiczenia.activities.TaskAppController
import com.example.prm_cwiczenia.databinding.TaskDetailViewBinding

class TaskDetail : Fragment() {

    private lateinit var binding: TaskDetailViewBinding

    companion object {
        fun newInstance() = TaskDetail()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TaskDetailViewBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.taskDetailDeadline.text = arguments?.get("deadline").toString()
        binding.taskDetailLogo.setImageResource(arguments?.get("logo").toString().toInt())
        binding.taskDetailPriority.text = arguments?.get("priority").toString()
        binding.taskDetailProgress.progress = arguments?.get("progress").toString().toInt()
        binding.taskDetailName.text = arguments?.get("name").toString()
        binding.taskDetailProgressChart.setPercentage(arguments?.get("progress").toString().toInt())
        binding.listPosition.text = arguments?.get("id").toString()
        binding.taskDetailProgress.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                binding.taskDetailProgressChart.setPercentage(progress)
                binding.taskDetailProgressChart.invalidate()
                (activity as TaskAppController).apply{
                    println(arguments?.get("id").toString())
                    updateTaskProgress(arguments?.get("id").toString(), progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }
}