package com.location.pwdnote

import android.content.ContentValues
import android.content.Context
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper

/**
 *
 * @author tianxiaolong
 * time：2020/3/28 15:43
 * description：
 */
 class SqliteHelper private constructor(val context:Context, val dbName:String,val pwd:String): SQLiteOpenHelper(context,dbName,null,1){

    companion object{
        private lateinit var context:Context
        private lateinit var name:String
        private lateinit var pwd:String

        private  val instacne:SqliteHelper by lazy {  SqliteHelper(context, name,pwd)}
         fun getInstance(context: Context,name:String,pwd:String):SqliteHelper{
            if(!this::context.isInitialized || !this::name.isInitialized){
                this.context = context
                this.name = name
                this.pwd = pwd
                SQLiteDatabase.loadLibs(context)
            }
            return instacne;
        }
    }
    //表名字
    val TABLE_NAME = "pwd"
    //用户名
    val USER_NAME = "userName"
    //密码
    val EXERA_PWD = "pwd"
    //id 自增
    val EXERA_ID = "id"
    //名字
    val EXERA_TITLE = "title"
    //时间
    val EXERA_TIME = "time"
    val COLUMN_PKG = "pkg"


    val SQL_MSG = "create table ${TABLE_NAME}($EXERA_ID integer primary key autoincrement," +
            "$USER_NAME Text," +
            "$EXERA_PWD Text," +
            "$EXERA_TITLE Text,"+
            "$EXERA_TIME long," +
            "$COLUMN_PKG Text" +
            ")"
    var db:SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_MSG)
        this.db = db
    }


    fun insert(userName:String,pwd:String,title:String):PwdData{
        checkDb()
        val values = ContentValues(4)
        return values.run {
            put(USER_NAME,userName)
            put(EXERA_PWD,pwd)
            put(EXERA_TITLE,title)
            put(EXERA_TIME,System.currentTimeMillis())
            val id =  db!!.insert(TABLE_NAME,null,values).toInt()
            PwdData(userName, pwd, id,title = title)
        }

    }
    fun insert(data:PwdData):PwdData{
        checkDb()
        val values = ContentValues(4)
        return values.run {
            put(USER_NAME,data.userName)
            put(EXERA_PWD,data.pwd)
            put(EXERA_TITLE,data.title)
            put(EXERA_TIME,System.currentTimeMillis())
            val id = db!!.insert(TABLE_NAME,null,values).toInt()
            data.copy(id = id)
        }
    }

    fun query():MutableList<PwdData>{
        checkDb()
        val list = ArrayList<PwdData>()
        val query = db!!.query(TABLE_NAME, null, null, null, null, null, null)
        while (query.moveToNext()){
            list.add(query.run {
                val username = getString(getColumnIndex(USER_NAME))
                val pwd = getString(getColumnIndex(EXERA_PWD))
                val id = getInt(getColumnIndex(EXERA_ID))
                val name = getString(getColumnIndex(EXERA_TITLE))
                PwdData(userName = username,pwd = pwd,id = id,title = name)
            })
        }
        query.close()
        return list
    }

    private fun checkDb() {
        try {
            if(db == null) db = getWritableDatabase(pwd)
        } catch (e: Exception) {
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


