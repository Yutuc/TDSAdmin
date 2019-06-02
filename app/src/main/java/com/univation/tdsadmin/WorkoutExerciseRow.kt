package com.univation.tdsadmin

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_and_warmup_exercise_row.view.*

class WorkoutExerciseRow(val exerciseObject: WorkoutExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val exerciseName = exerciseObject.exerciseName
        val sets = exerciseObject.sets
        val reps = exerciseObject.reps
        val rpe = exerciseObject.rpe

        viewHolder.itemView.exercise_name_textview.text = exerciseName
        viewHolder.itemView.sets_textview.text = sets
        viewHolder.itemView.reps_textview.text = reps
        viewHolder.itemView.rpe_textview.text = rpe
    }

    override fun getLayout(): Int {
        return R.layout.workout_and_warmup_exercise_row
    }
}