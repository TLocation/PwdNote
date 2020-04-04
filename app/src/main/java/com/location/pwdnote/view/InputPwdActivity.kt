package com.location.pwdnote.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.location.pwdnote.*
import com.location.pwdnote.adapter.InputPwdAdapter
import kotlinx.android.synthetic.main.activity_input_pwd.*

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 4:52 PM
 * description：
 */
class InputPwdActivity : AppCompatActivity(R.layout.activity_input_pwd) {
    val MSG_DELETE = "删除"
    val HAS_PIN = getSavePin() != null
    private val ItemList =
        mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", MSG_DELETE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!HAS_PIN) {
            input_pwd_edittext.hint = "请输入初始pin码"
        }
        input_pwd_list.layoutManager = GridLayoutManager(this, 3)
        input_pwd_list.adapter = InputPwdAdapter(ItemList) { _, position ->
            if (position == ItemList.size - 1) {
                if (input_pwd_edittext.text.isNotEmpty()) {
                    val index = input_pwd_edittext.selectionStart
                    input_pwd_edittext.text.delete(index - 1, index)
                }
            } else {
                val s = ItemList[position]
                input_pwd_edittext.append(s)
            }
        }
//        BuildConfig
        input_pwd_confirm.setOnClickListener {
            if (input_pwd_edittext.length() < 4) {
                toast("pin码最少4位")
            } else {
                val pwd = input_pwd_edittext.text.toString()
                if (HAS_PIN) {
                    val b = pwd.toMd5() == getSavePin()
                    if (b) {
                        toast("pin输入正确")
                        startActivity(MainActivity::class.java)
                    } else {
                        toast("pin输入错误")
                    }
                } else {
                    val savePinState = savePin(pwd.toMd5())
                    toast(if (savePinState) "配置成功" else "配置失败")
                    if(savePinState){
                        startActivity(MainActivity::class.java)
                    }
                }
            }

        }


    }
}