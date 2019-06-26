package com.univation.tdsadmin.addworkout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.AccessoryExerciseObject
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.objects.WarmupExerciseObject
import kotlinx.android.synthetic.main.activity_input_warmup_exercise.*

class InputAccessoryExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_accessory_exercise)

        val accessoryExerciseEdit = AddWeekToBlockActivity.accessoryExerciseEdit
        val exerciseDatabaseObject = ChooseAccessoryExerciseActivity.accessoryExerciseClicked

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_input_warmup.text = exerciseDatabaseObject.exerciseName
        }

        //if user wants to edit an exercise
        if(accessoryExerciseEdit != null){
            if(ChooseAccessoryExerciseActivity.accessoryExerciseClicked == null){
                ChooseAccessoryExerciseActivity.accessoryExerciseClicked = ExerciseDatabaseObject(accessoryExerciseEdit.exerciseName, accessoryExerciseEdit.url)
                exercise_name_input_warmup.text = accessoryExerciseEdit.exerciseName
            }
            else if(ChooseAccessoryExerciseActivity.accessoryExerciseClicked?.exerciseName != accessoryExerciseEdit.exerciseName){
                exercise_name_input_warmup.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                exercise_name_input_warmup.text = accessoryExerciseEdit.exerciseName
            }
            sets_input_warmup.setText(accessoryExerciseEdit.sets)
            reps_input_warmup.setText(accessoryExerciseEdit.reps)
        }

        add_exercise_button_warmup.setOnClickListener {
            if(accessoryExerciseEdit != null){
                editExerciseInAccessoryArrayList()
            }
            else{
                addAccessoryToArrayList()
            }
        }

        exercise_name_input_warmup.setOnClickListener {
            val intent = Intent(this, ChooseAccessoryExerciseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editExerciseInAccessoryArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val accessoryArrayList = workoutDayObject.accessoryArrayList
        val exercisePosition = AddWeekToBlockActivity.accessoryExerciseEdit?.position

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_warmup.text.toString()
            val sets = sets_input_warmup.text.toString()
            val reps = reps_input_warmup.text.toString()

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || exerciseName.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            accessoryArrayList?.set(exercisePosition!!,
                AccessoryExerciseObject(
                    exercisePosition,
                    exerciseName,
                    sets,
                    reps,
                    accessoryArrayList.get(exercisePosition).url
                )
            )

            AddWeekToBlockActivity.accessoryExerciseEdit = null
            ChooseAccessoryExerciseActivity.accessoryExerciseClicked = null

            val intent  = Intent(this, AddWeekToBlockActivity::class.java)
            startActivity(intent)
            finish()
        }
    }//editExerciseInWarmupArrayList function

    private fun addAccessoryToArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val accessoryArrayList = workoutDayObject.accessoryArrayList

        val exerciseName = exercise_name_input_warmup.text.toString()
        val sets = sets_input_warmup.text.toString()
        val reps = reps_input_warmup.text.toString()

        val exerciseDatabaseObject = ChooseAccessoryExerciseActivity.accessoryExerciseClicked

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || exerciseDatabaseObject == null){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        accessoryArrayList?.add(
            AccessoryExerciseObject(
                accessoryArrayList.size,
                exerciseName,
                sets,
                reps,
                exerciseDatabaseObject.videoUrl
            )
        )

        AddWeekToBlockActivity.accessoryExerciseEdit = null
        ChooseAccessoryExerciseActivity.accessoryExerciseClicked = null

        val intent  = Intent(this, AddWeekToBlockActivity::class.java)
        startActivity(intent)
        finish()
    }//addWarmupToArrayList function

    override fun onBackPressed() {

    }
}
