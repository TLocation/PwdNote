package com.location.pwdnote

import android.app.Application
import android.content.Context

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 8:08 PM
 * description：
 */
class App : Application() {
    companion object{
        lateinit var currentContext:Context
    }
    override fun onCreate() {
        super.onCreate()
        currentContext  = this
    }
}