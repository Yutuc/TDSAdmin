package com.univation.tdsadmin

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_choose_workout_exercise.*
import kotlinx.android.synthetic.main.add_exercise_alert_dialog.view.*

class ChooseWorkoutExerciseActivity : AppCompatActivity() {

    companion object{
        val exerciseMap = HashMap<String, ExerciseDatabaseObject>()
        val exerciseMapCopy = HashMap<String, ExerciseDatabaseObject>()
    }

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_workout_exercise)

        setTitle("Select an exercise")

        recyclerview_choose_workout_exercise.adapter = adapter
        recyclerview_choose_workout_exercise.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, view ->
            val exerciseRecyclerviewRowItem = item as ChooseExerciseRecyclerviewRow
            val intent = Intent(this, InputWorkoutExerciseActivity::class.java)
            intent.putExtra(InputWorkoutExerciseActivity.EXERCISE_OBJECT, exerciseRecyclerviewRowItem.exerciseDatabaseObject)
            startActivity(intent)
        }

        adapter.setOnItemLongClickListener { item, view ->
            val exerciseRecyclerviewRowItem = item as ChooseExerciseRecyclerviewRow

            val dialogBuilder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.add_exercise_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.exercise_name_input_add_exercise_alert_dialog.setText(exerciseRecyclerviewRowItem.exerciseDatabaseObject.exerciseName)
            dialogView.video_url_input_add_exercise_alert_dialog.setText(exerciseRecyclerviewRowItem.exerciseDatabaseObject.videoUrl)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_to_server_button_add_exercise_alert_dialog.setOnClickListener {
                val exerciseName = dialogView.exercise_name_input_add_exercise_alert_dialog.text.toString()
                if(exerciseName != exerciseRecyclerviewRowItem.exerciseDatabaseObject.exerciseName){
                    FirebaseDatabase.getInstance().getReference("exercise-database/${exerciseRecyclerviewRowItem.exerciseDatabaseObject.exerciseName}").removeValue()
                }
                val videoUrl = dialogView.video_url_input_add_exercise_alert_dialog.text.toString()
                if(exerciseName.isEmpty() || videoUrl.isEmpty()){
                    Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val ref = FirebaseDatabase.getInstance().getReference("exercise-database/$exerciseName")
                    ref.setValue(ExerciseDatabaseObject(exerciseName, videoUrl))
                    Toast.makeText(this, "Successfully saved exercise to server", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }
            }
            true
        }

        pullExerciseDatabseObjects()
    }

    private fun pullExerciseDatabseObjects(){
        val ref = FirebaseDatabase.getInstance().getReference("/exercise-database/")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val exerciseObject = p0.getValue(ExerciseDatabaseObject::class.java)
                if(exerciseObject != null){
                    exerciseMap[p0.key!!] = exerciseObject
                    exerciseMapCopy[p0.key!!] = exerciseObject
                    refreshRecyclerView(exerciseMap)
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                exerciseMap.remove(p0.key!!)
                exerciseMapCopy.remove(p0.key!!)
                refreshRecyclerView(exerciseMap)
            }

        })
    }//pullExerciseDatabseObjects function

    private fun refreshRecyclerView(exerciseMap: HashMap<String, ExerciseDatabaseObject>){
        adapter.clear()
        exerciseMap.values.forEach {
            adapter.add(ChooseExerciseRecyclerviewRow(it))
        }
    }//refreshRecyclerView function

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_exercise_top_nav_menu -> {
                val dialogBuilder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.add_exercise_alert_dialog, null)

                dialogBuilder.setView(dialogView)

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                dialogView.save_to_server_button_add_exercise_alert_dialog.setOnClickListener {
                    val exerciseName = dialogView.exercise_name_input_add_exercise_alert_dialog.text.toString()
                    val videoUrl = dialogView.video_url_input_add_exercise_alert_dialog.text.toString()
                    if(exerciseName.isEmpty() || videoUrl.isEmpty()){
                        Toast.makeText(this, "Empty field detected", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val ref = FirebaseDatabase.getInstance().getReference("exercise-database/$exerciseName")
                        ref.setValue(ExerciseDatabaseObject(exerciseName, videoUrl))
                        Toast.makeText(this, "Successfully saved exercise to server", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu_choose_exercise_activity, menu)
        //searches the recyclerview
        val searchIcon = menu?.findItem(R.id.search_exercises_top_nav_menu)
        if(searchIcon != null){
            val searchView = searchIcon.actionView as SearchView
            val searchViewEditText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            searchViewEditText.hint = "Search Exercises"
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        val search = newText.toLowerCase()
                        exerciseMapCopy.clear()
                        exerciseMap.values.forEach{
                            if((it.exerciseName).toLowerCase().contains(search)){
                                exerciseMapCopy[it.exerciseName] = it
                            }
                        }
                        refreshRecyclerView(exerciseMapCopy)
                    }
                    else{
                        refreshRecyclerView(exerciseMap)
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}
