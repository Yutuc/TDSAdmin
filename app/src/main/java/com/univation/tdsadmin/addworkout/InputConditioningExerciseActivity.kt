package com.univation.tdsadmin.addworkout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ConditioningExerciseObject
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import kotlinx.android.synthetic.main.activity_input_conditioning_exercise.*
import kotlinx.android.synthetic.main.activity_input_core_exercise.*

class InputConditioningExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_conditioning_exercise)

        val conditioningExerciseEdit = AddWeekToBlockActivity.conditioningExerciseEdit
        val exerciseDatabaseObject = ChooseConditioningExerciseActivity.conditioningExerciseClicked

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_input_conditioning.text = exerciseDatabaseObject.exerciseName
        }

        //if user wants to edit an exercise
        if(conditioningExerciseEdit != null){
            if(ChooseConditioningExerciseActivity.conditioningExerciseClicked == null){
                ChooseConditioningExerciseActivity.conditioningExerciseClicked = ExerciseDatabaseObject(conditioningExerciseEdit.exerciseName, conditioningExerciseEdit.url)
                exercise_name_input_conditioning.text = conditioningExerciseEdit.exerciseName
            }
            else if(ChooseConditioningExerciseActivity.conditioningExerciseClicked?.exerciseName != conditioningExerciseEdit.exerciseName){
                exercise_name_input_conditioning.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                exercise_name_input_conditioning.text = conditioningExerciseEdit.exerciseName
            }
            minutes_input_conditioning.setText(conditioningExerciseEdit.minutes)
            seconds_input_conditioning.setText(conditioningExerciseEdit.seconds)
        }

        add_exercise_button_conditioning.setOnClickListener {
            if(conditioningExerciseEdit != null){
                editExerciseInConditioningArrayList()
            }
            else{
                addConditioningToArrayList()
            }
        }

        exercise_name_input_conditioning.setOnClickListener {
            val intent = Intent(this, ChooseConditioningExerciseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editExerciseInConditioningArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val conditioningArrayList = workoutDayObject.conditioningArrayList
        val exercisePosition = AddWeekToBlockActivity.conditioningExerciseEdit?.position

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_conditioning.text.toString()
            val minutes = minutes_input_conditioning.text.toString()
            val seconds = seconds_input_conditioning.text.toString()

            if(exerciseName.isEmpty() || minutes.isEmpty() || seconds.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            conditioningArrayList?.set(exercisePosition!!,
                ConditioningExerciseObject(
                    exercisePosition,
                    exerciseName,
                    minutes,
                    seconds,
                    conditioningArrayList.get(exercisePosition).url
                )
            )

            AddWeekToBlockActivity.conditioningExerciseEdit = null
            ChooseConditioningExerciseActivity.conditioningExerciseClicked = null

            val intent  = Intent(this, AddWeekToBlockActivity::class.java)
            startActivity(intent)
            finish()
        }
    }//editExerciseInConditioningArrayList function

    private fun addConditioningToArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val conditioningArrayList = workoutDayObject.conditioningArrayList

        val exerciseName = exercise_name_input_conditioning.text.toString()
        val minutes = minutes_input_conditioning.text.toString()
        val seconds = seconds_input_conditioning.text.toString()

        val exerciseDatabaseObject = ChooseConditioningExerciseActivity.conditioningExerciseClicked

        if(exerciseName.isEmpty() || minutes.isEmpty() || seconds.isEmpty() || exerciseDatabaseObject == null){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        conditioningArrayList?.add(
            ConditioningExerciseObject(
                conditioningArrayList.size,
                exerciseName,
                minutes,
                seconds,
                exerciseDatabaseObject.videoUrl
            )
        )

        AddWeekToBlockActivity.conditioningExerciseEdit = null
        ChooseConditioningExerciseActivity.conditioningExerciseClicked = null

        val intent  = Intent(this, AddWeekToBlockActivity::class.java)
        startActivity(intent)
        finish()
    }//addConditioningToArrayList function

    override fun onBackPressed() {

    }
}
