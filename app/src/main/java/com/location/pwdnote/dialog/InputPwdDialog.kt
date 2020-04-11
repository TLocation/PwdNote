package com.location.pwdnote.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.location.pwdnote.*
import com.location.pwdnote.modle.MainViewModle

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 4:08 PM
 * description：
 */
class InputPwdDialog : DialogFragment() {

    private val userNameView by lazy { requireView().findViewById<EditText>(R.id.dialogUsername) }
    private val userPwdView by lazy { requireView().findViewById<EditText>(R.id.dialogPwd) }
    private val userTitleView by lazy { requireView().findViewById<EditText>(R.id.dialogTitle) }
    private val viewModle by activityViewModels<MainViewModle>()
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
                viewModle.savePwd(
                    PwdData(
                        userName.encryRsa(),
                        userPwdView.text.toString().encryRsa(),
                        title = userTitleView.text.toString()
                    )
                )
                dismiss()
            } else {
                toast("请输入用户名")
            }
        }

    }

}