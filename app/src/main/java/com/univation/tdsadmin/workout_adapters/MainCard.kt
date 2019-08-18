package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.MainExerciseObject
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_card.view.*

class MainCard (val key: String, val mainArrayList: ArrayList<MainExerciseObject>) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val mainCardAdapter = GroupAdapter<ViewHolder>()
        mainCardAdapter.add(MainTitlesRow())
        mainArrayList.forEach {
            mainCardAdapter.add(MainExerciseRow(key, it))
        }
        viewHolder.itemView.main_card_recyclerview.adapter = mainCardAdapter
        mainCardAdapter.setOnItemLongClickListener { item, _ ->
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()
            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.main_card
    }
}