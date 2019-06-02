package com.univation.tdsadmin

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.check_in_times.view.*

class CheckInTimeAdapter(val availableTimeObject: AvailableTimeObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.time_textview_check_in.text = availableTimeObject.time
    }

    override fun getLayout(): Int {
        return R.layout.check_in_times
    }
}