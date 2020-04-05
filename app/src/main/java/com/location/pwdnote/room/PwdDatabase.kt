package com.location.pwdnote.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.location.pwdnote.PwdData

/**
 *
 * @author tianxiaolong
 * time：2020/4/5 6:09 PM
 * description：
 */
@Database(entities = arrayOf(PwdData::class),version = 1)
abstract  class PwdDatabase:RoomDatabase() {
    abstract fun pwdDao():PwdDao

}