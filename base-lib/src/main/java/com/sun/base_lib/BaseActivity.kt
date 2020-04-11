package com.sun.base_lib

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

/**
 *
 * @author tianxiaolong
 * time：2020/4/6 8:01 PM
 * description：
 */
class BaseActivity<T:ViewModel>(@LayoutRes layoutId:Int) :AppCompatActivity(layoutId) {


}