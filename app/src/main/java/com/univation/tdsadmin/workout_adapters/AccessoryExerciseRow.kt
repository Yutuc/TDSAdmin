package com.univation.tdsadmin.workout_adapters

import android.graphics.Paint
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.AccessoryExerciseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.accessory_exercise_row.view.*

class AccessoryExerciseRow (val key: String, val accessoryExerciseObject: AccessoryExerciseObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_accessory.text = accessoryExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_accessory.text = accessoryExerciseObject.sets
        viewHolder.itemView.reps_textview_accessory.text = accessoryExerciseObject.reps
        if(accessoryExerciseObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_accessory.text = "lbs"
        }
        else{
            viewHolder.itemView.weight_input_accessory.text = accessoryExerciseObject.weight
        }
        viewHolder.itemView.weight_input_accessory.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun getLayout(): Int {
        return R.layout.accessory_exercise_row
    }
}
