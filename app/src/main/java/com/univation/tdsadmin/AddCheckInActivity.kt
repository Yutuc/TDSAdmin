package com.univation.tdsadmin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_add_check_in.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddCheckInActivity : AppCompatActivity() {

    companion object{
        val timeArrayList = ArrayList<CheckInTimeAdapter>()
        val adapter = GroupAdapter<ViewHolder>()
        var dateChosen = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_check_in)
        timeArrayList.clear()
        adapter.clear()
        check_in_card_recyclerview.adapter = adapter
        setTitle("Add check-in")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.date_picker_add_menu_check_in -> {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog =
                    DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        calendar.set(Calendar.YEAR, mYear)
                        calendar.set(Calendar.MONTH, mMonth)
                        calendar.set(Calendar.DAY_OF_MONTH, mDay)
                        dateChosen = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
                    }, year, month, day)
                datePickerDialog.show()
            }

            R.id.time_add_menu_check_in -> {
                var timeChosen = ""
                val selectedTime = Calendar.getInstance()
                var timeFormat = SimpleDateFormat("hh:mm a", Locale.CANADA)
                val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    timeChosen = timeFormat.format(selectedTime.time).toUpperCase()
                    if(!timeChosen.isEmpty()){
                        timeArrayList.add(CheckInTimeAdapter(ScheduledTimeObject(timeArrayList.size,"",
                            dateChosen, timeChosen, "")))
                        refreshRecyclerView()
                        timeChosen = ""
                    }
                },
                    selectedTime.get(Calendar.HOUR_OF_DAY), selectedTime.get(Calendar.MINUTE), false)
                refreshRecyclerView()
                timePicker.show()
            }

            R.id.save_to_user_menu_check_in -> {
                saveToUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu_add_check_in, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun saveToUser(){
        if(dateChosen.isEmpty()){
            Toast.makeText(this, "No date chosen", Toast.LENGTH_SHORT).show()
            return
        }
        else if(timeArrayList.isEmpty()){
            Toast.makeText(this, "No dates added", Toast.LENGTH_SHORT).show()
            return
        }
        val ref = FirebaseDatabase.getInstance().getReference("/check-in-page/$dateChosen")
        timeArrayList.forEach {
            val key = ref.push()
            it.availableTimeObject.key = key.key!!
            key.setValue(it.availableTimeObject)
        }
        timeArrayList.clear()
        refreshRecyclerView()
        Toast.makeText(this, "Successfully saved to server", Toast.LENGTH_SHORT).show()
    }//saveToUser function

    private fun refreshRecyclerView(){
        adapter.clear()
        timeArrayList.forEach {
            adapter.add(it)
        }
    }//refreshRecyclerView function
}
