package com.example.prm_cwiczenia.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.prm_cwiczenia.activities.TaskAppController
import java.util.*

class DatePickerFragment(private val taskUpsert: TaskUpsert) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        arguments?.apply {
            if (getInt("year") > 0) {
                year = getInt("year")
            }
            if (getInt("month") > 0) {
                month = getInt("month")
            }
            if (getInt("day") > 0) {
                day = getInt("day")
            }
        }
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        taskUpsert.setDueDate(year, month + 1, day)
    }

}