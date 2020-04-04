package com.location.pwdnote.view

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.location.pwdnote.PwdData
import com.location.pwdnote.R
import com.location.pwdnote.SqliteHelper
import com.location.pwdnote.adapter.HomeAdapter
import com.location.pwdnote.dialog.InputPwdDialog
import com.location.pwdnote.log
import com.location.pwdnote.widget.FabScrollListener
import com.location.pwdnote.widget.HideScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HideScrollListener, View.OnClickListener {


    private lateinit var sqliteHelper: SqliteHelper

    private lateinit var adapter: HomeAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sqliteHelper = SqliteHelper.getInstance(
            applicationContext,
            "test.db",
            "test"
        )
        val pwdList = sqliteHelper.query()
        homeRecyclerView.layoutManager = LinearLayoutManager(this)
        homeRecyclerView.addOnScrollListener(FabScrollListener(this))
        adapter = HomeAdapter()
        homeRecyclerView.adapter = adapter
        home_btn.setOnClickListener(this)
        adapter.submitList(pwdList)
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
          val dialog = InputPwdDialog()
        dialog.liveData.observe(this,saveUserDataObserver)
        dialog.show(supportFragmentManager,"test")

    }

    // 保存用户信息
    private val saveUserDataObserver = Observer<PwdData> {
           adapter.addData(sqliteHelper.insert(it))
    }


}
