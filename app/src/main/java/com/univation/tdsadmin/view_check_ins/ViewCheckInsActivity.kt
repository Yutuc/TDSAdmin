package com.univation.tdsadmin.view_check_ins

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.*
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.CheckInObject
import kotlinx.android.synthetic.main.activity_view_check_ins.*

class ViewCheckInsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_check_ins)

        setTitle("${ChooseUserForViewCheckInsActivity.userChosen?.firstName} ${ChooseUserForViewCheckInsActivity.userChosen?.lastName}'s Check-In")
        pullCheckIn()
    }

    private fun pullCheckIn(){
        val ref = FirebaseDatabase.getInstance().getReference("/check-ins").child("${ChooseUserForViewCheckInsActivity.userChosen?.uid}")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val updatedCheckIn = p0.getValue(CheckInObject::class.java)
                if(updatedCheckIn != null){
                    displayCheckIn(updatedCheckIn)
                }
                else{
                    Toast.makeText(baseContext, "No check-in found", Toast.LENGTH_SHORT).show()
                    return
                }
            }

        })
    }//pullCheckIn function

    private fun displayCheckIn(updatedCheckIn: CheckInObject){
        last_updated_textview.text = "Last Updated\n${updatedCheckIn.date}"
        question_one_answer_textview.text = updatedCheckIn.questionOne
        question_two_answer_textview.text = updatedCheckIn.questionTwo
        question_three_answer_textview.text = updatedCheckIn.questionThree
        question_four_answer_textview.text = updatedCheckIn.questionFour
        question_five_answer_textview.text = updatedCheckIn.questionFive
        question_six_answer_textview.text = updatedCheckIn.questionSix
        question_seven_answer_textview.text = updatedCheckIn.questionSeven
    }//displayCheckIn function

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.view_user_workout_check_in_top_nav_menu -> {
                val intent = Intent(this, ChooseBlockActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.check_in_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
