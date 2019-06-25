package com.univation.tdsadmin.adapters

import com.univation.tdsadmin.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class WorkoutTitlesWhenViewingRow() : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.workout_titles_when_viewing_row
    }
}