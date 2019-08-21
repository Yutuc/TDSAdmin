package com.univation.tdsadmin.view_workouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.CoreComponentFactory
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.add_workout.ChooseCoreExerciseActivity
import com.univation.tdsadmin.objects.CoreExerciseObject
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.workout_adapters.CoreCard
import kotlinx.android.synthetic.main.activity_edit_core.*

class EditCoreActivity : AppCompatActivity() {

    val coreExerciseEdit = ViewWorkoutWeekActivity.coreExerciseEdit
    var exerciseDatabaseObject = ViewWorkoutsChooseCoreExerciseActivity.coreExerciseClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_core)
        setTitle("Edit Core Workout")

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_edit_core.text = exerciseDatabaseObject!!.exerciseName
        }

        exercise_name_edit_core.setOnClickListener {
            val intent = Intent(this, ViewWorkoutsChooseCoreExerciseActivity::class.java)
            startActivity(intent)
            finish()
        }

        //if user wants to edit an exercise
        if(coreExerciseEdit != null){
            if(ViewWorkoutsChooseCoreExerciseActivity.coreExerciseClicked == null){
                ViewWorkoutsChooseCoreExerciseActivity.coreExerciseClicked = ExerciseDatabaseObject(coreExerciseEdit.exerciseName, coreExerciseEdit.url)
                exerciseDatabaseObject = ExerciseDatabaseObject(coreExerciseEdit.exerciseName, coreExerciseEdit.url)
                exercise_name_edit_core.text = coreExerciseEdit.exerciseName
            }
            else if(ViewWorkoutsChooseCoreExerciseActivity.coreExerciseClicked?.exerciseName != coreExerciseEdit.exerciseName){
                exercise_name_edit_core.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                ViewWorkoutsChooseCoreExerciseActivity.coreExerciseClicked = ExerciseDatabaseObject(coreExerciseEdit.exerciseName, coreExerciseEdit.url)
                exercise_name_edit_core.text = coreExerciseEdit.exerciseName
            }
            sets_edit_core.setText(coreExerciseEdit.sets)
            reps_edit_core.setText(coreExerciseEdit.reps)
        }

        add_exercise_button_core.setOnClickListener {
            saveEdit()
        }
    }

    private fun saveEdit(){
        val sets = sets_edit_core.text.toString().trim()
        val reps = reps_edit_core.text.toString().trim()

        if(exerciseDatabaseObject == null || sets.isEmpty() || reps.isEmpty()){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        val editedCore = CoreExerciseObject(coreExerciseEdit!!.position, exerciseDatabaseObject!!.exerciseName, sets, reps, coreExerciseEdit.weight, exerciseDatabaseObject!!.videoUrl)

        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/${CoreCard.keyClicked}/coreArrayList")
        CoreCard.coreArrayListClicked.set(coreExerciseEdit!!.position, editedCore)
        ref.setValue(CoreCard.coreArrayListClicked)
            .addOnSuccessListener {
                finish()
            }
    }//saveEdit function
}
