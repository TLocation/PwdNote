package com.location.pwdnote.widget

import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 5:34 PM
 * description：
 */
class RecyclerViewItemHelper private constructor(
    context: Context,
    private val rv: RecyclerView,
    private val listener: (view: View, position: Int) -> Unit
) : RecyclerView.OnItemTouchListener {
    companion object {
        fun addItemClick(view: RecyclerView, listener: (view: View, position: Int) -> Unit) {
            val helper = RecyclerViewItemHelper(view.context, view, listener)
            view.addOnItemTouchListener(helper)
        }
    }

    private val touchStore =
        GestureDetectorCompat(context, RecyclerViewItemTouchListener(this::checkItem))

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        touchStore.onTouchEvent(e)
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    private fun checkItem(e: MotionEvent?): Boolean {
        e?.let {
            val findChildViewUnder = rv.findChildViewUnder(it.x, it.y)
            if (findChildViewUnder != null) {
                val childViewHolder = rv.getChildViewHolder(findChildViewUnder)
                val adapterPosition = childViewHolder.adapterPosition
                listener.invoke(findChildViewUnder, adapterPosition)
                return true
            }
        }
        return false
    }

}