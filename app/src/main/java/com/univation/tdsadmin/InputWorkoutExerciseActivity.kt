package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_input_workout_exercise.*

class InputWorkoutExerciseActivity : AppCompatActivity() {

    companion object {
        val USER_KEY = "USER_KEY"
        val WORKOUT_ARRAY_LIST = "WORKOUT_ARRAY_LIST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_workout_exercise)

        add_exercise_button_workout.setOnClickListener {
            addToWorkoutArrayList()
        }
    }

    private fun addToWorkoutArrayList(){
        val userChosen = intent.getParcelableExtra<UserObject>(AddWorkoutActivity.USER_KEY)
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
        intent.putExtra(USER_KEY, userChosen)
        intent.putParcelableArrayListExtra(WORKOUT_ARRAY_LIST, workoutArrayList)
        startActivity(intent)
        finish()
    }//pushData function
}
