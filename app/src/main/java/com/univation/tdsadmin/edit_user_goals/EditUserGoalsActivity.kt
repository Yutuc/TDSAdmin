package com.univation.tdsadmin.edit_user_goals

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.UserGoalsObject
import kotlinx.android.synthetic.main.activity_edit_user_goals.*

class EditUserGoalsActivity : AppCompatActivity() {

    val userClicked = ChooseUserActivity.userChosen!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_goals)
        setTitle("Edit ${userClicked.firstName} ${userClicked.lastName}'s Goals")
        pullUserGoals()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_user_goals_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.save_user_goals -> {
                val startWeight = start_weight_input_edit_user_goals.text.toString().trim()
                val goalWeight = goal_weight_edit_user_goals.text.toString().trim()
                val protein = protein_input_edit_user_goals.text.toString().trim()
                val carbohydrates = carbohydrates_input_edit_user_goals.text.toString().trim()
                val fats = fat_input_edit_user_goals.text.toString().trim()
                val calories = calories_input_edit_user_goals.text.toString().trim()

                if(startWeight.isEmpty()){
                    Toast.makeText(this, "Please enter a start weight", Toast.LENGTH_SHORT).show()
                }
                else if(goalWeight.isEmpty()){
                    Toast.makeText(this, "Please enter a goal weight", Toast.LENGTH_SHORT).show()
                }
                else if(protein.isEmpty()){
                    Toast.makeText(this, "Please enter protein", Toast.LENGTH_SHORT).show()
                }
                else if(carbohydrates.isEmpty()){
                    Toast.makeText(this, "Please enter carbohydrates", Toast.LENGTH_SHORT).show()
                }
                else if(fats.isEmpty()){
                    Toast.makeText(this, "Please enter fats", Toast.LENGTH_SHORT).show()
                }
                else if(calories.isEmpty()){
                    Toast.makeText(this, "Please enter calories", Toast.LENGTH_SHORT).show()
                }
                else{
                    saveUserGoals(startWeight, goalWeight, protein, carbohydrates, fats, calories)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveUserGoals(startWeight: String, goalWeight: String, protein: String, carbohydrates: String, fats: String, calories: String){
        val userGoalsObject = UserGoalsObject("${userClicked.firstName} ${userClicked.lastName}",startWeight, goalWeight, protein, carbohydrates, fats, calories)
        val ref = FirebaseDatabase.getInstance().getReference("/user-goals/${userClicked.uid}")
        ref.setValue(userGoalsObject)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully edited user goals", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }//saveUserGoals function

    private fun pullUserGoals(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-goals/${userClicked.uid}")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                try{
                    val userGoalObject = p0.getValue(UserGoalsObject::class.java)!!
                    start_weight_input_edit_user_goals.setText(userGoalObject.startWeight)
                    goal_weight_edit_user_goals.setText(userGoalObject.goalWeight)
                    protein_input_edit_user_goals.setText(userGoalObject.protein)
                    carbohydrates_input_edit_user_goals.setText(userGoalObject.carbohydrates)
                    fat_input_edit_user_goals.setText(userGoalObject.fat)
                    calories_input_edit_user_goals.setText(userGoalObject.calories)
                }
                catch(e: Exception){
                    //When user goals is null/empty
                }
            }

        })
    }//pullUserGoals function
}
