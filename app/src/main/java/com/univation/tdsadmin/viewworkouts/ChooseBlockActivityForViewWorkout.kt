package com.univation.tdsadmin.viewworkouts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.UserObject
import com.univation.tdsadmin.viewcheckins.ChooseBlockActivity
import com.univation.tdsadmin.viewcheckins.ChooseBlockFragment
import com.univation.tdsadmin.viewcheckins.ChooseUserForViewCheckInsActivity

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
