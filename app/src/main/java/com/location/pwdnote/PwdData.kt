package com.location.pwdnote

import androidx.room.*

/**
 *
 * @author tianxiaolong
 * time：2020/3/28 16:29
 * description：
 */

@Entity(tableName = "pwd_table")
data class PwdData(
    var userName: String,
    var pwd: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val pkg: String? = null,
    val title: String,
    @Ignore
    var lock:Boolean = true
){
    constructor(   userName: String,
                   pwd: String,
                   id: Long = 0,
                   pkg: String? = null,
                   title: String
    ):this(userName, pwd, id, pkg, title,true)
}