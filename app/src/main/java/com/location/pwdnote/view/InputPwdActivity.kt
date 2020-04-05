package com.location.pwdnote.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.location.pwdnote.R
import com.location.pwdnote.adapter.InputPwdAdapter
import com.location.pwdnote.modle.InputModle
import com.location.pwdnote.startActivity
import com.location.pwdnote.toMd5
import com.location.pwdnote.toast
import kotlinx.android.synthetic.main.activity_input_pwd.*

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 4:52 PM
 * description：
 */
class InputPwdActivity : AppCompatActivity(R.layout.activity_input_pwd) {


    private val viewModle by lazy { ViewModelProviders.of(this)[InputModle::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!viewModle.hasPin()) {
            input_pwd_edittext.hint = "请输入初始pin码"
        }
        input_pwd_list.layoutManager = GridLayoutManager(this, 3)
        input_pwd_list.adapter = InputPwdAdapter(viewModle.ItemList) { _, position ->
            itemClick(position)
        }
        input_pwd_confirm.setOnClickListener {
            confirmClick()
        }


    }

    private fun itemClick(position: Int) {
        if (position == viewModle.ItemList.size - 1) {
            if (input_pwd_edittext.text.isNotEmpty()) {
                val index = input_pwd_edittext.selectionStart
                input_pwd_edittext.text.delete(index - 1, index)
            }
        } else {
            val s = viewModle.ItemList[position]
            input_pwd_edittext.append(s)
        }
    }

    private fun confirmClick() {
        if (input_pwd_edittext.length() < 4) {
            toast("pin码最少4位")
        } else {
            val pwd = input_pwd_edittext.text.toString()
            if (viewModle.hasPin()) {
                val b = viewModle.checkPin(pwd.toMd5())
                if (b) {
                    toast("pin输入正确")
                    startActivity(MainActivity::class.java)
                    finish()
                } else {
                    toast("pin输入错误")
                    input_pwd_edittext.setText("")
                }
            } else {
                val savePinState = viewModle.savePinPwd(pwd.toMd5())
                toast(if (savePinState) "配置成功" else "配置失败")
                if (savePinState) {
                    startActivity(MainActivity::class.java)
                    finish()
                }
            }
        }
    }
}