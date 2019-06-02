package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_choose_action.*

class ChooseActionActivity : AppCompatActivity() {

    companion object{
        val USER_KEY = "USER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_action)

        setTitle("Choose action")

        val userChosen = intent.getParcelableExtra<UserObject>(ChooseUserActivity.USER_KEY)
        add_workout_button.setOnClickListener {
            val intent = Intent(this, ChooseUserActivity::class.java)
            intent.putExtra(USER_KEY, userChosen)
            startActivity(intent)
        }
        add_check_in_button.setOnClickListener {
            val intent = Intent(this, AddCheckInActivity::class.java)
            intent.putExtra(USER_KEY, userChosen)
            startActivity(intent)
        }

        view_scheduled_check_ins_button.setOnClickListener {
            val intent = Intent(this, ViewScheduledCheckInsActivity::class.java)
            startActivity(intent)
        }
    }

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
        menuInflater.inflate(R.menu.top_nav_menu_choose_action_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
