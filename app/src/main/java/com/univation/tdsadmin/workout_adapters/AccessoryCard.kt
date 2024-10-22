package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.AccessoryExerciseObject
import com.univation.tdsadmin.view_workouts.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.accessory_card.view.*
import kotlinx.android.synthetic.main.edit_or_delete_alert_dialog.view.*
import java.lang.Exception

class AccessoryCard (val key: String, val accessoryArrayList: ArrayList<AccessoryExerciseObject>) : Item<ViewHolder>(){

    companion object {
        var keyClicked = ""
        var accessoryArrayListClicked = arrayListOf<AccessoryExerciseObject>()
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val accessoryCardAdapter = GroupAdapter<ViewHolder>()
        accessoryCardAdapter.add(AccessoryTitlesRow())
        accessoryArrayList.forEach {
            accessoryCardAdapter.add(AccessoryExerciseRow(key, it))
        }
        viewHolder.itemView.accessory_card_recyclerview.adapter = accessoryCardAdapter
        accessoryCardAdapter.setOnItemLongClickListener { item, _ ->
            try{
                val accessoryRowChosen = item as AccessoryExerciseRow
                val accessoryChosen = accessoryRowChosen.accessoryExerciseObject
                ViewWorkoutWeekActivity.accessoryExerciseEdit = accessoryChosen

                val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
                val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

                dialogBuilder.setView(dialogView)

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                dialogView.edit_button_edit_or_delete_alert_dialog.setOnClickListener {
                    keyClicked = key
                    accessoryArrayListClicked = accessoryArrayList
                    val intent = Intent(ViewWorkoutWeekActivity.mContext, EditAccessoryActivity::class.java)
                    ViewWorkoutWeekActivity.mContext!!.startActivity(intent)
                    alertDialog.dismiss()
                }

                dialogView.delete_button_edit_or_delete_alert_dialog.setOnClickListener {
                    accessoryArrayList.remove(accessoryChosen)
                    val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/$key/accessoryArrayList")
                    ref.setValue(accessoryArrayList)
                        .addOnSuccessListener {
                            Toast.makeText(ViewWorkoutWeekActivity.mContext, "Successfully deleted workout", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }catch (e: Exception){}

            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.accessory_card
    }
}