package com.location.pwdnote.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.location.pwdnote.R
import com.location.pwdnote.getSavePin
import com.location.pwdnote.startActivity

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 10:02 PM
 * description：
 */
class SplashActivity : AppCompatActivity(R.layout.activity_spash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(getSavePin() != null){
            startActivity(InputPwdActivity::class.java)
            return
        }


    }
}