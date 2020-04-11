package com.location.pwdnote.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.location.pwdnote.PwdData
import com.location.pwdnote.R
import com.location.pwdnote.startUIActivity

/**
 *
 * @author tianxiaolong
 * time：2020/4/11 7:23 PM
 * description：
 */
class UpdateDataActivity : AppCompatActivity(R.layout.activity_update_pwd) {

    companion object{
        const val  EXERA_DATA  = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       supportFragmentManager.commit {
           val bundleOf = bundleOf(
               EXERA_DATA to intent.getParcelableExtra<PwdData>(
                   EXERA_DATA
               )
           )
           add(R.id.frame_layout,UpdateFragment::class.java,bundleOf)
       }

    }

}