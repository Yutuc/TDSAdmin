package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsadmin.adapters.UserRow
import com.univation.tdsadmin.objects.UserObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_choose_user.*

class ChooseUserActivity : AppCompatActivity() {

    companion object {
        val adapter = GroupAdapter<ViewHolder>()
        var usersMap = HashMap<String, UserObject>()
        var usersMapCopy = HashMap<String, UserObject>()
        var userChosen: UserObject? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
        setTitle("Select user")
        verifyUserIsLoggedIn()

        adapter.setOnItemClickListener { item, _ ->
            val userClicked = item as UserRow
            val intent = Intent(this, ChooseActionActivity::class.java)
            userChosen = userClicked.user
            startActivity(intent)
        }

        recyclerview_choose_user.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerview_choose_user.adapter = adapter

        pullUsers()
    }

    private fun verifyUserIsLoggedIn(){
        if(FirebaseAuth.getInstance().uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK) //clears the stack of activities
            startActivity(intent)
        }
    }//verifyUserIsLoggedIn method

    private fun pullUsers(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val newUser = p0.getValue(UserObject::class.java)
                if(newUser != null){
                    usersMap[p0.key!!] = newUser
                    usersMapCopy[p0.key!!] = newUser
                    refreshRecyclerView(usersMap)
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val changedUser = p0.getValue(UserObject::class.java)
                if(changedUser != null){
                    usersMap[p0.key!!] = changedUser
                    usersMapCopy[p0.key!!] = changedUser
                    refreshRecyclerView(usersMap)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val removedUser = p0.getValue(UserObject::class.java)
                if(removedUser != null){
                    usersMap.remove(p0.key!!)
                    usersMapCopy.remove(p0.key!!)
                    refreshRecyclerView(usersMap)
                }
            }
        })
    }//pullUsers function

    private fun refreshRecyclerView(adapterMap: HashMap<String, UserObject>){
        adapter.clear()
        adapterMap.values.forEach {
            adapter.add(UserRow(it))
        }
    }//refreshRecyclerView function

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.sign_out_top_nav_menu -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.choose_user_activity_menu, menu)

        //searches the recyclerview
        val searchIcon = menu?.findItem(R.id.search_top_nav_menu)
        if(searchIcon != null){
            val searchView = searchIcon.actionView as SearchView
            val searchViewEditText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            searchViewEditText.hint = "Search User"
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        val search = newText.toLowerCase()
                        usersMapCopy.clear()
                        usersMap.values.forEach{
                            if((it.firstName + " " + it.lastName).toLowerCase().contains(search)){
                                usersMapCopy[it.uid] = it
                            }
                        }
                        refreshRecyclerView(usersMapCopy)
                    }
                    else{
                        refreshRecyclerView(usersMap)
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}