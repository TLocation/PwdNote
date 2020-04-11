package com.location.pwdnote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 10:14 PM
 * description：
 */

fun toast(msg:String) = Toast.makeText(App.currentContext,msg,Toast.LENGTH_SHORT).show()

fun FragmentActivity.startActivity(clazz: Class<out Activity>, bundle: Bundle? = null){
    val intent = Intent(this,clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}
fun Fragment.startUiActivity(clazz:Class<out Activity>){
    startActivity(Intent(activity,clazz))
}

inline fun <reified T:Activity> FragmentActivity.startUIActivity(){
    startActivity(Intent(this,T::class.java))
}

