package com.location.pwdnote.modle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.location.pwdnote.App
import com.location.pwdnote.PwdData

/**
 *
 * @author tianxiaolong
 * time：2020/4/5 9:18 PM
 * description：
 */
class MainViewModle:ViewModel() {

    fun getPwdLiveData():LiveData<List<PwdData>>{
        return App.pwdDatabase.pwdDao().queryPwdData()
    }

    
}