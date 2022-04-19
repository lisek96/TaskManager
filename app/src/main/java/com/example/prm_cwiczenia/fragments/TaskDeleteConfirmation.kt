package com.example.prm_cwiczenia.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.prm_cwiczenia.R
import com.example.prm_cwiczenia.activities.TaskAppController

class TaskDeleteConfirmation : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Delete Task:\t${arguments?.getString("taskName")}?")
            .setPositiveButton("Yes", { _, _ ->
                removeTask(arguments?.getString("taskId").toString()) })
            .setNegativeButton("No", { _, _ ->  })
            .create()

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }

    fun removeTask(id: String){
        var controller = requireActivity() as TaskAppController
        controller.removeTask(id)
    }
}