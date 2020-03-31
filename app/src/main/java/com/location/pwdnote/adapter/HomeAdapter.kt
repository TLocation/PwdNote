package com.location.pwdnote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.location.pwdnote.PwdData
import com.location.pwdnote.R

/**
 *
 * @author tianxiaolong
 * time：2020/3/29 17:28
 * description：
 */
class HomeAdapter(private val data: MutableList<PwdData>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_pwd,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val pwdData = data[position]
        holder.apply {
            textPwdView.text = pwdData.userName
            textUserName.text = pwdData.name
        }
    }

    fun addData(pwdData:PwdData){
        data.add(pwdData)
        notifyItemInserted(data.size-1)
    }
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textUserName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_username) }
        val textPwdView: TextView by lazy { itemView.findViewById<TextView>(R.id.item_pwd) }
    }
}