package com.univation.tdsadmin.view_user_nutrition

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.MealObject
import kotlinx.android.synthetic.main.activity_edit_meal.*

class EditMealActivity : AppCompatActivity() {

    val mealChosen = ViewUserNutritionActivity.mealChosen!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)
        setTitle("Edit meal")

        meal_name_input_edit_meal.setText(mealChosen.mealName)
        protein_input_edit_meal.setText(mealChosen.protein)
        carbohydrates_input_edit_meal.setText(mealChosen.carbohydrates)
        fat_input_edit_meal.setText(mealChosen.fat)
        vegetables_input_edit_meal.setText(mealChosen.vegetables)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_meal_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.save_meal -> {
                val mealName = meal_name_input_edit_meal.text.toString().trim()
                val protein = protein_input_edit_meal.text.toString().trim()
                val carbohydrates = carbohydrates_input_edit_meal.text.toString().trim()
                val fat = fat_input_edit_meal.text.toString().trim()
                val vegetables = vegetables_input_edit_meal.text.toString().trim()

                when {
                    mealName.isEmpty() -> {
                        Toast.makeText(this, "Please enter a meal name", Toast.LENGTH_SHORT).show()
                    }
                    protein.isEmpty() -> {
                        Toast.makeText(this, "Please enter protein", Toast.LENGTH_SHORT).show()
                    }
                    carbohydrates.isEmpty() -> {
                        Toast.makeText(this, "Please enter carbohydrates", Toast.LENGTH_SHORT).show()
                    }
                    fat.isEmpty() -> {
                        Toast.makeText(this, "Please enter fat", Toast.LENGTH_SHORT).show()
                    }
                    vegetables.isEmpty() -> {
                        Toast.makeText(this, "Please enter vegetables", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        saveMeal()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveMeal(){
        val mealName = meal_name_input_edit_meal.text.toString().trim()
        val protein = protein_input_edit_meal.text.toString().trim()
        val carbohydrates = carbohydrates_input_edit_meal.text.toString().trim()
        val fat = fat_input_edit_meal.text.toString().trim()
        val vegetables = vegetables_input_edit_meal.text.toString().trim()

        val ref = FirebaseDatabase.getInstance().getReference("/user-meals/${ChooseUserActivity.userChosen!!.uid}/${mealChosen.key}")
        val mealObject = MealObject(ref.key!!, mealName, protein, carbohydrates, fat, vegetables)
        ref.setValue(mealObject)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully edited meal", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }//saveMeal function
}