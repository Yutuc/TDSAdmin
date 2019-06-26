package com.univation.tdsadmin.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.MainExerciseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_exercise_row.view.*

class UserWorkoutExerciseRow(val context: Context, val inflater: LayoutInflater, val key: String, val workoutExerciseObject: MainExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        /*viewHolder.itemView.exercise_name_textview_workout.text = workoutExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_workout.text = workoutExerciseObject.sets
        viewHolder.itemView.reps_textview_workout.text = workoutExerciseObject.reps
        viewHolder.itemView.rpe_textview_workout.text = workoutExerciseObject.rpe

        if(workoutExerciseObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_workout.text = "lbs"
        }
        else{
            viewHolder.itemView.weight_input_workout.text = workoutExerciseObject.weight
        }

        viewHolder.itemView.weight_input_workout.paintFlags = Paint.UNDERLINE_TEXT_FLAG*/
    }

    override fun getLayout(): Int {
        return R.layout.workout_exercise_row
    }
}