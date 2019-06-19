package com.univation.tdsadmin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_check_ins.*

class ViewCheckInsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_check_ins)

        setTitle("${ChooseUserForCheckInsActivity.userChosen?.firstName} ${ChooseUserForCheckInsActivity.userChosen?.lastName}'s Check-In")
        pullCheckIn()
    }

    private fun pullCheckIn(){
        val ref = FirebaseDatabase.getInstance().getReference("/check-ins").child("${ChooseUserForCheckInsActivity.userChosen?.uid}")
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
        last_updated_textview.text = "Last Updated: \n ${updatedCheckIn.date}"
        question_one_answer_textview.text = updatedCheckIn.questionOne
        question_two_answer_textview.text = updatedCheckIn.questionTwo
        question_three_answer_textview.text = updatedCheckIn.questionThree
        question_four_answer_textview.text = updatedCheckIn.questionFour
        question_five_answer_textview.text = updatedCheckIn.questionFive
        question_six_answer_textview.text = updatedCheckIn.questionSix
        question_seven_answer_textview.text = updatedCheckIn.questionSeven
    }//displayCheckIn function
}
