package com.univation.tdsadmin

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.scheduled_check_ins_row.view.*

class ScheduledCheckInsRow(val adminScheduledTimeObject: AdminScheduledTimeObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.name_textview_check_ins.text = adminScheduledTimeObject.userName
        viewHolder.itemView.schedule_textview_check_ins.text = "${adminScheduledTimeObject.date} at ${adminScheduledTimeObject.time}"
    }

    override fun getLayout(): Int {
        return R.layout.scheduled_check_ins_row
    }
}