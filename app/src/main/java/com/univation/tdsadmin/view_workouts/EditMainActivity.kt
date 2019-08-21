package com.univation.tdsadmin.view_workouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.objects.MainExerciseObject
import com.univation.tdsadmin.workout_adapters.MainCard
import kotlinx.android.synthetic.main.activity_edit_main.*

class EditMainActivity : AppCompatActivity() {

    val mainExerciseEdit = ViewWorkoutWeekActivity.mainExerciseEdit
    var exerciseDatabaseObject = ViewWorkoutsChooseMainExerciseActivity.mainExerciseClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_main)
        setTitle("Edit Main Workout")

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_edit_workout.text = exerciseDatabaseObject!!.exerciseName
        }

        exercise_name_edit_workout.setOnClickListener {
            val intent = Intent(this, ViewWorkoutsChooseMainExerciseActivity::class.java)
            startActivity(intent)
            finish()
        }

        //if user wants to edit an exercise
        if(mainExerciseEdit != null){
            if(ViewWorkoutsChooseMainExerciseActivity.mainExerciseClicked == null){
                ViewWorkoutsChooseMainExerciseActivity.mainExerciseClicked = ExerciseDatabaseObject(mainExerciseEdit.exerciseName, mainExerciseEdit.url)
                exerciseDatabaseObject = ExerciseDatabaseObject(mainExerciseEdit.exerciseName, mainExerciseEdit.url)
                exercise_name_edit_workout.text = mainExerciseEdit.exerciseName
            }
            else if(ViewWorkoutsChooseMainExerciseActivity.mainExerciseClicked?.exerciseName != mainExerciseEdit.exerciseName){
                exercise_name_edit_workout.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                ViewWorkoutsChooseMainExerciseActivity.mainExerciseClicked = ExerciseDatabaseObject(mainExerciseEdit.exerciseName, mainExerciseEdit.url)
                exercise_name_edit_workout.text = mainExerciseEdit.exerciseName
            }
            sets_edit_workout.setText(mainExerciseEdit.sets)
            reps_edit_workout.setText(mainExerciseEdit.reps)
            rpe_edit_workout.setText(mainExerciseEdit.rpe)
        }

        add_exercise_button_workout.setOnClickListener {
            saveEdit()
        }
    }

    private fun saveEdit(){
        val sets = sets_edit_workout.text.toString().trim()
        val reps = reps_edit_workout.text.toString().trim()
        val rpe = rpe_edit_workout.text.toString().trim()

        if(exerciseDatabaseObject == null || sets.isEmpty() || reps.isEmpty()){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        val editedMain = MainExerciseObject(mainExerciseEdit!!.position, exerciseDatabaseObject!!.exerciseName, sets, reps, rpe, mainExerciseEdit.weight, exerciseDatabaseObject!!.videoUrl)

        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/${MainCard.keyClicked}/mainArrayList")
        MainCard.mainArrayListClicked.set(mainExerciseEdit!!.position, editedMain)
        ref.setValue(MainCard.mainArrayListClicked)
            .addOnSuccessListener {
                finish()
            }
    }//saveEdit function
}
