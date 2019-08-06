package com.univation.tdsadmin.add_workout

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.adapters.WorkoutDayColumn
import com.univation.tdsadmin.objects.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_add_week_to_block.*
import kotlin.collections.ArrayList

class AddWeekToBlockActivity : AppCompatActivity() {

    companion object {
        var context: Context? = null

        var workoutDaysArrayList = ArrayList<WorkoutDayObject>()
        var workoutDayClickedPosition = 0

        var mainExerciseEdit : MainExerciseObject? = null
        var warmupExerciseEdit : WarmupExerciseObject? = null
        var accessoryExerciseEdit : AccessoryExerciseObject? = null
        var coreExerciseEdit : CoreExerciseObject? = null
        var conditioningExerciseEdit : ConditioningExerciseObject? = null

        val adapter = GroupAdapter<ViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_week_to_block)
        setTitle("Create a week")
        context = this
        horizontal_recyclerview_weeks.adapter = adapter
        horizontal_recyclerview_weeks.scrollToPosition(workoutDayClickedPosition)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_day_to_week -> {
                val workoutDayObject = WorkoutDayObject(workoutDaysArrayList.size, "", arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf())
                workoutDaysArrayList.add(workoutDayObject)
                adapter.add(WorkoutDayColumn(workoutDayObject))
            }
            R.id.save_week_to_block -> {
                val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen?.uid}/${AddBlockActivity.blockClicked?.blockObject?.blockName}/Week ${AddBlockActivity.blockClicked?.blockObject?.size!!+1}")

                if(workoutDaysArrayList.isEmpty()){
                    Toast.makeText(this, "No days created", Toast.LENGTH_SHORT).show()
                    return true
                }

                workoutDaysArrayList.forEach {
                    if(it.mainArrayList?.isEmpty()!! || it.accessoryArrayList?.isEmpty()!! || it.coreArrayList?.isEmpty()!! || it.conditioningArrayList?.isEmpty()!! || it.warmupArrayList?.isEmpty()!!){
                        Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                        return true
                    }
                }

                workoutDaysArrayList.forEach {
                    val key = ref.push().key
                    ref.child("$key").setValue(WorkoutDayObject(it.position, key!!, it.mainArrayList, it.warmupArrayList, it.accessoryArrayList, it.coreArrayList, it.conditioningArrayList)).addOnSuccessListener {
                        Toast.makeText(this, "Successfully saved workout to user", Toast.LENGTH_SHORT).show()
                    }
                }

                val sizeRef = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen?.uid}/${AddBlockActivity.blockClicked?.blockObject?.blockName}").child("size")
                sizeRef.setValue(AddBlockActivity.blockClicked?.blockObject?.size!! + 1)
                AddBlockActivity.blockClicked!!.blockObject.size += 1
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_week_to_block_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {

    }
}
