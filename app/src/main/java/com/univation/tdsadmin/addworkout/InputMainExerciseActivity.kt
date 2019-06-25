package com.univation.tdsadmin.addworkout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.objects.MainExerciseObject
import kotlinx.android.synthetic.main.activity_input_main_exercise.*

class InputMainExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_main_exercise)

        val exerciseEdit = AddWeekToBlockActivity.exerciseEdit
        val exerciseDatabaseObject = ChooseMainExerciseActivity.mainExerciseClicked

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_input_workout.text = exerciseDatabaseObject.exerciseName
        }

        //if user wants to edit an exercise
        if(exerciseEdit != null){
            if(ChooseMainExerciseActivity.mainExerciseClicked == null){
                ChooseMainExerciseActivity.mainExerciseClicked = ExerciseDatabaseObject(exerciseEdit.exerciseName, exerciseEdit.url)
                exercise_name_input_workout.text = exerciseEdit.exerciseName
            }
            else if(ChooseMainExerciseActivity.mainExerciseClicked?.exerciseName != exerciseEdit.exerciseName){
                exercise_name_input_workout.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                exercise_name_input_workout.text = exerciseEdit.exerciseName
            }
            sets_input_workout.setText(exerciseEdit.sets)
            reps_input_workout.setText(exerciseEdit.reps)
            rpe_input_workout.setText(exerciseEdit.rpe)
        }

        add_exercise_button_workout.setOnClickListener {
            if(exerciseEdit != null){
                editExerciseInWorkoutArrayList()
            }
            else{
                addToWorkoutArrayList()
            }
        }

        exercise_name_input_workout.setOnClickListener {
            val intent = Intent(this, ChooseMainExerciseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editExerciseInWorkoutArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition!!)
        val mainWorkoutArrayList = workoutDayObject.mainWorkoutArrayList
        val exercisePosition = AddWeekToBlockActivity.exerciseEdit?.position

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_workout.text.toString()
            val sets = sets_input_workout.text.toString()
            val reps = reps_input_workout.text.toString()
            val rpe = rpe_input_workout.text.toString()

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty() || exerciseName.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            mainWorkoutArrayList?.set(exercisePosition!!,
                MainExerciseObject(
                    exercisePosition!!,
                    exerciseName,
                    sets,
                    reps,
                    rpe,
                    "",
                    mainWorkoutArrayList.get(exercisePosition).url
                )
            )

            AddWeekToBlockActivity.exerciseEdit = null
            ChooseMainExerciseActivity.mainExerciseClicked = null

            val intent  = Intent(this, AddWeekToBlockActivity::class.java)
            startActivity(intent)
            finish()
        }
    }//editExerciseInWorkoutArrayList function

    private fun addToWorkoutArrayList(){

        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition!!)
        val mainWorkoutArrayList = workoutDayObject.mainWorkoutArrayList

        val exerciseName = exercise_name_input_workout.text.toString()
        val sets = sets_input_workout.text.toString()
        val reps = reps_input_workout.text.toString()
        val rpe = rpe_input_workout.text.toString()

        val exerciseDatabaseObject = ChooseMainExerciseActivity.mainExerciseClicked

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty() || exerciseDatabaseObject == null){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        mainWorkoutArrayList?.add(
            MainExerciseObject(
                mainWorkoutArrayList.size,
                exerciseName,
                sets,
                reps,
                rpe,
                "",
                exerciseDatabaseObject.videoUrl
            )
        )

        AddWeekToBlockActivity.exerciseEdit = null
        ChooseMainExerciseActivity.mainExerciseClicked = null

        val intent  = Intent(this, AddWeekToBlockActivity::class.java)
        startActivity(intent)
        finish()
    }//pushData function
}
