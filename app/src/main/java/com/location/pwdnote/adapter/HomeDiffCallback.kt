package com.location.pwdnote.adapter

import androidx.recyclerview.widget.DiffUtil
import com.location.pwdnote.PwdData

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 3:44 PM
 * description：
 */
class HomeDiffCallback:DiffUtil.ItemCallback<PwdData>() {
    override fun areItemsTheSame(oldItem: PwdData, newItem: PwdData) = newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: PwdData, newItem: PwdData)= oldItem == newItem
}