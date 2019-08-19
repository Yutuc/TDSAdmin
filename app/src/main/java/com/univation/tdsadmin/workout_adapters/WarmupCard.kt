package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.WarmupExerciseObject
import com.univation.tdsadmin.view_workouts.ChooseBlockFragmentForViewWorkout
import com.univation.tdsadmin.view_workouts.ChooseWeekForViewWorkoutActivity
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.edit_or_delete_alert_dialog.view.*
import kotlinx.android.synthetic.main.warmup_card.view.*

class WarmupCard(val key: String, val warmupArrayList: ArrayList<WarmupExerciseObject>): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val warmupCardAdapter = GroupAdapter<ViewHolder>()
        warmupCardAdapter.add(WarmupTitlesRow())
        warmupArrayList.forEach {
            warmupCardAdapter.add(WarmupExerciseRow(it))
        }
        viewHolder.itemView.warmup_card_recyclerview.adapter = warmupCardAdapter
        warmupCardAdapter.setOnItemLongClickListener { item, _ ->
            val warmupRow = item as WarmupExerciseRow
            val warmupChosen = warmupRow.warmupExerciseObject
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.edit_button_edit_or_delete_alert_dialog.setOnClickListener {
                alertDialog.dismiss()
            }

            dialogView.delete_button_edit_or_delete_alert_dialog.setOnClickListener {
                warmupArrayList.remove(warmupChosen)
                val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/$key/warmupArrayList")
                ref.setValue(warmupArrayList)
                    .addOnSuccessListener {
                        Toast.makeText(ViewWorkoutWeekActivity.mContext, "Successfully deleted workout", Toast.LENGTH_SHORT).show()
                    }
                alertDialog.dismiss()
            }
            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.warmup_card
    }
}

