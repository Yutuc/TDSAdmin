package com.univation.tdsadmin.adapters

import android.content.Intent
import com.univation.tdsadmin.R
import com.univation.tdsadmin.addworkout.AddWeekToBlockActivity
import com.univation.tdsadmin.addworkout.InputAccessoryExerciseActivity
import com.univation.tdsadmin.addworkout.InputMainExerciseActivity
import com.univation.tdsadmin.addworkout.InputWarmupExerciseActivity
import com.univation.tdsadmin.objects.WorkoutDayObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_day_layout.view.*

class WorkoutDayColumn (val workoutDayObject: WorkoutDayObject): Item<ViewHolder>() {

    val mainAdapter = GroupAdapter<ViewHolder>()
    val warmupAdapter = GroupAdapter<ViewHolder>()
    val accessoryAdapter = GroupAdapter<ViewHolder>()

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.main_exercises_recyclerview.adapter = mainAdapter
        refreshMainRecyclerView()

        mainAdapter.setOnItemLongClickListener { item, _ ->
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val mainExerciseClicked = item as WorkoutExerciseRow
            val intent = Intent(AddWeekToBlockActivity.context, InputMainExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
            AddWeekToBlockActivity.mainExerciseEdit = mainExerciseClicked.exerciseObject
            true
        }

        viewHolder.itemView.add_main_exercise_button.setOnClickListener {
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val intent = Intent(AddWeekToBlockActivity.context, InputMainExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
        }

        viewHolder.itemView.warmup_exercises_recyclerview.adapter = warmupAdapter
        refreshWarmupRecyclerView()

        warmupAdapter.setOnItemLongClickListener { item, _ ->
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val warmupExerciseClicked = item as WarmupExerciseRow
            val intent = Intent(AddWeekToBlockActivity.context, InputWarmupExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
            AddWeekToBlockActivity.warmupExerciseEdit = warmupExerciseClicked.exerciseObject
            true
        }

        viewHolder.itemView.add_warmup_exercise_button.setOnClickListener {
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val intent = Intent(AddWeekToBlockActivity.context, InputWarmupExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
        }

        viewHolder.itemView.accessory_exercises_recyclerview.adapter = accessoryAdapter
        refreshAccessoryRecyclerView()

        accessoryAdapter.setOnItemLongClickListener { item, _ ->
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val accessoryExerciseClicked = item as AccessoryExerciseRow
            val intent = Intent(AddWeekToBlockActivity.context, InputAccessoryExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
            AddWeekToBlockActivity.accessoryExerciseEdit = accessoryExerciseClicked.exerciseObject
            true
        }

        viewHolder.itemView.add_accessory_exercise_button.setOnClickListener {
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val intent = Intent(AddWeekToBlockActivity.context, InputAccessoryExerciseActivity::class.java)
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

    private fun refreshAccessoryRecyclerView(){
        accessoryAdapter.clear()
        accessoryAdapter.add(AccessoryTitlesRow())
        workoutDayObject.accessoryArrayList?.forEach {
            accessoryAdapter.add(AccessoryExerciseRow(it))
        }
    }

    private fun refreshWarmupRecyclerView(){
        warmupAdapter.clear()
        warmupAdapter.add(WarmupTitlesRow())
        workoutDayObject.warmupArrayList?.forEach {
            warmupAdapter.add(WarmupExerciseRow(it))
        }
    }//refreshWarmupRecyclerView function

    override fun getLayout(): Int {
        return R.layout.workout_day_layout
    }
}