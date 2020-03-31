package com.location.pwdnote.widget

import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author tianxiaolong
 * time：2020/3/29 17:17
 * description：
 */
class FabScrollListener(private val listener: HideScrollListener) :
    RecyclerView.OnScrollListener() {

    private val THRESHOLD = 20
    private var distance = 0
    private var visible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (distance > THRESHOLD && visible) {
            visible = false
            listener.onHide()
            distance = 0
        } else if (distance < -THRESHOLD && !visible) {
            visible = true
            listener.onShow()
            distance = 0

        }

        if (visible && dy > 0 || (!visible && dy < 0)) {
            distance += dy
        }
    }

}