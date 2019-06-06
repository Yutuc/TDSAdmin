package com.univation.tdsadmin

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_exercise_row.view.*

class UserWorkoutExerciseRow(val context: Context, val inflater: LayoutInflater, val key: String, val workoutExerciseObject: WorkoutExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_workout.text = workoutExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_workout.text = workoutExerciseObject.sets
        viewHolder.itemView.reps_textview_workout.text = workoutExerciseObject.reps
        viewHolder.itemView.rpe_textview_workout.text = workoutExerciseObject.rpe

        if(workoutExerciseObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_workout.text = "lbs"
        }
        else{
            viewHolder.itemView.weight_input_workout.text = workoutExerciseObject.weight
        }

        viewHolder.itemView.weight_input_workout.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        /*viewHolder.itemView.weight_input_workout.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            val dialogView = inflater.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Weight"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("lbs")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val weightInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(weightInput.isEmpty()) {
                    Toast.makeText(context, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val currentUser = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/workout-page/$currentUser/$key/workoutExercises/${workoutExerciseObject.position}")
                    ref.child("weight").setValue(weightInput)
                    viewHolder.itemView.weight_input_workout.text = weightInput
                    alertDialog.dismiss()
                }
            }
        }*/
    }

    override fun getLayout(): Int {
        return R.layout.workout_exercise_row
    }
}