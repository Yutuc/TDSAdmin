package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.univation.tdsadmin.objects.AdminUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser(){
        val email = email_edittext_login.text.toString()
        val password = password_edittext_login.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener {
            val uid = FirebaseAuth.getInstance().uid
            val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val currentUser = p0.getValue(AdminUser::class.java)
                    if(!currentUser?.admin!!){
                        Toast.makeText(applicationContext, "Unauthorized user", Toast.LENGTH_SHORT).show()
                        FirebaseAuth.getInstance().signOut()
                        return
                    }
                    Toast.makeText(applicationContext, "Succesfully logged in", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, ChooseActionActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK) //clears the stack of activities
                    startActivity(intent)
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
        }
    }//loginUser method
}
