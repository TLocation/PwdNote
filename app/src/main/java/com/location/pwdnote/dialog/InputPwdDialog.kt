package com.location.pwdnote.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.location.pwdnote.App
import com.location.pwdnote.PwdData
import com.location.pwdnote.R
import com.location.pwdnote.encryRsa

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 4:08 PM
 * description：
 */
class InputPwdDialog : DialogFragment() {

    val userNameView by lazy { view!!.findViewById<EditText>(R.id.dialogUsername) }
    val userPwdView by lazy { view!!.findViewById<EditText>(R.id.dialogPwd) }
    val userTitleView by lazy { view!!.findViewById<EditText>(R.id.dialogTitle) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_save_pwd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.dialog_btn_cancel).setOnClickListener { dismiss() }
        view.findViewById<Button>(R.id.dialog_btn_confirm).setOnClickListener {
            val userName = userNameView?.text.toString()
            if (userName.isNotEmpty()) {
                App.pwdDatabase.pwdDao().insertUsers(PwdData(
                    userName.encryRsa(),
                    userPwdView.text.toString().encryRsa(),
                    title = userTitleView.text.toString()
                ))
                dismiss()
            }
        }

    }

}