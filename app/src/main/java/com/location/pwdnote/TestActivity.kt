package com.location.pwdnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

/**
 *
 * @author tianxiaolong
 * time：2020/4/6 8:24 PM
 * description：
 */
class TestActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
setContentView(R.layout.test_nav)
//        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.test_fragment))
    }

    override fun onResume() {
        super.onResume()

    }
}