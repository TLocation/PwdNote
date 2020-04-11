package com.location.pwdnote.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.location.pwdnote.*
import com.sun.base_lib.BaseFragment
import kotlinx.android.synthetic.main.fragment_update_pwd.*

/**
 *
 * @author tianxiaolong
 * time：2020/4/11 7:34 PM
 * description：
 */
class UpdateFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_update_pwd,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<PwdData>(UpdateDataActivity.EXERA_DATA)?.let {
            update_pwd.setText(it.pwd.decryRsa())
            update_title.setText(it.title)
            update_user.setText(it.userName)
        }
        update_btn.setOnClickListener {
            arguments?.getParcelable<PwdData>(UpdateDataActivity.EXERA_DATA)?.let {

                App.pwdDatabase.pwdDao().updateUsers(it.copy(userName = update_user.text.toString().encryRsa(),pwd = update_pwd.text.toString().encryRsa(),title = update_title.text.toString()))
            }
            activity?.finish()
        }
    }
}