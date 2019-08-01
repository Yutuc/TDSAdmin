package com.univation.tdsadmin.view_workouts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.univation.tdsadmin.R

class ChooseBlockActivityForViewWorkout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_workouts)

        setTitle("Choose block")

        displayFragment(ChooseBlockFragmentForViewWorkout())
    }

    private fun displayFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }//displayFragment function
}
