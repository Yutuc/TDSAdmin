package com.univation.tdsadmin.adapters

import android.content.Intent
import com.univation.tdsadmin.R
import com.univation.tdsadmin.addworkout.AddWeekToBlockActivity
import com.univation.tdsadmin.addworkout.InputMainExerciseActivity
import com.univation.tdsadmin.objects.WorkoutDayObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_day_layout.view.*

class WorkoutDayColumn (val workoutDayObject: WorkoutDayObject): Item<ViewHolder>() {

    val mainAdapter = GroupAdapter<ViewHolder>()

    override fun bind(viewHolder: ViewHolder, position: Int) {
        AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position

        viewHolder.itemView.main_exercises_recyclerview.adapter = mainAdapter
        refreshMainRecyclerView()

        mainAdapter.setOnItemLongClickListener { item, view ->
            val mainExerciseClicked = item as WorkoutExerciseRow
            val intent = Intent(AddWeekToBlockActivity.context, InputMainExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
            AddWeekToBlockActivity.exerciseEdit = mainExerciseClicked.exerciseObject
            true
        }

        viewHolder.itemView.add_main_exercise_button.setOnClickListener {
            val intent = Intent(AddWeekToBlockActivity.context, InputMainExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
        }
    }

    private fun refreshMainRecyclerView(){
        mainAdapter.clear()
        mainAdapter.add(WorkoutTitlesRow())
        workoutDayObject.mainWorkoutArrayList?.forEach {
            mainAdapter.add(WorkoutExerciseRow(it))
        }
    }//refreshMainRecyclerView function

    override fun getLayout(): Int {
        return R.layout.workout_day_layout
    }
}