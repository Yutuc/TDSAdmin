package com.univation.tdsadmin.view_workouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.AccessoryExerciseObject
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.workout_adapters.AccessoryCard
import kotlinx.android.synthetic.main.activity_edit_accessory.*

class EditAccessoryActivity : AppCompatActivity() {

    val accessoryExerciseEdit = ViewWorkoutWeekActivity.accessoryExerciseEdit
    var exerciseDatabaseObject = ViewWorkoutsChooseAccessoryExerciseActivity.accessoryExerciseClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_accessory)
        setTitle("Edit Accessory Workout")

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_edit_accessory.text = exerciseDatabaseObject!!.exerciseName
        }

        exercise_name_edit_accessory.setOnClickListener {
            val intent = Intent(this, ViewWorkoutsChooseAccessoryExerciseActivity::class.java)
            startActivity(intent)
            finish()
        }

        //if user wants to edit an exercise
        if(accessoryExerciseEdit != null){
            if(ViewWorkoutsChooseAccessoryExerciseActivity.accessoryExerciseClicked == null){
                ViewWorkoutsChooseAccessoryExerciseActivity.accessoryExerciseClicked = ExerciseDatabaseObject(accessoryExerciseEdit.exerciseName, accessoryExerciseEdit.url)
                exerciseDatabaseObject = ExerciseDatabaseObject(accessoryExerciseEdit.exerciseName, accessoryExerciseEdit.url)
                exercise_name_edit_accessory.text = accessoryExerciseEdit.exerciseName
            }
            else if(ViewWorkoutsChooseAccessoryExerciseActivity.accessoryExerciseClicked?.exerciseName != accessoryExerciseEdit.exerciseName){
                exercise_name_edit_accessory.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                ViewWorkoutsChooseAccessoryExerciseActivity.accessoryExerciseClicked = ExerciseDatabaseObject(accessoryExerciseEdit.exerciseName, accessoryExerciseEdit.url)
                exercise_name_edit_accessory.text = accessoryExerciseEdit.exerciseName
            }
            sets_edit_accessory.setText(accessoryExerciseEdit.sets)
            reps_edit_accessory.setText(accessoryExerciseEdit.reps)
        }

        add_exercise_button_accessory.setOnClickListener {
            saveEdit()
        }
    }

    private fun saveEdit(){
        val sets = sets_edit_accessory.text.toString().trim()
        val reps = reps_edit_accessory.text.toString().trim()

        if(exerciseDatabaseObject == null || sets.isEmpty() || reps.isEmpty()){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        val editedAccessory = AccessoryExerciseObject(accessoryExerciseEdit!!.position, exerciseDatabaseObject!!.exerciseName, sets, reps, accessoryExerciseEdit.weight, exerciseDatabaseObject!!.videoUrl)

        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/${AccessoryCard.keyClicked}/accessoryArrayList")
        AccessoryCard.accessoryArrayListClicked.set(accessoryExerciseEdit!!.position, editedAccessory)
        ref.setValue(AccessoryCard.accessoryArrayListClicked)
            .addOnSuccessListener {
                finish()
            }
    }//saveEdit function
}
