package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_workout_exercise.*

class InputWorkoutExerciseActivity : AppCompatActivity() {

    companion object {
        val EXERCISE_OBJECT = "EXERCISE_OBJECT"
        val WORKOUT_EXERCISE_KEY = "WORKOUT_EXERCISE_KEY"
    }

    val spinnerOptionsArray = arrayOf("Main", "Accessory", "Core", "Cardiovascular")
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_workout_exercise)

        val exerciseDatabaseObject = intent.getParcelableExtra<ExerciseDatabaseObject>(EXERCISE_OBJECT)
        if(exerciseDatabaseObject != null){
            exercise_name_input_workout.text = exerciseDatabaseObject.exerciseName
        }

        val exerciseObject = intent.getParcelableExtra<WorkoutExerciseObject>(AddWorkoutActivity.WORKOUT_EXERCISE_KEY)
        if(exerciseObject != null){
            exercise_name_input_workout.text = exerciseObject.exerciseName
            sets_input_workout.setText(exerciseObject.sets)
            reps_input_workout.setText(exerciseObject.reps)
            rpe_input_workout.setText(exerciseObject.rpe)
            workout_type_spinner_workout.post {
                workout_type_spinner_workout.setSelection(spinnerOptionsArray.indexOf(exerciseObject.type))
            }
        }

        add_exercise_button_workout.setOnClickListener {
            if(exerciseObject != null){
                editExerciseInWorkoutArrayList()
            }
            else{
                addToWorkoutArrayList()
            }
        }

        workout_type_spinner_workout.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerOptionsArray)

        workout_type_spinner_workout.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = spinnerOptionsArray.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        exercise_name_input_workout.setOnClickListener {
            val intent = Intent(this, ChooseWorkoutExerciseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editExerciseInWorkoutArrayList(){
        val workoutArrayList = AddWorkoutActivity.workoutArrayList
        val exercisePosition = intent.getIntExtra(AddWorkoutActivity.WORKOUT_EXERCISE_POSITION, -1)

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_workout.text.toString()
            val sets = sets_input_workout.text.toString()
            val reps = reps_input_workout.text.toString()
            val rpe = rpe_input_workout.text.toString()

            val exerciseDatabaseObject = intent.getParcelableExtra<WorkoutExerciseObject>(WORKOUT_EXERCISE_KEY)

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty() || type.isEmpty() || exerciseDatabaseObject.url.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            workoutArrayList.set(exercisePosition, WorkoutExerciseObject(exercisePosition.toString(), exerciseName, sets, reps, rpe, "", type, exerciseDatabaseObject.url))
            val intent  = Intent(this, AddWorkoutActivity::class.java)
            intent.putParcelableArrayListExtra(AddWorkoutActivity.WORKOUT_ARRAY_LIST, workoutArrayList)
            startActivity(intent)
            finish()
        }
    }//editExerciseInWorkoutArrayList function

    private fun addToWorkoutArrayList(){
        val workoutArrayList = AddWorkoutActivity.workoutArrayList

        val exerciseName = exercise_name_input_workout.text.toString()
        val sets = sets_input_workout.text.toString()
        val reps = reps_input_workout.text.toString()
        val rpe = rpe_input_workout.text.toString()

        val exerciseDatabaseObject = intent.getParcelableExtra<ExerciseDatabaseObject>(EXERCISE_OBJECT)

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty() || type.isEmpty() || exerciseDatabaseObject == null){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        workoutArrayList.add(WorkoutExerciseObject(workoutArrayList.size.toString(), exerciseName, sets, reps, rpe, "", type, exerciseDatabaseObject.videoUrl))
        val intent  = Intent(this, AddWorkoutActivity::class.java)
        intent.putParcelableArrayListExtra(AddWorkoutActivity.WORKOUT_ARRAY_LIST, workoutArrayList)
        startActivity(intent)
        finish()
    }//pushData function
}
