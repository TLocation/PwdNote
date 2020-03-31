package com.location.pwdnote

/**
 *
 * @author tianxiaolong
 * time：2020/3/28 16:29
 * description：
 */
data class PwdData(var userName:String,var pwd:String,val id:Int,val pkg:String?,val name:String) {
    constructor( userName:String, pwd:String, id:Int,name:String):this(userName, pwd, id, null,name)
}