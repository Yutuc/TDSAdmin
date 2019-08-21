package com.univation.tdsadmin.view_workouts

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.*
import com.univation.tdsadmin.workout_adapters.WorkoutDayRow
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_workout_week.*

class ViewWorkoutWeekActivity : AppCompatActivity() {

    companion object {
        var mInflater: LayoutInflater? = null
        var mContext: Context? = null

        var mainExerciseEdit : MainExerciseObject? = null
        var warmupExerciseEdit : WarmupExerciseObject? = null
        var accessoryExerciseEdit : AccessoryExerciseObject? = null
        var coreExerciseEdit : CoreExerciseObject? = null
        var conditioningExerciseEdit : ConditioningExerciseObject? = null
    }

    val workoutDayArraylist = ArrayList<WorkoutDayObject>()

    val adapter = GroupAdapter<ViewHolder>()
    val currentUser = ChooseUserActivity.userChosen?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_workout_week)

        setTitle("${ChooseUserActivity.userChosen?.firstName} ${ChooseUserActivity.userChosen?.lastName}'s ${ChooseWeekForViewWorkoutActivity.weekClicked?.weekNumber} Workouts")

        mInflater = layoutInflater
        mContext = this

        horizontal_recyclerview_workout_week.adapter = adapter
        pullWorkoutDays()
    }
    private fun pullWorkoutDays(){
        val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${ChooseBlockFragmentForViewWorkout.blockClicked?.blockObject?.blockName}/${ChooseWeekForViewWorkoutActivity.weekClicked?.weekNumber}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val workoutDayObject = p0.getValue(WorkoutDayObject::class.java)!!
                workoutDayArraylist.set(workoutDayObject.position, workoutDayObject)
                refreshRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val workoutDayObject = p0.getValue(WorkoutDayObject::class.java)!!
                workoutDayArraylist.add(workoutDayObject)
                refreshRecyclerView()
            }

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
