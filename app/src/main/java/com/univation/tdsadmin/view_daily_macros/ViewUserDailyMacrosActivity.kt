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
import com.univation.tdsadmin.objects.DailyMacronutrientsAveragesObject
import com.univation.tdsadmin.objects.DailyMacronutrientsObject
import com.univation.tdsadmin.view_daily_macros_adapters.DailyMacronutrientsAveragesCard
import com.univation.tdsadmin.view_daily_macros_adapters.DailyMacronutrientsCard
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_user_daily_macros.*
import java.lang.Exception
import java.util.ArrayList

class ViewUserDailyMacrosActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()
    val dailyMacronutrientsArrayList = ArrayList<DailyMacronutrientsObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_daily_macros)
        setTitle("${ChooseUserActivity.userChosen!!.firstName} ${ChooseUserActivity.userChosen!!.lastName}'s Daily Macros")
        recyclerview_view_user_daily_macros.adapter = adapter

        pullDailyMacros()
    }

    private fun pullDailyMacros(){
        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacronutrientsArrayList[find(dailyMacronutrientsObject)] = dailyMacronutrientsObject
                    refreshRecyclerView()
                }catch (e: Exception){}
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacronutrientsArrayList.add(dailyMacronutrientsObject)
                    refreshRecyclerView()
                }catch (e: Exception){}
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacroHistory function

    private fun refreshRecyclerView(){
        adapter.clear()
        adapter.add(DailyMacronutrientsAveragesCard(macroAverages()))
        for (i in dailyMacronutrientsArrayList.size-1 downTo 0 step 1) {
            adapter.add(DailyMacronutrientsCard(dailyMacronutrientsArrayList[i]))
        }
    }//refreshRecyclerView function

    private fun macroAverages() : DailyMacronutrientsAveragesObject{
        var protein = 0f
        var carbohydrates = 0f
        var fat = 0f
        var calories = 0f
        var weight = 0f

        if(dailyMacronutrientsArrayList.size <= 7) {
            dailyMacronutrientsArrayList.forEach {
                try{
                    protein += it.protein.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    carbohydrates += it.carbohydrates.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    fat += it.fat.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    calories += it.calories.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    weight += it.weight.toFloat()
                }catch (e: java.lang.Exception){}
            }

            protein = protein.div(dailyMacronutrientsArrayList.size)
            carbohydrates = carbohydrates.div(dailyMacronutrientsArrayList.size)
            fat = fat.div(dailyMacronutrientsArrayList.size)
            calories = calories.div(dailyMacronutrientsArrayList.size)
            weight = weight.div(dailyMacronutrientsArrayList.size)
        }
        else{
            for (i in dailyMacronutrientsArrayList.size-1 downTo dailyMacronutrientsArrayList.size-7 step 1) {
                try{
                    protein += dailyMacronutrientsArrayList[i].protein.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    carbohydrates += dailyMacronutrientsArrayList[i].carbohydrates.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    fat += dailyMacronutrientsArrayList[i].fat.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    calories += dailyMacronutrientsArrayList[i].calories.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    weight += dailyMacronutrientsArrayList[i].weight.toFloat()
                }catch (e: java.lang.Exception){}
            }

            protein = protein.div(7)
            carbohydrates = carbohydrates.div(7)
            fat = fat.div(7)
            calories = calories.div(7)
            weight = weight.div(7)
        }

        val dailyMacronutrientsAveragesObject = DailyMacronutrientsAveragesObject("%.2f".format(protein), "%.2f".format(carbohydrates), "%.2f".format(fat), "%.2f".format(calories), "%.2f".format(weight))
        return dailyMacronutrientsAveragesObject
    }//macroAverages function

    private fun find(dailyMacronutrientsObject: DailyMacronutrientsObject) : Int {
        for (i in dailyMacronutrientsArrayList.size-1 downTo 0 step 1) {
            if(dailyMacronutrientsArrayList[i].key == dailyMacronutrientsObject.key){
                return i
            }
        }
        return -1
    }
}
