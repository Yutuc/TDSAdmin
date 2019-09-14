package com.univation.tdsadmin.adapters

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import com.univation.tdsadmin.R
import com.univation.tdsadmin.add_workout.*
import com.univation.tdsadmin.objects.WorkoutDayObject
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_add_week_to_block.*
import kotlinx.android.synthetic.main.delete_workout_day_alert_dialog.view.*
import kotlinx.android.synthetic.main.workout_day_layout.view.*

class WorkoutDayColumn (val workoutDayObject: WorkoutDayObject): Item<ViewHolder>() {

    val mainAdapter = GroupAdapter<ViewHolder>()
    val warmupAdapter = GroupAdapter<ViewHolder>()
    val accessoryAdapter = GroupAdapter<ViewHolder>()
    val coreAdapter = GroupAdapter<ViewHolder>()
    val conditioningAdapter = GroupAdapter<ViewHolder>()

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.workout_day_scrollview.post {
            viewHolder.itemView.workout_day_scrollview.fullScroll(View.FOCUS_DOWN)
        }

        viewHolder.itemView.main_exercises_recyclerview.adapter = mainAdapter
        refreshMainRecyclerView()

        mainAdapter.setOnItemLongClickListener { item, _ ->
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            try{
                val mainExerciseClicked = item as WorkoutExerciseRow
                val intent = Intent(AddWeekToBlockActivity.context, InputMainExerciseActivity::class.java)
                AddWeekToBlockActivity.context?.startActivity(intent)
                AddWeekToBlockActivity.mainExerciseEdit = mainExerciseClicked.exerciseObject
            }catch (e: Exception){}
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
            try{
                val warmupExerciseClicked = item as WarmupExerciseRow
                val intent = Intent(AddWeekToBlockActivity.context, InputWarmupExerciseActivity::class.java)
                AddWeekToBlockActivity.context?.startActivity(intent)
                AddWeekToBlockActivity.warmupExerciseEdit = warmupExerciseClicked.exerciseObject
            }catch (e: Exception){}
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
            try{
                val accessoryExerciseClicked = item as AccessoryExerciseRow
                val intent = Intent(AddWeekToBlockActivity.context, InputAccessoryExerciseActivity::class.java)
                AddWeekToBlockActivity.context?.startActivity(intent)
                AddWeekToBlockActivity.accessoryExerciseEdit = accessoryExerciseClicked.exerciseObject
            }catch (e: Exception){}
            true
        }

        viewHolder.itemView.add_accessory_exercise_button.setOnClickListener {
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val intent = Intent(AddWeekToBlockActivity.context, InputAccessoryExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
        }

        viewHolder.itemView.core_exercises_recyclerview.adapter = coreAdapter
        refreshCoreRecyclerView()

        coreAdapter.setOnItemLongClickListener { item, _ ->
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            try{
                val coreExerciseClicked = item as CoreExerciseRow
                val intent = Intent(AddWeekToBlockActivity.context, InputCoreExerciseActivity::class.java)
                AddWeekToBlockActivity.context?.startActivity(intent)
                AddWeekToBlockActivity.coreExerciseEdit = coreExerciseClicked.exerciseObject
            }catch (e: Exception){}
            true
        }

        viewHolder.itemView.add_core_exercise_button.setOnClickListener {
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val intent = Intent(AddWeekToBlockActivity.context, InputCoreExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
        }

        viewHolder.itemView.conditioning_exercises_recyclerview.adapter = conditioningAdapter
        refreshConditioningRecyclerView()

        conditioningAdapter.setOnItemLongClickListener { item, _ ->
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            try{
                val conditioningExerciseClicked = item as ConditioningExerciseRow
                val intent = Intent(AddWeekToBlockActivity.context, InputConditioningExerciseActivity::class.java)
                AddWeekToBlockActivity.context?.startActivity(intent)
                AddWeekToBlockActivity.conditioningExerciseEdit = conditioningExerciseClicked.exerciseObject
            }catch (e: Exception){}
            true
        }

        viewHolder.itemView.add_conditioning_exercise_button.setOnClickListener {
            AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
            val intent = Intent(AddWeekToBlockActivity.context, InputConditioningExerciseActivity::class.java)
            AddWeekToBlockActivity.context?.startActivity(intent)
        }

        viewHolder.itemView.delete_day_button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(AddWeekToBlockActivity.context)
            val dialogView = AddWeekToBlockActivity.mLayoutInflater!!.inflate(R.layout.delete_workout_day_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogBuilder.setTitle("Warning")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.confirm_button_delete_workout_day.setOnClickListener {
                AddWeekToBlockActivity.workoutDayClickedPosition = workoutDayObject.position
                AddWeekToBlockActivity.workoutDaysArrayList.removeAt(workoutDayObject.position)
                AddWeekToBlockActivity.adapter.clear()
                AddWeekToBlockActivity.workoutDaysArrayList.forEach {
                    AddWeekToBlockActivity.adapter.add(WorkoutDayColumn(it))
                }
                alertDialog.dismiss()
            }
        }
    }

    private fun refreshMainRecyclerView(){
        mainAdapter.clear()
        mainAdapter.add(WorkoutTitlesRow())
        workoutDayObject.mainArrayList?.forEach {
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

    private fun refreshCoreRecyclerView(){
        coreAdapter.clear()
        coreAdapter.add(CoreTitlesRow())
        workoutDayObject.coreArrayList?.forEach {
            coreAdapter.add(CoreExerciseRow(it))
        }
    }//refreshCoreRecyclerView function

    private fun refreshConditioningRecyclerView(){
        conditioningAdapter.clear()
        conditioningAdapter.add(ConditioningTitlesRow())
        workoutDayObject.conditioningArrayList?.forEach {
            conditioningAdapter.add(ConditioningExerciseRow(it))
        }
    }//refreshConditioningRecyclerView function

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