package com.univation.tdsadmin

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.date_title_workout_page.view.*

class DateTitleWorkoutRow(val date: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.date_textview_workout.text = date
    }

    override fun getLayout(): Int {
        return R.layout.date_title_workout_page
    }
}