package com.example.formapplication.user.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formapplication.R
import com.example.formapplication.application.FormApplication
import com.example.formapplication.data.db.database.UserDatabase
import com.example.formapplication.data.db.entity.UserEntity
import com.example.formapplication.user.adapter.UserAdapter
import com.example.formapplication.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_user_list.*
import java.util.ArrayList
import androidx.recyclerview.widget.DividerItemDecoration


class AllUserActivity : AppCompatActivity() {

    private lateinit var mUserAdapter: UserAdapter
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserDetails: UserEntity
    private var db: UserDatabase? = null
    private var individualListFiltered: MutableList<UserEntity> = ArrayList()
    private var groupListFiltered: MutableList<UserEntity> = ArrayList()
    private var updatedList: MutableList<UserEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        db = UserDatabase.getInstance(FormApplication.applicationContext())
        mUserViewModel =
            ViewModelProviders.of(this, UserViewModel.Factory(FormApplication.context)).get(UserViewModel::class.java)
        mUserDetails = UserEntity()

        initView()
        setEventListener()
        getUserList()

    }
    //on pulling to refresh the updated list will be fetched from the database
    private fun setEventListener() {
        pull_to_refresh.setOnRefreshListener {
            getUserList()
            pull_to_refresh!!.isRefreshing = false
        }
    }

    // fetching the whole list from database
    fun getUserList() {
        mUserViewModel.getUserDetails().observe(this, Observer { userList ->
            updatedList.clear()
            groupListFiltered.clear()
            individualListFiltered.clear()
            userList.forEachIndexed { i, _ ->
                //fetching the rows and putting it into individualListFiltered arraylist which have type = individual
                if (userList[i].cust_type == resources.getString(R.string.individual)) {
                    individualListFiltered.add(userList[i])

                }
                //fetching the rows and putting it into individualListFiltered arraylist which have type = group
                else if (userList[i].cust_type == resources.getString(R.string.group)) {
                    groupListFiltered.add(userList[i])
                }
            }
            updatedList.addAll(groupListFiltered)
            updatedList.addAll(individualListFiltered)

            setAdapter(updatedList)
        })
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        rv_user_list?.setHasFixedSize(true)
        rv_user_list?.layoutManager = layoutManager
        rv_user_list.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }


    /*
    * To view User List an adapter has been set
    * */
    private fun setAdapter(userList: List<UserEntity>) {
        mUserAdapter = UserAdapter(userList, mUserViewModel,this)
        rv_user_list?.adapter = mUserAdapter
        mUserAdapter.notifyDataSetChanged()
    }
}