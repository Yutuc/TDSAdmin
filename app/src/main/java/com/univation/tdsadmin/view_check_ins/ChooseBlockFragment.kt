package com.univation.tdsadmin.view_check_ins

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_choose_block_from_check_in.view.*

class ChooseBlockFragment : Fragment() {

    companion object {
        var blockClicked: BlockRow? = null
    }

    val blockArrayList = ArrayList<BlockObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_choose_block_from_check_in, container, false)
        pullBlocks()
        view.recyclerview_choose_block.adapter = adapter
        view.recyclerview_choose_block.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.setOnItemClickListener { item, _ ->
            val intent = Intent(context, ChooseWeekActivity::class.java)
            blockClicked = item as BlockRow
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun pullBlocks(){
        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${ChooseUserActivity.userChosen?.uid}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val blockObject = p0.getValue(BlockObject::class.java)!!
                blockArrayList.add(blockObject)
                refreshRecyclerView()
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
}
