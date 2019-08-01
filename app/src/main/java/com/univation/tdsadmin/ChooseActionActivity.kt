package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.univation.tdsadmin.add_workout.ChooseUserForAddWorkoutsActivity
import com.univation.tdsadmin.edit_user_goals.ChooseUserForEditUserGoalsActivity
import com.univation.tdsadmin.view_check_ins.ChooseUserForViewCheckInsActivity
import com.univation.tdsadmin.view_workouts.ChooseUserForViewWorkoutActivity
import kotlinx.android.synthetic.main.activity_choose_action.*

class ChooseActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_action)

        setTitle("Choose action")
        verifyUserIsLoggedIn()

        add_workout_button.setOnClickListener {
            val intent = Intent(this, ChooseUserForAddWorkoutsActivity::class.java)
            startActivity(intent)
        }

        view_check_ins_button.setOnClickListener {
            val intent = Intent(this, ChooseUserForViewCheckInsActivity::class.java)
            startActivity(intent)
        }

        view_workouts_button.setOnClickListener {
            val intent = Intent(this, ChooseUserForViewWorkoutActivity::class.java)
            startActivity(intent)
        }

        edit_user_goals_button_choose_action.setOnClickListener {
            val intent = Intent(this, ChooseUserForEditUserGoalsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun verifyUserIsLoggedIn(){
        if(FirebaseAuth.getInstance().uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK) //clears the stack of activities
            startActivity(intent)
        }
    }//verifyUserIsLoggedIn method

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.sign_out_top_nav_menu -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.choose_action_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
