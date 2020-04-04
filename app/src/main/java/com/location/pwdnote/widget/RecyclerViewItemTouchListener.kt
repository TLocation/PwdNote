package com.location.pwdnote.widget

import android.view.GestureDetector
import android.view.MotionEvent

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 5:26 PM
 * description：
 */
class RecyclerViewItemTouchListener(private val touchListener:(e:MotionEvent?) -> Boolean) : GestureDetector.SimpleOnGestureListener(){
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return touchListener.invoke(e)
    }

}