package com.location.pwdnote.modle

import androidx.lifecycle.ViewModel
import com.location.pwdnote.getSavePin
import com.location.pwdnote.savePin

/**
 *
 * @author tianxiaolong
 * time：2020/4/5 9:25 PM
 * description：
 */
class InputModle : ViewModel() {
    private val savePin by lazy { getSavePin() }
    private val hasPin by lazy { savePin != null }
    private  val MSG_DELETE = "删除"
    val ItemList =
        mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", MSG_DELETE)

    fun checkPin(currentPin:String) = savePin == currentPin

    fun hasPin() = hasPin

    fun savePinPwd(pin: String) = savePin(pin)


}