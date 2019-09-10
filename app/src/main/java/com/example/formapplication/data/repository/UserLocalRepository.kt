package com.example.formapplication.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.formapplication.application.FormApplication
import com.example.formapplication.data.db.database.UserDatabase
import com.example.formapplication.data.db.entity.UserEntity

class UserLocalRepository {

    private var mUserDatabase: UserDatabase ? = null

    companion object {

        private var sUserLocalRepository: UserLocalRepository? = null

        @Synchronized
        @JvmStatic
        fun getInstance(): UserLocalRepository {
            if (sUserLocalRepository == null)
                sUserLocalRepository =
                    UserLocalRepository()
            return sUserLocalRepository!!
        }
    }

    init {
        mUserDatabase = UserDatabase.getInstance(FormApplication.applicationContext())
    }

    fun insertUser(insertUser: UserEntity){
        object : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                mUserDatabase!!.userDao().insertUser(insertUser)
                return null
            }
        }.execute()
    }

    fun getAllUserList(): LiveData<List<UserEntity>>{
        var userList = MutableLiveData<List<UserEntity>>()
        object : AsyncTask<Void, Void, List<UserEntity>>(){
            override fun doInBackground(vararg p0: Void?): List<UserEntity> {
                return mUserDatabase!!.userDao().getAllUserDetails()
            }
            override fun onPostExecute(result: List<UserEntity>?) {
                return userList.postValue(result)
            }
        }.execute()
        return  userList
    }

    fun updateUserDetails(updateUser: UserEntity){
        object : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                mUserDatabase!!.userDao().updateUserDetails(updateUser)
                return null
            }
        }.execute()
    }
}