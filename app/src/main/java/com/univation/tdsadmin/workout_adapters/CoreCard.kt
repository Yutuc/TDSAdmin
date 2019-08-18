package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.CoreExerciseObject
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.core_card.view.*

class CoreCard(val key: String, val coreArrayList: ArrayList<CoreExerciseObject>) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val coreCardAdapter = GroupAdapter<ViewHolder>()
        coreCardAdapter.add(CoreTitlesRow())
        coreArrayList.forEach {
            coreCardAdapter.add(CoreExerciseRow(key, it))
        }
        viewHolder.itemView.core_card_recyclerview.adapter = coreCardAdapter
        coreCardAdapter.setOnItemLongClickListener { item, _ ->
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()
            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.core_card
    }
}