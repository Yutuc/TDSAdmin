package com.univation.tdsadmin.addworkout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.CoreExerciseObject
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import kotlinx.android.synthetic.main.activity_input_core_exercise.*

class InputCoreExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_core_exercise)

        val coreExerciseEdit = AddWeekToBlockActivity.coreExerciseEdit
        val exerciseDatabaseObject = ChooseCoreExerciseActivity.coreExerciseClicked

        //after an exercise from the database was chosen
        if(exerciseDatabaseObject != null){
            exercise_name_input_core.text = exerciseDatabaseObject.exerciseName
        }

        //if user wants to edit an exercise
        if(coreExerciseEdit != null){
            if(ChooseCoreExerciseActivity.coreExerciseClicked == null){
                ChooseCoreExerciseActivity.coreExerciseClicked = ExerciseDatabaseObject(coreExerciseEdit.exerciseName, coreExerciseEdit.url)
                exercise_name_input_core.text = coreExerciseEdit.exerciseName
            }
            else if(ChooseCoreExerciseActivity.coreExerciseClicked?.exerciseName != coreExerciseEdit.exerciseName){
                exercise_name_input_core.text = exerciseDatabaseObject?.exerciseName
            }
            else{
                exercise_name_input_core.text = coreExerciseEdit.exerciseName
            }
            sets_input_core.setText(coreExerciseEdit.sets)
            reps_input_core.setText(coreExerciseEdit.reps)
        }

        add_exercise_button_core.setOnClickListener {
            if(coreExerciseEdit != null){
                editExerciseInCoreArrayList()
            }
            else{
                addCoreToArrayList()
            }
        }

        exercise_name_input_core.setOnClickListener {
            val intent = Intent(this, ChooseCoreExerciseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editExerciseInCoreArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val coreArrayList = workoutDayObject.coreArrayList
        val exercisePosition = AddWeekToBlockActivity.coreExerciseEdit?.position

        if(exercisePosition != -1){
            val exerciseName = exercise_name_input_core.text.toString()
            val sets = sets_input_core.text.toString()
            val reps = reps_input_core.text.toString()

            if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty()){
                Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                return
            }

            coreArrayList?.set(exercisePosition!!,
                CoreExerciseObject(
                    exercisePosition,
                    exerciseName,
                    sets,
                    reps,
                    "",
                    coreArrayList.get(exercisePosition).url
                )
            )

            AddWeekToBlockActivity.coreExerciseEdit = null
            ChooseCoreExerciseActivity.coreExerciseClicked = null

            val intent  = Intent(this, AddWeekToBlockActivity::class.java)
            startActivity(intent)
            finish()
        }
    }//addCoreToArrayList function

    private fun addCoreToArrayList(){
        val workoutDayObject = AddWeekToBlockActivity.workoutDaysArrayList.get(AddWeekToBlockActivity.workoutDayClickedPosition)
        val coreArrayList = workoutDayObject.coreArrayList

        val exerciseName = exercise_name_input_core.text.toString()
        val sets = sets_input_core.text.toString()
        val reps = reps_input_core.text.toString()

        val exerciseDatabaseObject = ChooseCoreExerciseActivity.coreExerciseClicked

        if(exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || exerciseDatabaseObject == null){
            Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        coreArrayList?.add(
            CoreExerciseObject(
                coreArrayList.size,
                exerciseName,
                sets,
                reps,
                "",
                exerciseDatabaseObject.videoUrl
            )
        )

        AddWeekToBlockActivity.coreExerciseEdit = null
        ChooseCoreExerciseActivity.coreExerciseClicked = null

        val intent  = Intent(this, AddWeekToBlockActivity::class.java)
        startActivity(intent)
        finish()
    }//addCoreToArrayList function

    override fun onBackPressed() {

    }
}
