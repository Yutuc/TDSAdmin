package com.univation.tdsadmin.view_check_ins

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.WorkoutDayObject
import com.univation.tdsadmin.workout_adapters.WorkoutDayRow
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_workout_week.*

class ViewWorkoutWeekActivityForCheckIns : AppCompatActivity() {

    companion object {
        var mInflater: LayoutInflater? = null
        var mContext: Context? = null
    }

    val workoutDayArraylist = ArrayList<WorkoutDayObject>()

    val adapter = GroupAdapter<ViewHolder>()
    val currentUser = ChooseUserForViewCheckInsActivity.userChosen?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_workout_week)

        setTitle("${ChooseUserForViewCheckInsActivity.userChosen?.firstName} ${ChooseUserForViewCheckInsActivity.userChosen?.lastName}'s ${ChooseWeekActivity.weekClicked?.weekNumber} Workouts")

        mInflater = layoutInflater
        mContext = this

        horizontal_recyclerview_workout_week.adapter = adapter
        pullWorkoutDays()
    }

    private fun pullWorkoutDays(){
        val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${ChooseBlockFragment.blockClicked?.blockObject?.blockName}/${ChooseWeekActivity.weekClicked?.weekNumber}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val workoutDayObject = p0.getValue(WorkoutDayObject::class.java)!!
                workoutDayArraylist.add(workoutDayObject)
                refreshRecyclerView()            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullWorkoutDays function

    private fun refreshRecyclerView(){
        adapter.clear()
        workoutDayArraylist.forEach {
            adapter.add(WorkoutDayRow(it))
        }
    }//refreshRecyclerView function
}
