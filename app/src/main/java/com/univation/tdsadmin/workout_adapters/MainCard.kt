package com.univation.tdsadmin.workout_adapters

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.MainExerciseObject
import com.univation.tdsadmin.view_workouts.ChooseBlockFragmentForViewWorkout
import com.univation.tdsadmin.view_workouts.ChooseWeekForViewWorkoutActivity
import com.univation.tdsadmin.view_workouts.EditMainActivity
import com.univation.tdsadmin.view_workouts.ViewWorkoutWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.edit_or_delete_alert_dialog.view.*
import kotlinx.android.synthetic.main.main_card.view.*
import java.lang.Exception

class MainCard (val key: String, val mainArrayList: ArrayList<MainExerciseObject>) : Item<ViewHolder>(){

    companion object {
        var keyClicked = ""
        var mainArrayListClicked = arrayListOf<MainExerciseObject>()
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val mainCardAdapter = GroupAdapter<ViewHolder>()
        mainCardAdapter.add(MainTitlesRow())
        mainArrayList.forEach {
            mainCardAdapter.add(MainExerciseRow(key, it))
        }
        viewHolder.itemView.main_card_recyclerview.adapter = mainCardAdapter
        mainCardAdapter.setOnItemLongClickListener { item, _ ->
            try{
                val mainRow = item as MainExerciseRow
                val mainChosen = mainRow.mainExerciseObject
                ViewWorkoutWeekActivity.mainExerciseEdit = mainChosen

                val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
                val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.edit_or_delete_alert_dialog, null)!!

                dialogBuilder.setView(dialogView)

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                dialogView.edit_button_edit_or_delete_alert_dialog.setOnClickListener {
                    keyClicked = key
                    mainArrayListClicked = mainArrayList

                    val intent = Intent(ViewWorkoutWeekActivity.mContext, EditMainActivity::class.java)
                    ViewWorkoutWeekActivity.mContext!!.startActivity(intent)
                    alertDialog.dismiss()
                }

                dialogView.delete_button_edit_or_delete_alert_dialog.setOnClickListener {
                    mainArrayList.remove(mainChosen)
                    val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen!!.uid}/${ChooseBlockFragmentForViewWorkout.blockClicked!!.blockObject.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked!!.weekNumber}/$key/mainArrayList")
                    ref.setValue(mainArrayList)
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
        return R.layout.main_card
    }
}