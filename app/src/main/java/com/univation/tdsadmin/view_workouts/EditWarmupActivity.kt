package com.univation.tdsadmin.view_workouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.add_workout.ChooseWarmupExerciseActivity
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.univation.tdsadmin.objects.WarmupExerciseObject
import com.univation.tdsadmin.workout_adapters.WarmupCard
import kotlinx.android.synthetic.main.activity_edit_warmup.*

class EditWarmupActivity : AppCompatActivity() {

    val warmupExerciseEdit = ViewWorkoutWeekActivity.warmupExerciseEdit
    var exerciseDatabaseObject = ViewWorkoutsChooseWarmupExerciseActivity.warmupExerciseClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_warmup)
        setTitle("Edit Warmup Workout")

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_edit_warmup.text = exerciseDatabaseObject!!.exerciseName
        }

        exercise_name_edit_warmup.setOnClickListener {
            val intent = Intent(this, ViewWorkoutsChooseWarmupExerciseActivity::class.java)
            startActivity(intent)
            finish()
        }

        //if user wants to edit an exercise
        if(warmupExerciseEdit != null){
            if(ViewWorkoutsChooseWarmupExerciseActivity.warmupExerciseClicked == null){
                ViewWorkoutsChooseWarmupExerciseActivity.warmupExerciseClicked = ExerciseDatabaseObject(warmupExerciseEdit.exerciseName, warmupExerciseEdit.url)
                exerciseDatabaseObject = ExerciseDatabaseObject(warmupExerciseEdit.exerciseName, warmupExerciseEdit.url)
                exercise_name_edit_warmup.text = warmupExerciseEdit.exerciseName
            }
            else if(ViewWorkoutsChooseWarmupExerciseActivity.warmupExerciseClicked?.exerciseName != warmupExerciseEdit.exerciseName){
                exercise_name_edit_warmup.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                ViewWorkoutsChooseWarmupExerciseActivity.warmupExerciseClicked = ExerciseDatabaseObject(warmupExerciseEdit.exerciseName, warmupExerciseEdit.url)
                exercise_name_edit_warmup.text = warmupExerciseEdit.exerciseName
            }
            sets_edit_warmup.setText(warmupExerciseEdit.sets)
            reps_edit_warmup.setText(warmupExerciseEdit.reps)
        }

        add_exercise_button_warmup.setOnClickListener {
            saveEdit()
        }
    }

    private fun saveEdit(){
        val sets = sets_edit_warmup.text.toString().trim()
        val reps = reps_edit_warmup.text.toString().trim()

        if(exerciseDatabaseObject == null || sets.isEmpty() || reps.isEmpty()){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        val editedWarmup = WarmupExerciseObject(warmupExerciseEdit!!.position, exerciseDatabaseObject!!.exerciseName, sets, reps, exerciseDatabaseObject!!.videoUrl)

        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/${WarmupCard.keyClicked}/warmupArrayList")
        WarmupCard.warmupArrayListClicked.set(warmupExerciseEdit.position, editedWarmup)
        ref.setValue(WarmupCard.warmupArrayListClicked)
            .addOnSuccessListener {
                finish()
            }
    }//saveEdit function
}
