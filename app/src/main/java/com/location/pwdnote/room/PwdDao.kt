package com.location.pwdnote.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.location.pwdnote.PwdData

/**
 *
 * @author tianxiaolong
 * time：2020/4/5 6:11 PM
 * description：
 */
@Dao
interface PwdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers( pwdData: PwdData):Long
    @Update
    fun updateUsers( pwdData: PwdData)
    @Delete
    fun deleteUsers( pwdData: PwdData)

    @Query("SELECT * from pwd_table")
    fun queryPwdData():LiveData<List<PwdData>>
}