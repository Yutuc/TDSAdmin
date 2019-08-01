package com.univation.tdsadmin.view_check_ins

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.R
import com.univation.tdsapplication.workout_adapters.WeekRow
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_choose_week.*

class ChooseWeekActivity : AppCompatActivity() {

    companion object {
        var weekClicked : WeekRow? = null
    }

    val weekArrayList = ArrayList<String>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_week)
        setTitle("Choose week")

        recyclerview_choose_week.adapter = adapter
        recyclerview_choose_week.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, _ ->
            weekClicked = item as WeekRow
            val intent = Intent(this, ViewWorkoutWeekActivityForCheckIns::class.java)
            startActivity(intent)
        }

        pullWeeks()
    }

    private fun pullWeeks(){
        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserForViewCheckInsActivity.userChosen?.uid}/${ChooseBlockFragment.blockClicked?.blockObject?.blockName}")
        ref.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val week = p0.key!!
                if(week != "blockName" && week != "size"){
                    weekArrayList.add(week)
                    refreshRecyclerView()
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullWeeks function

    private fun refreshRecyclerView(){
        adapter.clear()
        weekArrayList.forEach {
            adapter.add(WeekRow(it))
        }
    }//refreshRecyclerView function
}
