package com.univation.tdsadmin.view_daily_macros

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.DailyMacronutrientsObject
import com.univation.tdsadmin.view_daily_macros_adapters.DailyMacronutrientsCard
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_daily_macronutrients.*
import java.util.ArrayList

class ViewDailyMacronutrientsActivity : AppCompatActivity() {

    val userChosen = ChooseUserActivity.userChosen!!
    val adapter = GroupAdapter<ViewHolder>()
    val dailyMacroNutrientsHistoryArrayList = ArrayList<DailyMacronutrientsObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_daily_macronutrients)
        setTitle("${userChosen!!.firstName} ${userChosen.lastName}'s Daily Macros")
        recyclerview_daily_macros.adapter = adapter
        pullDailyMacroHistory()
    }

    private fun pullDailyMacroHistory(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-daily-macro-history/${userChosen.uid}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                dailyMacroNutrientsHistoryArrayList.add(dailyMacronutrientsObject)
                refreshRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacroHistory function

    private fun refreshRecyclerView(){
        adapter.clear()
        dailyMacroNutrientsHistoryArrayList.forEach {
            adapter.add(DailyMacronutrientsCard(it))
        }
    }//refreshRecyclerView function
}
