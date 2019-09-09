package com.example.formapplication.data

import androidx.lifecycle.LiveData
import com.example.formapplication.data.db.entity.UserEntity

interface UserDbHandler {
    fun insertIntoUserDetails(userDetails: UserEntity)
    fun getUserDetails(): LiveData<List<UserEntity>>
    fun updateUserDetails(userDetails: UserEntity)
}