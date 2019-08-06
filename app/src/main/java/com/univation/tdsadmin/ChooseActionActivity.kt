package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.univation.tdsadmin.add_workout.AddBlockActivity
import com.univation.tdsadmin.view_user_nutrition.ViewUserNutritionActivity
import com.univation.tdsadmin.edit_user_goals.EditUserGoalsActivity
import com.univation.tdsadmin.view_check_ins.ViewCheckInsActivity
import com.univation.tdsadmin.view_workouts.ChooseBlockActivityForViewWorkout
import kotlinx.android.synthetic.main.activity_choose_action.*

class ChooseActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_action)

        setTitle("Choose action")

        add_workout_button.setOnClickListener {
            val intent = Intent(this, AddBlockActivity::class.java)
            startActivity(intent)
        }

        view_check_ins_button.setOnClickListener {
            val intent = Intent(this, ViewCheckInsActivity::class.java)
            startActivity(intent)
        }

        view_workouts_button.setOnClickListener {
            val intent = Intent(this, ChooseBlockActivityForViewWorkout::class.java)
            startActivity(intent)
        }

        edit_user_goals_button_choose_action.setOnClickListener {
            val intent = Intent(this, EditUserGoalsActivity::class.java)
            startActivity(intent)
        }

        edit_user_nutrition_choices_button_choose_action.setOnClickListener {
            val intent = Intent(this, ViewUserNutritionActivity::class.java)
            startActivity(intent)
        }
    }
}
