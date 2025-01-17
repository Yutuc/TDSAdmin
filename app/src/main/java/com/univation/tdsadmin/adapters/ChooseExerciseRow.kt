package com.univation.tdsadmin.adapters

import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.ExerciseDatabaseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.choose_exercise_recyclerview_row.view.*

class ChooseExerciseRow(val exerciseDatabaseObject: ExerciseDatabaseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_choose_exercise_recyclerview_row.text = exerciseDatabaseObject.exerciseName
    }

    override fun getLayout(): Int {
        return R.layout.choose_exercise_recyclerview_row
    }
}
