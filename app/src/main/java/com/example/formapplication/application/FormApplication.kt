package com.example.formapplication.application

import android.app.Application
import android.content.Context
import com.example.formapplication.data.db.database.UserDatabase

class FormApplication: Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext()
    }

    companion object {
        private var instance: FormApplication? = null

        lateinit var context: Context

        lateinit var database: UserDatabase

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        fun getAppContext() : Context {
            return context;
        }
    }
}