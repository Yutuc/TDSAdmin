package com.univation.tdsadmin.adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ConditioningExerciseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.conditioning_exercise_row.view.*
import kotlinx.android.synthetic.main.workout_exercise_row.view.exercise_name_textview

class ConditioningExerciseRow(val exerciseObject: ConditioningExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val exerciseName = exerciseObject.exerciseName
        val minutes = exerciseObject.minutes
        val seconds = exerciseObject.seconds

        viewHolder.itemView.exercise_name_textview.text = exerciseName
        viewHolder.itemView.minutes_textview.setText(minutes)
        viewHolder.itemView.seconds_textview.setText(seconds)
    }

    override fun getLayout(): Int {
        return R.layout.conditioning_exercise_row
    }
}