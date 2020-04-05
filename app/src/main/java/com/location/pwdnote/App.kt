package com.location.pwdnote

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.location.pwdnote.room.PwdDatabase

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 8:08 PM
 * description：
 */
class App : Application() {
    companion object{
        lateinit var currentContext:Context
        val pwdDatabase by lazy { Room.databaseBuilder(currentContext, PwdDatabase::class.java, "pwd.db").allowMainThreadQueries()
            .build() }
    }
    override fun onCreate() {
        super.onCreate()
        currentContext  = this
    }
}