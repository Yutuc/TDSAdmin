package com.univation.tdsadmin.view_user_nutrition

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.FoodChoicesObject
import kotlinx.android.synthetic.main.activity_edit_user_food_choices.*

class EditUserFoodChoicesActivity : AppCompatActivity() {

    val userChosen = ChooseUserActivity.userChosen
    val foodChoicesObject = ViewUserNutritionActivity.foodChoicesObject!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_food_choices)
        setTitle(ViewUserNutritionActivity.typeChosen)

        when(ViewUserNutritionActivity.typeChosen){
            "Protein" -> {
                food_choices_input_edittext_edit_user_food_choices.setText(foodChoicesObject.protein)
            }
            "Carbohydrates" -> {
                food_choices_input_edittext_edit_user_food_choices.setText(foodChoicesObject.carbohydrates)
            }
            "Fat" -> {
                food_choices_input_edittext_edit_user_food_choices.setText(foodChoicesObject.fat)
            }
            "Vegetables" -> {
                food_choices_input_edittext_edit_user_food_choices.setText(foodChoicesObject.vegetables)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_user_food_choices_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.save_user_food_choices -> {
                val foodChoices = food_choices_input_edittext_edit_user_food_choices.text.toString().trim()
                if(foodChoices.isEmpty()){
                    Toast.makeText(this, "Please input food choices for the user", Toast.LENGTH_SHORT).show()
                }
                else{
                    saveUserFoodChoices()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveUserFoodChoices(){
        val foodChoicesInputted = food_choices_input_edittext_edit_user_food_choices.text.toString().trim()

        val ref = FirebaseDatabase.getInstance().getReference("/user-nutrition/${userChosen!!.uid}")
        when(ViewUserNutritionActivity.typeChosen){
            "Protein" -> {
                ref.child("protein").setValue(foodChoicesInputted)
                    .addOnSuccessListener {
                        //Toast.makeText(this, "Successfully edited protein choices", Toast.LENGTH_SHORT).show()
                        finish()
                    }
            }
            "Carbohydrates" -> {
                ref.child("carbohydrates").setValue(foodChoicesInputted)
                finish()
            }
            "Fat" -> {
                ref.child("fat").setValue(foodChoicesInputted)
                finish()
            }
            "Vegetables" -> {
                ref.child("vegetables").setValue(foodChoicesInputted)
                finish()
            }
        }
    }
}
