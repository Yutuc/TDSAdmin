package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_warmup_exercise.*

class InputWarmupExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_warmup_exercise)

        val exerciseObject = intent.getParcelableExtra<WarmupExerciseObject>(AddWorkoutActivity.WARMUP_EXERCISE_KEY)
        if(exerciseObject != null){
            exercise_name_input_warmup.setText(exerciseObject.exerciseName)
            sets_input_warmup.setText(exerciseObject.sets)
            reps_input_warmup.setText(exerciseObject.reps)
            rpe_input_warmup.setText(exerciseObject.rpe)
        }

        add_exercise_button_warmup.setOnClickListener {
            if(exerciseObject != null){
                editExerciseInWarmupArrayList()
            }
            else{
                addWarmupToArrayList()
            }
        }
    }

    private fun editExerciseInWarmupArrayList(){
        val warmupArrayList = intent.getParcelableArrayListExtra<WarmupExerciseObject>(AddWorkoutActivity.WARMUP_ARRAY_LIST)
        val exercisePosition = intent.getIntExtra(AddWorkoutActivity.WARMUP_EXERCISE_POSITION, -1)
        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_warmup.text.toString()
            val sets = sets_input_warmup.text.toString()
            val reps = reps_input_warmup.text.toString()
            val rpe = rpe_input_warmup.text.toString()

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            warmupArrayList.set(exercisePosition, WarmupExerciseObject(exerciseName, sets, reps, rpe))
            val intent  = Intent(this, AddWorkoutActivity::class.java)
            intent.putParcelableArrayListExtra(AddWorkoutActivity.WARMUP_ARRAY_LIST, warmupArrayList)
            startActivity(intent)
            finish()
        }
    }//editExerciseInWarmupArrayList function

    private fun addWarmupToArrayList(){
        val warmupArrayList = intent.getParcelableArrayListExtra<WarmupExerciseObject>(AddWorkoutActivity.WARMUP_ARRAY_LIST)

        val exerciseName = exercise_name_input_warmup.text.toString()
        val sets = sets_input_warmup.text.toString()
        val reps = reps_input_warmup.text.toString()
        val rpe = rpe_input_warmup.text.toString()

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty()){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        warmupArrayList.add(WarmupExerciseObject(exerciseName, sets, reps, rpe))
        val intent  = Intent(this, AddWorkoutActivity::class.java)
        intent.putParcelableArrayListExtra(AddWorkoutActivity.WARMUP_ARRAY_LIST, warmupArrayList)
        startActivity(intent)
        finish()
    }//addWarmupToArrayList function
}
