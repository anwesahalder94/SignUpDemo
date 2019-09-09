package com.example.formapplication.user.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.formapplication.data.UserDbHandler
import com.example.formapplication.data.db.entity.UserEntity
import com.example.formapplication.data.repository.UserLocalRepository

class UserViewModel(val context: Context): ViewModel(), UserDbHandler {

    override fun insertIntoUserDetails(userDetails: UserEntity) {
        return UserLocalRepository.getInstance().insertUser(userDetails)
    }

    override fun getUserDetails(): LiveData<List<UserEntity>> {
        return UserLocalRepository.getInstance().getAllUserList()
    }

    override fun updateUserDetails(userDetails: UserEntity) {
        return UserLocalRepository.getInstance().updateUserDetails(userDetails)
    }

    /**
     * To instantiate a ViewModel class we need a NewInstanceFactory
     * It creates a new instance of the class
     */
    class Factory(val context: Context) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return UserViewModel(context) as T

        }
    }
}