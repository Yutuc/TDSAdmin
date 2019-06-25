package com.univation.tdsadmin.addworkout

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.univation.tdsadmin.R
import com.univation.tdsadmin.adapters.WorkoutDayColumn
import com.univation.tdsadmin.objects.MainExerciseObject
import com.univation.tdsadmin.objects.WorkoutDayObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_add_week_to_block.*

class AddWeekToBlockActivity : AppCompatActivity() {

    companion object {
        var context: Context? = null

        var workoutDaysArrayList = ArrayList<WorkoutDayObject>()
        var workoutDayClickedPosition = 0

        var exerciseEdit : MainExerciseObject? = null

        val adapter = GroupAdapter<ViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_week_to_block)
        context = this
        horizontal_recyclerview_weeks.adapter = adapter
        horizontal_recyclerview_weeks.scrollToPosition(workoutDayClickedPosition)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_day_to_week -> {
                val workoutDayObject = WorkoutDayObject(workoutDaysArrayList.size, arrayListOf())
                workoutDaysArrayList.add(workoutDayObject)
                adapter.add(WorkoutDayColumn(workoutDayObject))
            }
            R.id.save_week_to_block -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu_add_week_to_block, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
