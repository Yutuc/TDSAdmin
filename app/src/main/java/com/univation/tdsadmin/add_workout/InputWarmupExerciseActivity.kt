package com.univation.tdsadmin.add_workout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.objects.WarmupExerciseObject
import kotlinx.android.synthetic.main.activity_input_warmup_exercise.*

class InputWarmupExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_warmup_exercise)

        val warmupExerciseEdit = AddWeekToBlockActivity.warmupExerciseEdit
        val exerciseDatabaseObject = ChooseWarmupExerciseActivity.warmupExerciseClicked

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_input_warmup.text = exerciseDatabaseObject.exerciseName
        }

        //if user wants to edit an exercise
        if(warmupExerciseEdit != null){
            if(ChooseWarmupExerciseActivity.warmupExerciseClicked == null){
                ChooseWarmupExerciseActivity.warmupExerciseClicked = ExerciseDatabaseObject(warmupExerciseEdit.exerciseName, warmupExerciseEdit.url)
                exercise_name_input_warmup.text = warmupExerciseEdit.exerciseName
            }
            else if(ChooseWarmupExerciseActivity.warmupExerciseClicked?.exerciseName != warmupExerciseEdit.exerciseName){
                exercise_name_input_warmup.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                exercise_name_input_warmup.text = warmupExerciseEdit.exerciseName
            }
            sets_input_warmup.setText(warmupExerciseEdit.sets)
            reps_input_warmup.setText(warmupExerciseEdit.reps)
        }

        add_exercise_button_warmup.setOnClickListener {
            if(warmupExerciseEdit != null){
                editExerciseInWarmupArrayList()
            }
            else{
                addWarmupToArrayList()
            }
        }

        exercise_name_input_warmup.setOnClickListener {
            val intent = Intent(this, ChooseWarmupExerciseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editExerciseInWarmupArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val warmupArrayList = workoutDayObject.warmupArrayList
        val exercisePosition = AddWeekToBlockActivity.warmupExerciseEdit?.position

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_warmup.text.toString()
            val sets = sets_input_warmup.text.toString()
            val reps = reps_input_warmup.text.toString()

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || exerciseName.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            warmupArrayList?.set(exercisePosition!!,
                WarmupExerciseObject(
                    exercisePosition,
                    exerciseName,
                    sets,
                    reps,
                    warmupArrayList.get(exercisePosition).url
                )
            )

            AddWeekToBlockActivity.warmupExerciseEdit = null
            ChooseWarmupExerciseActivity.warmupExerciseClicked = null

            val intent  = Intent(this, AddWeekToBlockActivity::class.java)
            startActivity(intent)
            finish()
        }
    }//editExerciseInWarmupArrayList function

    private fun addWarmupToArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val warmupArrayList = workoutDayObject.warmupArrayList

        val exerciseName = exercise_name_input_warmup.text.toString()
        val sets = sets_input_warmup.text.toString()
        val reps = reps_input_warmup.text.toString()

        val exerciseDatabaseObject = ChooseWarmupExerciseActivity.warmupExerciseClicked

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || exerciseDatabaseObject == null){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        warmupArrayList?.add(
            WarmupExerciseObject(
                warmupArrayList.size,
                exerciseName,
                sets,
                reps,
                exerciseDatabaseObject.videoUrl
            )
        )

        AddWeekToBlockActivity.warmupExerciseEdit = null
        ChooseWarmupExerciseActivity.warmupExerciseClicked = null

        val intent  = Intent(this, AddWeekToBlockActivity::class.java)
        startActivity(intent)
        finish()
    }//addWarmupToArrayList function

    override fun onBackPressed() {

    }
}
