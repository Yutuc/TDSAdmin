package com.univation.tdsadmin

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.vertical_recycler_view_view_check_ins.view.*

class VerticalRecyclerViewCheckIn(val checkInPageObject: CheckInPageObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val adapter = GroupAdapter<ViewHolder>()
        viewHolder.itemView.date_textview_check_in.text = checkInPageObject.date

        checkInPageObject.timesArrayList.forEach {
            adapter.add(ScheduledTimeRow(it))
        }
        viewHolder.itemView.vertical_recyclerview_check_in.adapter = adapter
    }

    override fun getLayout(): Int {
        return R.layout.vertical_recycler_view_view_check_ins
    }
}
