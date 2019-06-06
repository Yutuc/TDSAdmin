package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_input_workout_exercise.*

class InputWorkoutExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_workout_exercise)

        val exerciseObject = intent.getParcelableExtra<WorkoutExerciseObject>(AddWorkoutActivity.WORKOUT_EXERCISE_KEY)
        if(exerciseObject != null){
            exercise_name_input_workout.setText(exerciseObject.exerciseName)
            sets_input_workout.setText(exerciseObject.sets)
            reps_input_workout.setText(exerciseObject.reps)
            rpe_input_workout.setText(exerciseObject.rpe)
        }

        add_exercise_button_workout.setOnClickListener {
            if(exerciseObject != null){
                editExerciseInWorkoutArrayList()
            }
            else{
                addToWorkoutArrayList()
            }
        }
    }

    private fun editExerciseInWorkoutArrayList(){
        val workoutArrayList = intent.getParcelableArrayListExtra<WorkoutExerciseObject>(AddWorkoutActivity.WORKOUT_ARRAY_LIST)
        val exercisePosition = intent.getIntExtra(AddWorkoutActivity.WORKOUT_EXERCISE_POSITION, -1)

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_workout.text.toString()
            val sets = sets_input_workout.text.toString()
            val reps = reps_input_workout.text.toString()
            val rpe = rpe_input_workout.text.toString()

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            workoutArrayList.set(exercisePosition, WorkoutExerciseObject(workoutArrayList.size.toString(), exerciseName, sets, reps, rpe, ""))
            val intent  = Intent(this, AddWorkoutActivity::class.java)
            intent.putParcelableArrayListExtra(AddWorkoutActivity.WORKOUT_ARRAY_LIST, workoutArrayList)
            startActivity(intent)
            finish()
        }
    }//editExerciseInWorkoutArrayList function

    private fun addToWorkoutArrayList(){
        val workoutArrayList = intent.getParcelableArrayListExtra<WorkoutExerciseObject>(AddWorkoutActivity.WORKOUT_ARRAY_LIST)

        val exerciseName = exercise_name_input_workout.text.toString()
        val sets = sets_input_workout.text.toString()
        val reps = reps_input_workout.text.toString()
        val rpe = rpe_input_workout.text.toString()

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty()){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        workoutArrayList.add(WorkoutExerciseObject(workoutArrayList.size.toString(), exerciseName, sets, reps, rpe, ""))
        val intent  = Intent(this, AddWorkoutActivity::class.java)
        intent.putParcelableArrayListExtra(AddWorkoutActivity.WORKOUT_ARRAY_LIST, workoutArrayList)
        startActivity(intent)
        finish()
    }//pushData function
}
