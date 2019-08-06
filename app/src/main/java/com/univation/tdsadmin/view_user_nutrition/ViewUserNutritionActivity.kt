package com.univation.tdsadmin.view_user_nutrition

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.FoodChoicesObject
import com.univation.tdsadmin.view_user_food_choices_adapters.CarbohydrateChoicesColumn
import com.univation.tdsadmin.view_user_food_choices_adapters.FatChoicesColumn
import com.univation.tdsadmin.view_user_food_choices_adapters.ProteinChoicesColumn
import com.univation.tdsadmin.view_user_food_choices_adapters.VegetableChoicesColumn
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_user_nutrition.*

class ViewUserNutritionActivity : AppCompatActivity() {

    companion object {
        var typeChosen = ""
    }

    val userChosen = ChooseUserActivity.userChosen
    val userClicked = ChooseUserActivity.userChosen
    val foodChoicesMap = HashMap<String, String>()
    val foodChoicesAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_nutrition)
        setTitle("Edit ${userClicked!!.firstName} ${userClicked.lastName}'s Nutrition")
        recyclerview_view_user_food_choices.adapter = foodChoicesAdapter

        foodChoicesAdapter.setOnItemLongClickListener { item, _ ->
            when (item) {
                is ProteinChoicesColumn -> typeChosen = "Protein"
                is CarbohydrateChoicesColumn -> typeChosen = "Carbohydrates"
                is FatChoicesColumn -> typeChosen = "Fat"
                else -> typeChosen = "Vegetables"
            }
            val intent = Intent(this, EditUserFoodChoicesActivity::class.java)
            startActivity(intent)
            true
        }

        pullFoodChoices()
    }

    private fun pullFoodChoices(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-nutrition/${userChosen!!.uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val foodChoicesPulled = p0.getValue(String::class.java)!!
                foodChoicesMap.set(p0.key!!, foodChoicesPulled)
                refreshRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val foodChoicesPulled = p0.getValue(String::class.java)!!
                foodChoicesMap[p0.key!!] = foodChoicesPulled
                if(foodChoicesMap.size == 4){
                    refreshRecyclerView()
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullFoodChoices function

    private fun refreshRecyclerView(){
        foodChoicesAdapter.clear()
        foodChoicesAdapter.add(ProteinChoicesColumn(foodChoicesMap.get("protein")!!))
        foodChoicesAdapter.add(CarbohydrateChoicesColumn(foodChoicesMap.get("carbohydrates")!!))
        foodChoicesAdapter.add(FatChoicesColumn(foodChoicesMap.get("fat")!!))
        foodChoicesAdapter.add(VegetableChoicesColumn(foodChoicesMap.get("vegetables")!!))
    }//refreshRecyclerView function

}
