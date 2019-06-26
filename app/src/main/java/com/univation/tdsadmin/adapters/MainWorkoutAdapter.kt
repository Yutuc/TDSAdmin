package com.univation.tdsadmin.adapters

import android.content.Context
import android.view.LayoutInflater
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.MainExerciseObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_page_column.view.*

class MainWorkoutAdapter(val context: Context, val inflater: LayoutInflater, val key: String, val workoutArrayList: ArrayList<MainExerciseObject>): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(WorkoutTitlesWhenViewingRow())
        workoutArrayList.forEach {
            adapter.add(UserWorkoutExerciseRow(context, inflater, key, it))
        }
        viewHolder.itemView.workout_item_recyclerview.adapter = adapter
    }

    override fun getLayout(): Int {
        return R.layout.workout_page_column
    }
}

