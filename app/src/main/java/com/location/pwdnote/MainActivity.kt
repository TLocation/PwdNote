package com.location.pwdnote

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.location.pwdnote.adapter.HomeAdapter
import com.location.pwdnote.widget.FabScrollListener
import com.location.pwdnote.widget.HideScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HideScrollListener, View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var dialog_userName: TextView? = null
    private var dialog_userPwd: TextView? = null
    private lateinit var sqliteHelper: SqliteHelper

    private lateinit var adapter: HomeAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sqliteHelper = SqliteHelper.getInstance(applicationContext, "test.db", "test")
        val pwdList = sqliteHelper.query()
        homeRecyclerView.layoutManager = LinearLayoutManager(this)
        homeRecyclerView.addOnScrollListener(FabScrollListener(this))
        adapter = HomeAdapter(pwdList)
        homeRecyclerView.adapter = adapter
        home_btn.setOnClickListener(this)

    }

    override fun onHide() {
        home_btn.visibility = View.GONE
        log("onHide")
    }

    override fun onShow() {
        log("onShow")
        home_btn.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        if (dialog == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_save_pwd, null);
            dialog = AlertDialog.Builder(this)
                .setTitle(R.string.add_pwd)
                .setView(view)
                .setPositiveButton(
                    "保存"
                ) { dialog, which ->
                    saveData(dialog)
                }
                .setNegativeButton("取消") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            dialog_userName = view.findViewById<TextView>(R.id.dialogUsername);
            dialog_userPwd = view.findViewById<TextView>(R.id.dialogPwd);

        }

        dialog!!.show()
    }

    private fun saveData(dialog: DialogInterface) {
        val data = sqliteHelper.insert(
            dialog_userName!!.text.toString(),
            dialog_userPwd!!.text.toString()
        ,"test")
        adapter.addData(data)
        dialog.dismiss()
        dialog_userName!!.text = ""
        dialog_userPwd!!.text = ""
    }
}
