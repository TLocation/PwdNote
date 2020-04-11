package com.location.pwdnote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.location.pwdnote.PwdData
import com.location.pwdnote.R
import com.location.pwdnote.decryRsa

/**
 *
 * @author tianxiaolong
 * time：2020/3/29 17:28
 * description：
 */
class HomeAdapter(val block: (position: Int) -> Unit) :
    ListAdapter<PwdData, HomeAdapter.HomeViewHolder>(HomeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_pwd,
                parent,
                false
            ), block = block
        )


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }


    fun addData(data: PwdData) {
        val tempList = ArrayList<PwdData>(currentList)
        tempList.add(data)
        submitList(tempList)
    }

    class HomeViewHolder(itemView: View, val block: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val textUserName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_username) }
        private val textPwdView: TextView by lazy { itemView.findViewById<TextView>(R.id.item_pwd) }

        init {
            itemView.setOnClickListener { block.invoke(adapterPosition) }
        }

        fun bindItem(value: PwdData) {
            if (value.lock) {
                value.lock = false
                value.userName = value.userName.decryRsa()
            }
            textUserName.text = value.title
            textPwdView.text = value.userName
        }
    }
}