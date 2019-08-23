package com.univation.tdsadmin.view_daily_macros

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.objects.BlockObject
import com.univation.tdsapplication.workout_adapters.BlockRow
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_select_daily_macro_block.*
import java.util.ArrayList

class SelectDailyMacroBlockActivity : AppCompatActivity() {

    companion object {
        var blockClicked: BlockObject? = null
    }

    val userChosen = ChooseUserActivity.userChosen!!
    val adapter = GroupAdapter<ViewHolder>()
    val dailyMacroNutrientBlocksHistoryArrayList = ArrayList<BlockObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_daily_macro_block)
        setTitle("${userChosen.firstName} ${userChosen.lastName}'s Daily Macros")
        recyclerview_daily_macro_blocks.adapter = adapter
        recyclerview_daily_macro_blocks.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, _ ->
            val blockRow = item as BlockRow
            blockClicked = blockRow.blockObject
            val intent = Intent(this, ViewDailyMacroBlockActivity::class.java)
            startActivity(intent)
        }

        pullDailyMacroHistory()
    }

    private fun pullDailyMacroHistory(){
        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${userChosen.uid}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val blockObject = p0.getValue(BlockObject::class.java)!!
                dailyMacroNutrientBlocksHistoryArrayList.add(blockObject)
                refreshRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacroHistory function

    private fun refreshRecyclerView(){
        adapter.clear()
        dailyMacroNutrientBlocksHistoryArrayList.forEach {
            adapter.add(BlockRow(it))
        }
    }//refreshRecyclerView function
}
