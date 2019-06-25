package com.univation.tdsadmin.adapters

import android.content.Context
import android.view.LayoutInflater
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.WorkoutPageObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_vertical_recyclerview.view.*

class VerticalRecyclerViewWorkout(val context: Context, val inflater: LayoutInflater, val workoutPageObject: WorkoutPageObject): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val verticalAdapter = GroupAdapter<ViewHolder>()
        verticalAdapter.add(DateTitleWorkoutRow(workoutPageObject.date))
        verticalAdapter.add(WarmupCard(workoutPageObject.warmupExercises))
        verticalAdapter.add(
            WorkoutCard(
                context,
                inflater,
                workoutPageObject.key,
                workoutPageObject.workoutExercises
            )
        )
        verticalAdapter.add(
            DailyMacronutrientsCard(
                context,
                inflater,
                workoutPageObject.key,
                workoutPageObject.dailyMacronutrientsObject
            )
        )
        viewHolder.itemView.vertical_recyclerview_workout.adapter = verticalAdapter
        //can create functions inside here by using ex) fun hello() {}
    }

    override fun getLayout(): Int {
        return R.layout.workout_vertical_recyclerview
    }
}