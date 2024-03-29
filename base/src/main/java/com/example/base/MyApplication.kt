package com.example.base

import android.app.Application
import android.content.Context
import com.example.base.MyApplication

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        val context: Context?
            get() = instance
    }
}