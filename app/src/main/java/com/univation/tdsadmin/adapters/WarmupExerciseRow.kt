package com.univation.tdsadmin.adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.WarmupExerciseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_exercise_row.view.*

class WarmupExerciseRow(val exerciseObject: WarmupExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val exerciseName = exerciseObject.exerciseName
        val sets = exerciseObject.sets
        val reps = exerciseObject.reps

        viewHolder.itemView.exercise_name_textview.text = exerciseName
        viewHolder.itemView.sets_textview.text = sets
        viewHolder.itemView.reps_textview.text = reps
    }

    override fun getLayout(): Int {
        return R.layout.warmup_exercise_row
    }
}