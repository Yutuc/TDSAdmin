package com.univation.tdsadmin.view_user_nutrition

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.*
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.FoodChoicesObject
import com.univation.tdsadmin.objects.MealObject
import com.univation.tdsadmin.view_user_nutrition_adapters.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_user_nutrition.*
import kotlinx.android.synthetic.main.edit_or_delete_alert_dialog.view.*

class ViewUserNutritionActivity : AppCompatActivity() {

    companion object {
        var typeChosen = ""
        var foodChoicesObject: FoodChoicesObject? = null
        var mealChosen: MealObject? = null
    }

    val userChosen = ChooseUserActivity.userChosen
    val userClicked = ChooseUserActivity.userChosen

    var foodChoicesClickedPosition = 0
    val foodChoicesMap = HashMap<String, String>()
    val foodChoicesAdapter = GroupAdapter<ViewHolder>()

    val mealsAdapter = GroupAdapter<ViewHolder>()
    val mealArrayList = ArrayList<MealObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_nutrition)
        setTitle("Edit ${userClicked!!.firstName} ${userClicked.lastName}'s Nutrition")
        recyclerview_food_choices.adapter = foodChoicesAdapter

        foodChoicesAdapter.setOnItemLongClickListener { item, _ ->
            when (item) {
                is ProteinChoicesColumn -> {
                    typeChosen = "Protein"
                    foodChoicesClickedPosition = 0
                }
                is CarbohydrateChoicesColumn -> {
                    typeChosen = "Carbohydrates"
                    foodChoicesClickedPosition = 1
                }
                is FatChoicesColumn -> {
                    typeChosen = "Fat"
                    foodChoicesClickedPosition = 2
                }
                else -> {
                    typeChosen = "Vegetables"
                    foodChoicesClickedPosition = 3
                }
            }
            val intent = Intent(this, EditUserFoodChoicesActivity::class.java)
            startActivity(intent)
            true
        }

        recyclerview_meals.adapter = mealsAdapter
        mealsAdapter.add(MacrosPerMealTitlesRow())

        mealsAdapter.setOnItemLongClickListener { item, _ ->
            try{
                val mealRowChosen = item as MealRow
                mealChosen = mealRowChosen.mealObject

                val dialogBuilder = AlertDialog.Builder(this)
                var dialogView = layoutInflater.inflate(R.layout.edit_or_delete_alert_dialog, null)
                dialogBuilder.setView(dialogView)

                val editOrDeleteAlertDialog = dialogBuilder.create()!!
                editOrDeleteAlertDialog.show()

                dialogView.edit_button_edit_or_delete_alert_dialog.setOnClickListener {
                    val intent = Intent(this, EditMealActivity::class.java)
                    startActivity(intent)
                    editOrDeleteAlertDialog.dismiss()
                }

                dialogView.delete_button_edit_or_delete_alert_dialog.setOnClickListener {
                    val ref = FirebaseDatabase.getInstance().getReference("/user-meals/${ChooseUserActivity.userChosen!!.uid}/${mealChosen!!.key}")
                    ref.removeValue()
                    editOrDeleteAlertDialog.dismiss()
                }
            }
            catch(e: Exception){}

            true
        }

        pullFoodChoices()
        pullMeals()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_user_nutrition_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_meal -> {
                val intent = Intent(this, AddMealActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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
                refreshFoodChoicesRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val foodChoicesPulled = p0.getValue(String::class.java)!!
                foodChoicesMap[p0.key!!] = foodChoicesPulled
                if(foodChoicesMap.size == 4){
                    refreshFoodChoicesRecyclerView()
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullFoodChoices function

    private fun pullMeals(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-meals/${ChooseUserActivity.userChosen!!.uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val mealObject = p0.getValue(MealObject::class.java)!!
                mealArrayList.set(find(mealObject.key), mealObject)
                refreshMealsRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val mealObject = p0.getValue(MealObject::class.java)!!
                mealArrayList.add(mealObject)
                refreshMealsRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                try {
                    val mealRemoved = p0.getValue(MealObject::class.java)!!
                    mealArrayList.removeAt(find(mealRemoved.key))
                    refreshMealsRecyclerView()
                }
                catch(e: Exception){}
            }

        })
    }//pullMeals function

    private fun find(key: String) : Int{
        for(i in mealArrayList.indices){
            if(key == mealArrayList[i].key){
                return i
            }
        }
        return -1
    }//find function

    private fun refreshFoodChoicesRecyclerView(){
        foodChoicesAdapter.clear()
        foodChoicesObject = FoodChoicesObject(foodChoicesMap.get("protein")!!, foodChoicesMap.get("carbohydrates")!!, foodChoicesMap.get("fat")!!, foodChoicesMap.get("vegetables")!!)
        foodChoicesAdapter.add(ProteinChoicesColumn(foodChoicesMap.get("protein")!!))
        foodChoicesAdapter.add(CarbohydrateChoicesColumn(foodChoicesMap.get("carbohydrates")!!))
        foodChoicesAdapter.add(FatChoicesColumn(foodChoicesMap.get("fat")!!))
        foodChoicesAdapter.add(VegetableChoicesColumn(foodChoicesMap.get("vegetables")!!))
    }//refreshRecyclerView function

    private fun refreshMealsRecyclerView(){
        mealsAdapter.clear()
        mealsAdapter.add(MacrosPerMealTitlesRow())
        mealArrayList.forEach {
            mealsAdapter.add(MealRow(it))
        }
    }//refreshMealsRecyclerView function

    override fun onResume() {
        recyclerview_food_choices.scrollToPosition(foodChoicesClickedPosition)
        super.onResume()
    }

}
