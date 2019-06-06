package com.univation.tdsadmin

import android.content.Context
import android.content.Intent
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.vertical_recycler_view_view_check_ins.view.*

class VerticalRecyclerViewCheckIn(val context: Context, val checkInPageObject: CheckInPageObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val adapter = GroupAdapter<ViewHolder>()
        viewHolder.itemView.date_textview_check_in.text = checkInPageObject.date

        checkInPageObject.timesArrayList.forEach {
            adapter.add(ScheduledTimeRow(it))
        }
        viewHolder.itemView.vertical_recyclerview_check_in.adapter = adapter

        adapter.setOnItemClickListener { item, view ->
            val scheduledClicked = item as ScheduledTimeRow
            val uid = scheduledClicked.scheduledTimeObject.uid
            viewUserWorkouts(uid)
        }
    }

    private fun viewUserWorkouts(uid: String){
        val intent = Intent(context, ViewUserWorkoutsActivity::class.java)
        intent.putExtra(ViewScheduledCheckInsActivity.USER_UID, uid)
        context.startActivity(intent)
    }//viewUserWorkouts function

    override fun getLayout(): Int {
        return R.layout.vertical_recycler_view_view_check_ins
    }
}
