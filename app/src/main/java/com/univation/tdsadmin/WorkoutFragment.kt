package com.univation.tdsadmin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_workout.view.*

class WorkoutFragment : Fragment() {


    val workoutPagesArrayList = ArrayList<WorkoutPageObject>()
    val adapter = GroupAdapter<ViewHolder>()
    val currentUser = ViewUserWorkoutsActivity.uid

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_workout, container, false)
        pullUserWorkouts(context!!, inflater)
        view.horizontal_recyclerview_workout.adapter = adapter
        // Inflate the layout for this fragment
        return view
    }

    private fun pullUserWorkouts(context: Context, inflater: LayoutInflater){
        val ref = FirebaseDatabase.getInstance().getReference("workout-page/$currentUser")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                pullDate(context, inflater, p0.key!!)
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
    }//pullUserRef function

    private fun pullDate(context: Context, inflater: LayoutInflater, key: String){
        val ref = FirebaseDatabase.getInstance().getReference("workout-page/$currentUser/$key").child("date")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val date = p0.getValue(String::class.java)
                if(date != null){
                    pullWorkoutArrayList(context, inflater, key, date)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }//pullDate function

    private fun pullWorkoutArrayList(context: Context, inflater: LayoutInflater, key: String, date: String){
        val ref = FirebaseDatabase.getInstance().getReference("workout-page/$currentUser/$key/workoutExercises")
        val workoutExercisesArrayList = ArrayList<WorkoutExerciseObject>()
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val workoutExercise = p0.getValue(WorkoutExerciseObject::class.java)
                if(workoutExercise != null){
                    workoutExercisesArrayList.add(workoutExercise)
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
        pullWarmupArrayList(context, inflater, key, date, workoutExercisesArrayList)
    }//pullWorkoutArrayList function

    private fun pullWarmupArrayList(context: Context, inflater: LayoutInflater, key: String, date: String, workoutExercisesArrayList: ArrayList<WorkoutExerciseObject>){
        val ref = FirebaseDatabase.getInstance().getReference("workout-page/$currentUser/$key/warmupExercises")
        val warmupExercisesArrayList = ArrayList<WarmupExerciseObject>()
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val warmupExercise = p0.getValue(WarmupExerciseObject::class.java)
                if(warmupExercise != null){
                    warmupExercisesArrayList.add(warmupExercise)
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
        pullDailyMacronutrients(context, inflater, key, date, workoutExercisesArrayList, warmupExercisesArrayList)
    }//pullWarmupArrayList function

    private fun pullDailyMacronutrients(context: Context, inflater: LayoutInflater, key: String, date: String, workoutExercisesArrayList: ArrayList<WorkoutExerciseObject>, warmupExercisesArrayList: ArrayList<WarmupExerciseObject>){
        val ref = FirebaseDatabase.getInstance().getReference("workout-page/$currentUser/$key").child("dailyMacronutrientsObject")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)
                if(dailyMacronutrientsObject != null){
                    workoutPagesArrayList.add(WorkoutPageObject(key, date, workoutExercisesArrayList, warmupExercisesArrayList, dailyMacronutrientsObject))
                    refreshRecyclerView(context, inflater)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }//pullDailyMacronutrients function

    private fun refreshRecyclerView(context: Context, inflater: LayoutInflater){
        adapter.clear()
        workoutPagesArrayList.forEach {
            adapter.add(VerticalRecyclerViewWorkout(context, inflater, it))
        }
    }//refreshRecyclerView function
}
