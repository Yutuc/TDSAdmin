package com.univation.tdsadmin.view_workouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ConditioningExerciseObject
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.workout_adapters.ConditioningCard
import kotlinx.android.synthetic.main.activity_edit_conditioning.*
import java.util.concurrent.locks.Condition

class EditConditioningActivity : AppCompatActivity() {

    val conditioningExerciseEdit = ViewWorkoutWeekActivity.conditioningExerciseEdit
    var exerciseDatabaseObject = ViewWorkoutsChooseConditioningExerciseActivity.conditioningExerciseClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_conditioning)
        setTitle("Edit Conditioning Workout")

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_edit_conditioning.text = exerciseDatabaseObject!!.exerciseName
        }

        exercise_name_edit_conditioning.setOnClickListener {
            val intent = Intent(this, ViewWorkoutsChooseConditioningExerciseActivity::class.java)
            startActivity(intent)
            finish()
        }

        //if user wants to edit an exercise
        if(conditioningExerciseEdit != null){
            if(ViewWorkoutsChooseConditioningExerciseActivity.conditioningExerciseClicked == null){
                ViewWorkoutsChooseConditioningExerciseActivity.conditioningExerciseClicked = ExerciseDatabaseObject(conditioningExerciseEdit.exerciseName, conditioningExerciseEdit.url)
                exerciseDatabaseObject = ExerciseDatabaseObject(conditioningExerciseEdit.exerciseName, conditioningExerciseEdit.url)
                exercise_name_edit_conditioning.text = conditioningExerciseEdit.exerciseName
            }
            else if(ViewWorkoutsChooseConditioningExerciseActivity.conditioningExerciseClicked?.exerciseName != conditioningExerciseEdit.exerciseName){
                exercise_name_edit_conditioning.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                ViewWorkoutsChooseConditioningExerciseActivity.conditioningExerciseClicked = ExerciseDatabaseObject(conditioningExerciseEdit.exerciseName, conditioningExerciseEdit.url)
                exercise_name_edit_conditioning.text = conditioningExerciseEdit.exerciseName
            }
            minutes_edit_conditioning.setText(conditioningExerciseEdit.minutes)
            seconds_edit_conditioning.setText(conditioningExerciseEdit.seconds)
        }

        add_exercise_button_conditioning.setOnClickListener {
            saveEdit()
        }
    }

    private fun saveEdit(){
        val minutes = minutes_edit_conditioning.text.toString().trim()
        val seconds = seconds_edit_conditioning.text.toString().trim()

        if(exerciseDatabaseObject == null || minutes.isEmpty() || seconds.isEmpty()){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        val editedConditioning = ConditioningExerciseObject(conditioningExerciseEdit!!.position, exerciseDatabaseObject!!.exerciseName, minutes, seconds, conditioningExerciseEdit.url)

        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/${ConditioningCard.keyClicked}/conditioningArrayList")
        ConditioningCard.conditioningArrayListClicked.set(conditioningExerciseEdit!!.position, editedConditioning)
        ref.setValue(ConditioningCard.conditioningArrayListClicked)
            .addOnSuccessListener {
                finish()
            }
    }//saveEdit function
}
