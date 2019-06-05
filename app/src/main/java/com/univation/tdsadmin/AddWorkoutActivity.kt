package com.univation.tdsadmin

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_add_workout.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddWorkoutActivity : AppCompatActivity() {

    companion object {
        val USER_KEY = "USER_KEY"

        val WORKOUT_EXERCISE_KEY = "WORKOUT_EXERCISE_KEY"
        val WORKOUT_EXERCISE_POSITION = "WORKOUT_EXERCISE_POSITION"

        val WARMUP_EXERCISE_KEY = "WARMUP_EXERCISE_KEY"
        val WARMUP_EXERCISE_POSITION = " WARMUP_EXERCISE_POSITION"

        val WORKOUT_ARRAY_LIST = "WORKOUT_ARRAY_LIST"
        val WARMUP_ARRAY_LIST = "WARMUP_ARRAY_LIST"

        var workoutArrayList = ArrayList<WorkoutExerciseObject>()
        var warmupArrayList = ArrayList<WarmupExerciseObject>()

        val workoutCardAdapter = GroupAdapter<ViewHolder>()
        val warmupCardAdapter = GroupAdapter<ViewHolder>()

        var dateChosen = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_workout)
        setTitle("Add workout")

        val userChosen = intent.getParcelableExtra<UserObject>(ChooseUserActivity.USER_KEY)

        refreshWorkoutRecycler()
        refreshWarmupRecycler()

        workout_card_recyclerview.adapter = workoutCardAdapter
        warmup_card_recyclerview.adapter = warmupCardAdapter

        add_workout_exercise_button.setOnClickListener {
            val intent = Intent(this, InputWorkoutExerciseActivity::class.java)
            intent.putExtra(USER_KEY, userChosen)
            intent.putParcelableArrayListExtra(WORKOUT_ARRAY_LIST, workoutArrayList)
            startActivity(intent)
        }

        add_warmup_exercise_button.setOnClickListener {
            val intent = Intent(this, InputWarmupExerciseActivity::class.java)
            intent.putExtra(USER_KEY, userChosen)
            intent.putParcelableArrayListExtra(WARMUP_ARRAY_LIST, warmupArrayList)
            startActivity(intent)
        }

        workoutCardAdapter.setOnItemLongClickListener { item, view ->
            val workoutClicked = item as WorkoutExerciseRow
            editWorkout(workoutClicked)
            true
        }

        warmupCardAdapter.setOnItemLongClickListener { item, view ->
            val warmupClicked = item as WarmupExerciseRow
            editWarmup(warmupClicked)
            true
        }
    }//onCreate function

    private fun editWorkout(workoutClicked: WorkoutExerciseRow){
        val intent = Intent(this, InputWorkoutExerciseActivity::class.java)
        intent.putExtra(WORKOUT_EXERCISE_KEY, workoutClicked.exerciseObject)
        intent.putExtra(WORKOUT_EXERCISE_POSITION, workoutCardAdapter.getAdapterPosition(workoutClicked)-1)
        intent.putParcelableArrayListExtra(WORKOUT_ARRAY_LIST, workoutArrayList)
        startActivity(intent)
    }//editWorkout function

    private fun editWarmup(warmupClicked: WarmupExerciseRow){
        val intent = Intent(this, InputWarmupExerciseActivity::class.java)
        intent.putExtra(WARMUP_EXERCISE_KEY, warmupClicked.exerciseObject)
        intent.putExtra(WARMUP_EXERCISE_POSITION, warmupCardAdapter.getAdapterPosition(warmupClicked)-1)
        intent.putParcelableArrayListExtra(WARMUP_ARRAY_LIST, warmupArrayList)
        startActivity(intent)
    }//editWarmup function

    private fun refreshWorkoutRecycler(){
        val updatedWorkoutArrayList = intent.getParcelableArrayListExtra<WorkoutExerciseObject>(WORKOUT_ARRAY_LIST)
        if(updatedWorkoutArrayList != null) {
            workoutArrayList = updatedWorkoutArrayList
        }

        workoutCardAdapter.clear()
        workoutCardAdapter.add(WorkoutTitlesRow())
        workoutArrayList.forEach {
            workoutCardAdapter.add(WorkoutExerciseRow(it))
        }
    }//refreshWorkoutRecycler function

    private fun refreshWarmupRecycler(){
        val updatedWarmupArrayList = intent.getParcelableArrayListExtra<WarmupExerciseObject>(WARMUP_ARRAY_LIST)
        if(updatedWarmupArrayList != null) {
            warmupArrayList = updatedWarmupArrayList
        }

        warmupCardAdapter.clear()
        warmupCardAdapter.add(WarmupTitlesRow())
        warmupArrayList.forEach{
            warmupCardAdapter.add((WarmupExerciseRow(it)))
        }
    }//refreshWarmupRecycler function

    private fun saveToUser(){
        if(dateChosen.isEmpty()){
            Toast.makeText(this, "Choose a date", Toast.LENGTH_SHORT).show()
            return
        }
        else if(workoutArrayList.isEmpty()){
            Toast.makeText(this, "No workout exercises added", Toast.LENGTH_SHORT).show()
            return
        }
        else if(warmupArrayList.isEmpty()){
            Toast.makeText(this, "No warmup exercises added", Toast.LENGTH_SHORT).show()
            return
        }

        val userChosen = intent.getParcelableExtra<UserObject>(ChooseUserActivity.USER_KEY)

        val pageRef = FirebaseDatabase.getInstance().getReference("/workout-page/${userChosen.uid}").push()
        pageRef.setValue(WorkoutPageObject(pageRef.key!!, dateChosen, workoutArrayList, warmupArrayList, DailyMacronutrientsObject("", "", "", "")))

        Toast.makeText(this, "Successfully saved to user", Toast.LENGTH_SHORT).show()
        dateChosen = ""
    }//saveToUser function

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.date_picker_add_menu_workout -> {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog =
                    DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        calendar.set(Calendar.YEAR, mYear)
                        calendar.set(Calendar.MONTH, mMonth)
                        calendar.set(Calendar.DAY_OF_MONTH, mDay)
                        dateChosen = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
                    }, year, month, day)
                datePickerDialog.show()
            }

            R.id.save_to_user_menu_workout -> {
                saveToUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu_add_workout, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
