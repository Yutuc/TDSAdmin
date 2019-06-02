package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_warmup_exercise.*

class InputWarmupExerciseActivity : AppCompatActivity() {

    companion object {
        val USER_KEY = "USER_KEY"
        val WARMUP_ARRAY_LIST = "WARMUP_ARRAY_LIST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_warmup_exercise)

        add_exercise_button_warmup.setOnClickListener {
            addWarmupToArrayList()
        }
    }

    private fun addWarmupToArrayList(){
        val userChosen = intent.getParcelableExtra<UserObject>(AddWorkoutActivity.USER_KEY)
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
        intent.putExtra(USER_KEY, userChosen)
        intent.putParcelableArrayListExtra(WARMUP_ARRAY_LIST, warmupArrayList)
        startActivity(intent)
        finish()
    }//addWarmupToArrayList function
}
