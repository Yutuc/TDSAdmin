package com.univation.tdsadmin.workout_adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ConditioningExerciseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.conditioning_exercise_row_check_ins.view.*

class ConditioningExerciseRow (val conditioningExerciseObject: ConditioningExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_conditioning.text = conditioningExerciseObject.exerciseName
        viewHolder.itemView.seconds_textview_conditioning.text = conditioningExerciseObject.seconds
        viewHolder.itemView.minutes_textview_conditioning.text = conditioningExerciseObject.minutes
    }

    override fun getLayout(): Int {
        return R.layout.conditioning_exercise_row_check_ins
    }
}