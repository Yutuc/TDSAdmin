package com.univation.tdsapplication.workout_adapters

import com.univation.tdsadmin.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.week_row.view.*

class WeekRow (val weekNumber: String) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.week_label_textview.text = weekNumber
    }

    override fun getLayout(): Int {
        return R.layout.week_row
    }
}