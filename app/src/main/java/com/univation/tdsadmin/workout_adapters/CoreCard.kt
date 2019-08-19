package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.CoreExerciseObject
import com.univation.tdsadmin.view_workouts.ChooseBlockFragmentForViewWorkout
import com.univation.tdsadmin.view_workouts.ChooseWeekForViewWorkoutActivity
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.core_card.view.*
import kotlinx.android.synthetic.main.edit_or_delete_alert_dialog.view.*

class CoreCard(val key: String, val coreArrayList: ArrayList<CoreExerciseObject>) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val coreCardAdapter = GroupAdapter<ViewHolder>()
        coreCardAdapter.add(CoreTitlesRow())
        coreArrayList.forEach {
            coreCardAdapter.add(CoreExerciseRow(key, it))
        }
        viewHolder.itemView.core_card_recyclerview.adapter = coreCardAdapter
        coreCardAdapter.setOnItemLongClickListener { item, _ ->
            val coreRow = item as CoreExerciseRow
            val coreChosen = coreRow.coreExerciseObject

            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.edit_button_edit_or_delete_alert_dialog.setOnClickListener {
                alertDialog.dismiss()
            }

            dialogView.delete_button_edit_or_delete_alert_dialog.setOnClickListener {
                coreArrayList.remove(coreChosen)
                val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/$key/coreArrayList")
                ref.setValue(coreArrayList)
                    .addOnSuccessListener {
                        Toast.makeText(ViewWorkoutWeekActivity.mContext, "Successfully deleted workout", Toast.LENGTH_SHORT).show()
                    }
                alertDialog.dismiss()
            }

            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.core_card
    }
}