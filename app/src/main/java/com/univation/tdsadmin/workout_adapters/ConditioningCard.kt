package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ConditioningExerciseObject
import com.univation.tdsadmin.view_workouts.ChooseBlockFragmentForViewWorkout
import com.univation.tdsadmin.view_workouts.ChooseWeekForViewWorkoutActivity
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.conditioning_card.view.*
import kotlinx.android.synthetic.main.edit_or_delete_alert_dialog.view.*

class ConditioningCard(val key: String, val conditioningArrayList: ArrayList<ConditioningExerciseObject>): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val conditioningCardAdapter = GroupAdapter<ViewHolder>()
        conditioningCardAdapter.add(ConditioningTitlesRow())
        conditioningArrayList.forEach {
            conditioningCardAdapter.add(ConditioningExerciseRow(it))
        }
        viewHolder.itemView.conditioning_card_recyclerview.adapter = conditioningCardAdapter
        conditioningCardAdapter.setOnItemLongClickListener { item, _ ->
            val conditioningRow = item as ConditioningExerciseRow
            val conditioningChosen = conditioningRow.conditioningExerciseObject

            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.edit_button_edit_or_delete_alert_dialog.setOnClickListener {
                alertDialog.dismiss()
            }

            dialogView.delete_button_edit_or_delete_alert_dialog.setOnClickListener {
                conditioningArrayList.remove(conditioningChosen)
                val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/$key/conditioningArrayList")
                ref.setValue(conditioningArrayList)
                    .addOnSuccessListener {
                        Toast.makeText(ViewWorkoutWeekActivity.mContext, "Successfully deleted workout", Toast.LENGTH_SHORT).show()
                    }
                alertDialog.dismiss()
            }

            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.conditioning_card
    }
}