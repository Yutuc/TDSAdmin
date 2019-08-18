package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ConditioningExerciseObject
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.conditioning_card.view.*

class ConditioningCard(val key: String, val conditioningArrayList: ArrayList<ConditioningExerciseObject>): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val conditioningCardAdapter = GroupAdapter<ViewHolder>()
        conditioningCardAdapter.add(ConditioningTitlesRow())
        conditioningArrayList.forEach {
            conditioningCardAdapter.add(ConditioningExerciseRow(it))
        }
        viewHolder.itemView.conditioning_card_recyclerview.adapter = conditioningCardAdapter
        conditioningCardAdapter.setOnItemLongClickListener { item, _ ->
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()
            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.conditioning_card
    }
}