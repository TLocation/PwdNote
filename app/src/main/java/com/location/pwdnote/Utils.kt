package com.location.pwdnote

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity

/**
 *
 * @author tianxiaolong
 * time：2020/3/29 17:40
 * description：
 */
fun FragmentActivity.log(msg: String) {
    Log.i(javaClass.simpleName, msg)
}



const val SP_PIN = "pin"
private val SP by lazy {
    App.currentContext.getSharedPreferences(
        App.currentContext.packageName,
        Context.MODE_PRIVATE
    )
}


fun getSavePin() = SP.getString(SP_PIN, null)


fun savePin(pin: String) = SP.edit().putString(SP_PIN, pin).commit()


fun clearPin() = SP.edit().remove(SP_PIN).commit()

