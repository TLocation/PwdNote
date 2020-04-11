package com.location.pwdnote.modle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.location.pwdnote.App
import com.location.pwdnote.PwdData
import com.location.pwdnote.decryRsa

/**
 *
 * @author tianxiaolong
 * time：2020/4/5 9:18 PM
 * description：
 */
class MainViewModle : ViewModel() {
    private val pwdDao by lazy { App.pwdDatabase.pwdDao() }
    private val queryLiveData by lazy { pwdDao.queryPwdData() }
    fun getPwdLiveData(): LiveData<List<PwdData>> {
        return queryLiveData
    }

    fun savePwd(pwdData: PwdData) {
        pwdDao.insertUsers(pwdData)
    }

    fun delete(pwdData: PwdData){
        pwdDao.delete(pwdData)
    }


    fun showRealPwd(pwdData: PwdData) = pwdData.pwd.decryRsa()


}