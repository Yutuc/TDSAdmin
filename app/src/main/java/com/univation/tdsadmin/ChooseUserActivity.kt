package com.univation.tdsadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class ChooseUserActivity : AppCompatActivity() {

    companion object {
        val USER_KEY = "USER_KEY"
        val adapter = GroupAdapter<ViewHolder>()
        var usersMap = HashMap<String, UserObject>()
        var usersMapCopy = HashMap<String, UserObject>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AddWorkoutActivity.workoutArrayList.clear()
        AddWorkoutActivity.warmupArrayList.clear()
        AddWorkoutActivity.dateChosen = ""

        setTitle("Select user to add to")

        adapter.setOnItemClickListener { item, view ->
            val userClicked = item as UserRow
            val intent = Intent(view.context, AddWorkoutActivity::class.java)
            intent.putExtra(USER_KEY, userClicked.user) //sends data(User object) from NewMessageActivity to ChatLogActivity
            startActivity(intent)
        }

        recyclerview_main.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerview_main.adapter = adapter

        verifyUserIsLoggedIn()
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
        val currentUser = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val newUser = p0.getValue(UserObject::class.java)
                if(newUser != null && currentUser != newUser.uid){
                    usersMap[p0.key!!] = newUser
                    usersMapCopy[p0.key!!] = newUser
                    refreshRecyclerView(usersMap)
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val changedUser = p0.getValue(UserObject::class.java)
                if(changedUser != null && currentUser != changedUser.uid){
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu_choose_user_activity, menu)

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
