package com.univation.tdsadmin.viewcheckins

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.UserObject

class ViewUserWorkoutsFromCheckInActivity : AppCompatActivity() {

    companion object {
        var uid = ""
        var userName = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_workouts_from_check_in)

        uid = ChooseUserForViewCheckInsActivity.userChosen?.uid!!
        pullUserName()
        displayFragment(WorkoutFragmentForCheckIns())
    }

    private fun pullUserName(){
        val ref = FirebaseDatabase.getInstance().getReference("/users/").child("$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(UserObject::class.java)
                userName = "${user?.firstName} ${user?.lastName}"
                setTitle("$userName's Workouts")
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }//pullUserName function

    private fun displayFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }//displayFragment function
}