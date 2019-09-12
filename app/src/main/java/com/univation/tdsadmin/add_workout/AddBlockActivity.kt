package com.univation.tdsadmin.add_workout

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.ChooseUserActivity
import com.univation.tdsadmin.R
import com.univation.tdsadmin.adapters.BlockRow
import com.univation.tdsadmin.objects.BlockObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_add_block.*
import kotlinx.android.synthetic.main.add_block_alert_dialog.view.*

class AddBlockActivity : AppCompatActivity() {

    companion object {
        var blockClicked: BlockRow? = null
    }

    val blockArrayList = ArrayList<BlockObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("Select a block")

        AddWeekToBlockActivity.workoutDaysArrayList.clear()
        AddWeekToBlockActivity.adapter.clear()

        setContentView(R.layout.activity_add_block)
        recyclerview_add_block.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerview_add_block.adapter = adapter

        pullBlocks()

        adapter.setOnItemClickListener { item, _ ->
            blockClicked = item as BlockRow
            val intent = Intent(this, AddWeekToBlockActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pullBlocks(){
        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen?.uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val blockObject = p0.getValue(BlockObject::class.java)
                if(blockObject != null){
                    blockArrayList.add(blockObject)
                    refreshRecyclerView()
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullBlocks function

    private fun refreshRecyclerView(){
        adapter.clear()
        blockArrayList.forEach {
            adapter.add(BlockRow(it))
        }
    }//refreshRecyclerView function

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_block -> {
                val dialogBuilder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.add_block_alert_dialog, null)

                dialogBuilder.setView(dialogView)

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                dialogView.add_block_button_add_block_alert_dialog.setOnClickListener {
                    val blockName = dialogView.block_name_input_add_block_alert_dialog.text.toString().trim()
                    if(blockName.isEmpty()){
                        Toast.makeText(this, "Please enter a block name", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen?.uid}").push()
                        ref.setValue(BlockObject(ref.key!!, blockName, 0))
                        Toast.makeText(this, "Successfully created $blockName", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_block_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
