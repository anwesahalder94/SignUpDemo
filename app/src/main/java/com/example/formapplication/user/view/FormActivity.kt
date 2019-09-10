package com.example.formapplication.user.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.formapplication.R
import com.example.formapplication.application.FormApplication
import com.example.formapplication.data.db.database.UserDatabase
import com.example.formapplication.data.db.entity.UserEntity
import com.example.formapplication.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserDetails: UserEntity
    private lateinit var mUserDatabase: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        mUserDatabase = UserDatabase.getInstance(FormApplication.applicationContext())!!
        mUserViewModel =
            ViewModelProviders.of(this, UserViewModel.Factory(FormApplication.context)).get(UserViewModel::class.java)

        mUserDetails = UserEntity()

        setCustId()
        setEventListener()
    }

    /**
     * setting customer id in textview after getting last value of customer id and incrementing by 1
     * the user list button will be visible if there is at least one non-empty row in the list
     */
    private fun setCustId() {
        mUserViewModel.getUserDetails().observe(this, Observer { userList ->
            if (userList.size == 1) {
                if (userList[0].cust_name.equals("")) {
                    text_customer_id.text = "1"
                    button_user_list.visibility = View.GONE

                } else {
                    button_user_list.visibility = View.VISIBLE
                }
            } else {
                button_user_list.visibility = View.VISIBLE
            }
            if (userList.size > 1) {
                for (i in 0 until userList.size) {
                    text_customer_id.text = (userList[userList.size - 1].cust_id + 1).toString()
                }
            }
        })
    }

    private fun setEventListener() {

        //actions on selecting each value in spinner
        spinner_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.selectedItem.toString()
                when {
                    selectedItem.equals(resources.getString(R.string.individual)) -> {
                        mUserDetails.cust_type = resources.getString(R.string.individual)
                    }

                    selectedItem.equals(resources.getString(R.string.group)) -> {
                        mUserDetails.cust_type = resources.getString(R.string.group)
                    }
                    else -> {
                        mUserDetails.cust_type = ""
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        button_submit.setOnClickListener {

            //mUserDetails = UserEntity(edit_name.text.toString(),edit_customer_id.text.toString(),edit_address.text.toString(),edit_phone.text.toString(),"Group")

            if (isFieldValid()) {
                setData()

                //getUserList()

                insertData()
                clearData()
                Toast.makeText(this, "Successfully Inserted in Db", Toast.LENGTH_LONG).show()
            }
        }

        button_user_list.setOnClickListener {

            startActivity(Intent(this, AllUserActivity::class.java))
        }
    }

    /**
     * updating first row in database if the size of the list is one and the other fields are empty
     * instead of the above condition insert the data in the database
     */
    private fun insertData() {
        mUserViewModel.getUserDetails().observe(this, Observer { userList ->
            mUserDetails.cust_id = text_customer_id.text.toString().toInt()
            if (userList.size == 1) {
                if (userList[0].cust_name.equals("")) {
                    button_user_list.visibility = View.GONE
                    mUserViewModel.updateUserDetails(mUserDetails)
                } else {
                    mUserViewModel.insertIntoUserDetails(mUserDetails)
                }
            } else {
                mUserViewModel.insertIntoUserDetails(mUserDetails)
            }
            text_customer_id.text = (mUserDetails.cust_id + 1).toString()
            button_user_list.visibility = View.VISIBLE
        })
    }

    //making the edit texts blank after uploading data to mUserDatabase
    private fun clearData() {
        edit_name?.text!!.clear()
        edit_address?.text!!.clear()
        edit_phone?.text!!.clear()
        edit_name.requestFocus()
    }

    // setting data in UserEntity model
    private fun setData() {
        mUserDetails.cust_name = edit_name.text.toString()
        mUserDetails.cust_add = edit_address.text.toString()
        mUserDetails.cust_phn = edit_phone.text.toString()
    }

    //validating if any field is empty or not.
    private fun isFieldValid(): Boolean {

        if (edit_name!!.text.toString().trim().isEmpty()) {
            edit_name.error = "Customer name should not be blank"
            edit_name.requestFocus()
            return false
        }

        if (edit_address.text!!.toString().trim().isEmpty()) {
            edit_address.error = "Customer address should not be blank"
            edit_address.requestFocus()
            return false
        }

        if (edit_phone.text!!.toString().trim().isEmpty()) {
            edit_phone.error = "Customer phone number should not be blank"
            edit_phone.requestFocus()
            return false
        }

        if (text_customer_id.text.toString().trim().equals(edit_phone.text!!.toString().trim())) {
            edit_phone.error = "Customer phone number should not be equal to customer id"
            edit_phone.requestFocus()
            return false
        }

        if (spinner_type.selectedItem.toString().trim().equals("Select Customer Type")) {
            Toast.makeText(this, "Choose something from dropdown", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}

