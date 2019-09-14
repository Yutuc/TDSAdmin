package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import com.univation.tdsadmin.R
import com.univation.tdsadmin.add_workout.AddWeekToBlockActivity
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.delete_workout_day_alert_dialog.view.*
import kotlinx.android.synthetic.main.main_titles_row.view.*

class MainTitlesRow(): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.main_titles_row
    }
}