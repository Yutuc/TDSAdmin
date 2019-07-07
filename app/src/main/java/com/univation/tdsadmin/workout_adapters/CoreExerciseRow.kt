package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.graphics.Paint
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.CoreExerciseObject
import com.univation.tdsadmin.viewcheckins.ChooseWeekActivity
import com.univation.tdsadmin.viewcheckins.ChooseBlockFragment
import com.univation.tdsadmin.viewcheckins.ViewWorkoutWeekActivityForCheckIns
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.core_exercise_row_check_ins.view.*

class CoreExerciseRow (val key: String, val coreExerciseObject: CoreExerciseObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_core.text = coreExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_core.text = coreExerciseObject.sets
        viewHolder.itemView.reps_textview_core.text = coreExerciseObject.reps
        if(coreExerciseObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_core.text = "lbs"
        }
        else{
            viewHolder.itemView.weight_input_core.text = coreExerciseObject.weight
        }
        viewHolder.itemView.weight_input_core.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
    override fun getLayout(): Int {
        return R.layout.core_exercise_row_check_ins
    }
}