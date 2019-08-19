package com.univation.tdsadmin.workout_adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.WorkoutDayObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.vertical_recyclerview_workout.view.*

class WorkoutDayRow(val workoutDayObject : WorkoutDayObject): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val verticalAdapter = GroupAdapter<ViewHolder>()

        try{
            verticalAdapter.add(WarmupCard(workoutDayObject.key, workoutDayObject.warmupArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(MainCard(workoutDayObject.key, workoutDayObject.mainArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(AccessoryCard(workoutDayObject.key, workoutDayObject.accessoryArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(CoreCard(workoutDayObject.key, workoutDayObject.coreArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(ConditioningCard(workoutDayObject.key, workoutDayObject.conditioningArrayList!!))
        }
        catch (e: Exception){}

        viewHolder.itemView.vertical_recyclerview_workout.adapter = verticalAdapter
        //can create functions inside here by using ex) fun hello() {}
    }

    override fun getLayout(): Int {
        return R.layout.vertical_recyclerview_workout
    }
}