package com.location.pwdnote.dialog

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.location.pwdnote.PwdData
import com.location.pwdnote.R
import com.location.pwdnote.modle.MainViewModle
import com.location.pwdnote.toast
import kotlinx.android.synthetic.main.dialog_show_pwd.*


/**
 *
 * @author tianxiaolong
 * time：2020/4/6 2:47 PM
 * description：
 */
class PwdDetailsDialog : DialogFragment() {
    companion object {
        const val EXERA_DATA = "data"
    }


    private val viewModle by activityViewModels<MainViewModle>()

    private val pwdData by lazy { arguments?.getParcelable<PwdData>(EXERA_DATA)!! }

    private val copyManager  by lazy { requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_show_pwd, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pwdData.apply {
            dialog_show_title.text = title
            dialog_show_username.text = getString(R.string.show_details_username, userName)
            dialog_show_pwd.text = getString(R.string.show_details_pwd, viewModle.showRealPwd(this))
        }


        dialog_show_delete.setOnClickListener {
            viewModle.delete(pwdData)
            dismiss()
        }
        dialog_show_copy_user.setOnClickListener {
            val mClipData = ClipData.newPlainText("copyUser", pwdData.userName)
            copyManager.setPrimaryClip(mClipData)
            toast(getString(R.string.toast_copy))
        }
        dialog_show_copy_pwd.setOnClickListener {
            val mClipData = ClipData.newPlainText("copyPwd",  viewModle.showRealPwd(pwdData))
            copyManager.setPrimaryClip(mClipData)
            toast(getString(R.string.toast_copy))
        }


    }

}