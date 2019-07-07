package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.graphics.Paint
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.MainExerciseObject
import com.univation.tdsadmin.viewcheckins.ChooseWeekActivity
import com.univation.tdsadmin.viewcheckins.ChooseBlockFragment
import com.univation.tdsadmin.viewcheckins.ViewWorkoutWeekActivityForCheckIns
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_exercise_row_check_in.view.*

class MainExerciseRow(val key: String, val mainExerciseObject : MainExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_workout.text = mainExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_workout.text = mainExerciseObject.sets
        viewHolder.itemView.reps_textview_workout.text = mainExerciseObject.reps
        viewHolder.itemView.rpe_textview_workout.text = mainExerciseObject.rpe

        if(mainExerciseObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_workout.text = "lbs"
        }
        else{
            viewHolder.itemView.weight_input_workout.text = mainExerciseObject.weight
        }

        viewHolder.itemView.weight_input_workout.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun getLayout(): Int {
        return R.layout.main_exercise_row_check_in
    }
}