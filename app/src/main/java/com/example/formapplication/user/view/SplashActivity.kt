package com.example.formapplication.user.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.formapplication.R
import com.example.formapplication.application.FormApplication
import com.example.formapplication.data.db.database.UserDatabase
import com.example.formapplication.data.db.entity.UserEntity
import com.example.formapplication.user.viewmodel.UserViewModel

/**
 * SplashActivity is used to show the first screen when the app launches
 */
class SplashActivity: AppCompatActivity() {

    private var mUserDatabase: UserDatabase? = null
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserDetails: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //for making the SplashActivity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash)

        // creating the database
        mUserDatabase = UserDatabase.getInstance(applicationContext)

        mUserViewModel =
            ViewModelProviders.of(this, UserViewModel.Factory(FormApplication.context)).get(UserViewModel::class.java)

        //inserting a blank data in database
        mUserDetails = UserEntity(1,"","","","")
        mUserViewModel.insertIntoUserDetails(mUserDetails)

        //for delaying some specific amount of time(3sec) to open the next activity
        Handler().postDelayed({
            startActivity(Intent(this, FormActivity::class.java))
            finish()
        }, (3 * 1000).toLong())
    }
}