package com.univation.tdsadmin.view_check_ins

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.univation.tdsadmin.R

class ChooseBlockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_workouts_from_check_in)

        setTitle("Choose block")

        displayFragment(ChooseBlockFragment())
    }

    private fun displayFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }//displayFragment function
}
