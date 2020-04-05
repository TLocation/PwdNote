package com.location.pwdnote.view

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.location.pwdnote.*
import com.location.pwdnote.adapter.HomeAdapter
import com.location.pwdnote.dialog.InputPwdDialog
import com.location.pwdnote.modle.MainViewModle
import com.location.pwdnote.widget.FabScrollListener
import com.location.pwdnote.widget.HideScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HideScrollListener, View.OnClickListener {


    private val viewModle by lazy { ViewModelProviders.of(this)[MainViewModle::class.java] }
    private val adapter by lazy { HomeAdapter() }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeRecyclerView.layoutManager = LinearLayoutManager(this)
        homeRecyclerView.addOnScrollListener(FabScrollListener(this))
        homeRecyclerView.adapter = adapter
        home_btn.setOnClickListener(this)
        viewModle.getPwdLiveData().observe(this, Observer<List<PwdData>> {
            adapter.submitList(it)
        })
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
        dialog.show(supportFragmentManager, "InputPwdDialog")
    }


}
