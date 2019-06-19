package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_warmup_exercise.*

class InputWarmupExerciseActivity : AppCompatActivity() {

    companion object {
        val EXERCISE_OBJECT = "EXERCISE_OBJECT"
        val WARMUP_EXERCISE_KEY = "WARMUP_EXERCISE_KEY"
    }

    val spinnerOptionsArray = arrayOf("Main", "Accessory", "Core", "Cardiovascular")
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_warmup_exercise)

        val exerciseDatabaseObject = intent.getParcelableExtra<ExerciseDatabaseObject>(EXERCISE_OBJECT)
        if(exerciseDatabaseObject != null){
            exercise_name_input_warmup.text = exerciseDatabaseObject.exerciseName
        }

        val exerciseObject = intent.getParcelableExtra<WarmupExerciseObject>(AddWorkoutActivity.WARMUP_EXERCISE_KEY)
        if(exerciseObject != null){
            exercise_name_input_warmup.text = exerciseObject.exerciseName
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

        warmup_type_spinner_warmup.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerOptionsArray)

        warmup_type_spinner_warmup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = spinnerOptionsArray.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //when nothing is selected yet
            }

        }

        exercise_name_input_warmup.setOnClickListener {
            val intent = Intent(this, ChooseWarmupExerciseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editExerciseInWarmupArrayList(){
        val warmupArrayList = AddWorkoutActivity.warmupArrayList
        val exercisePosition = intent.getIntExtra(AddWorkoutActivity.WARMUP_EXERCISE_POSITION, -1)

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_warmup.text.toString()
            val sets = sets_input_warmup.text.toString()
            val reps = reps_input_warmup.text.toString()
            val rpe = rpe_input_warmup.text.toString()

            val exerciseDatabaseObject = intent.getParcelableExtra<WarmupExerciseObject>(WARMUP_EXERCISE_KEY)

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty() || exerciseDatabaseObject.url.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            warmupArrayList.set(exercisePosition, WarmupExerciseObject(exerciseName, sets, reps, rpe, type, exerciseDatabaseObject.url))
            val intent  = Intent(this, AddWorkoutActivity::class.java)
            intent.putParcelableArrayListExtra(AddWorkoutActivity.WARMUP_ARRAY_LIST, warmupArrayList)
            startActivity(intent)
            finish()
        }
    }//editExerciseInWarmupArrayList function

    private fun addWarmupToArrayList(){
        val warmupArrayList = AddWorkoutActivity.warmupArrayList

        val exerciseName = exercise_name_input_warmup.text.toString()
        val sets = sets_input_warmup.text.toString()
        val reps = reps_input_warmup.text.toString()
        val rpe = rpe_input_warmup.text.toString()

        val exerciseDatabaseObject = intent.getParcelableExtra<ExerciseDatabaseObject>(EXERCISE_OBJECT)

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty() || exerciseDatabaseObject == null){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        warmupArrayList.add(WarmupExerciseObject(exerciseName, sets, reps, rpe, type, exerciseDatabaseObject.videoUrl))
        val intent  = Intent(this, AddWorkoutActivity::class.java)
        intent.putParcelableArrayListExtra(AddWorkoutActivity.WARMUP_ARRAY_LIST, warmupArrayList)
        startActivity(intent)
        finish()
    }//addWarmupToArrayList function
}
