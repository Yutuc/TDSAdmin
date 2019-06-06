package com.univation.tdsadmin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_scheduled_check_ins.*

class ViewScheduledCheckInsActivity : AppCompatActivity() {

    companion object {
        val USER_UID = "USER_UID"
    }

    val checkinPagesArrayList = ArrayList<CheckInPageObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_scheduled_check_ins)
        setTitle("Scheduled Check-Ins")
        recyclerview_view_scheduled_check_ins.adapter = adapter
        pullDate(this)
    }

    private fun pullDate(context: Context){
        val ref = FirebaseDatabase.getInstance().getReference("/check-in-page")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                pullTimes(context, p0.key!!)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }
        })
    }//pullDate function

    private fun pullTimes(context: Context, date: String){
        val ref = FirebaseDatabase.getInstance().getReference("check-in-page/$date")
        val timesArrayList = ArrayList<ScheduledTimeObject>()
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val time = p0.getValue(ScheduledTimeObject::class.java)
                timesArrayList.add(time!!)
                refreshRecyclerView(context!!)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val time = p0.getValue(ScheduledTimeObject::class.java)
                timesArrayList.set(time?.position!!, time!!)
                refreshRecyclerView(context!!)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }
        })
        checkinPagesArrayList.add(CheckInPageObject(date, timesArrayList))
    }//pullTimes function

    private fun refreshRecyclerView(context: Context){
        adapter.clear()
        checkinPagesArrayList.forEach {
            adapter.add(VerticalRecyclerViewCheckIn(context, it))
        }
    }//refreshRecyclerView function
}
