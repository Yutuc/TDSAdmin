package com.univation.tdsadmin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_view_scheduled_check_ins.*

class ViewScheduledCheckInsActivity : AppCompatActivity() {

    val schedulesArrayList = ArrayList<AdminScheduledTimeObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_scheduled_check_ins)
        recyclerview_view_scheduled_check_ins.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerview_view_scheduled_check_ins.adapter = adapter
        pullAdminSchedules()
    }

    private fun pullAdminSchedules(){
        val ref = FirebaseDatabase.getInstance().getReference("/admin-check-ins")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val adminScheduledTimeObject = p0.getValue(AdminScheduledTimeObject::class.java)
                if(adminScheduledTimeObject != null){
                    schedulesArrayList.add(adminScheduledTimeObject)
                    refreshRecyclerView()
                }
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
    }//pullAdminSchedules function

    private fun refreshRecyclerView(){
        adapter.clear()
        schedulesArrayList.forEach {
            adapter.add(ScheduledCheckInsRow(it))
        }
    }//refreshRecyclerView function
}
