package com.example.formapplication.data.repository

import android.database.Cursor
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.formapplication.application.FormApplication
import com.example.formapplication.data.db.database.UserDatabase
import com.example.formapplication.data.db.entity.UserEntity

class UserLocalRepository {

    private var db: UserDatabase ? = null

    companion object {

        private var userLocalRepository: UserLocalRepository? = null

        @Synchronized
        @JvmStatic
        fun getInstance(): UserLocalRepository {
            if (userLocalRepository == null)
                userLocalRepository =
                    UserLocalRepository()
            return userLocalRepository!!
        }
    }

    init {
        db = UserDatabase.getInstance(FormApplication.applicationContext())
    }

    fun insertUser(insertUser: UserEntity){
        object : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                db!!.userDao().insertUser(insertUser)
                return null
            }
        }.execute()
    }

    fun getAllUserList(): LiveData<List<UserEntity>>{
        var userList = MutableLiveData<List<UserEntity>>()
        object : AsyncTask<Void, Void, List<UserEntity>>(){
            override fun doInBackground(vararg p0: Void?): List<UserEntity> {
                return db!!.userDao().getAllUserDetails()
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
                db!!.userDao().updateUserDetails(updateUser)
                return null
            }
        }.execute()
    }
}