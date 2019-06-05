package com.univation.tdsadmin

import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.scheduled_check_ins_row.view.*

class ScheduledTimeRow(val scheduledTimeObject: ScheduledTimeObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

        if(scheduledTimeObject.uid.isEmpty()){
            viewHolder.itemView.cardview_scheduled_check_ins_row.visibility = View.GONE
        }
        else{
            val ref = FirebaseDatabase.getInstance().getReference("/users/").child("${scheduledTimeObject.uid}")
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val user = p0.getValue(UserObject::class.java)
                    viewHolder.itemView.name_textview_check_ins.text = "${user?.firstName} ${user?.lastName}"
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
            viewHolder.itemView.schedule_textview_check_ins.text = "${scheduledTimeObject.time}"
        }

    }

    override fun getLayout(): Int {
        return R.layout.scheduled_check_ins_row
    }
}