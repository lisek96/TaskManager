package com.example.prm_cwiczenia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.activities.TaskAppController
import com.example.prm_cwiczenia.databinding.TaskDetailViewBinding
import com.example.prm_cwiczenia.databinding.TaskUpsertViewBinding

class TaskUpsert : Fragment() {
    private lateinit var binding: TaskUpsertViewBinding

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
        return TaskUpsertViewBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}