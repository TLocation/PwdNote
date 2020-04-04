package com.location.pwdnote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.location.pwdnote.R

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 5:01 PM
 * description：
 */
class InputPwdAdapter(val dataList: List<String>,val itemClick:(view:View,position:Int) -> Unit) :
    RecyclerView.Adapter<InputPwdAdapter.InputHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InputHolder.createViewHolder(parent,itemClick)

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: InputHolder, position: Int) =
        holder.bindItem(dataList[position])

    class InputHolder(view: View,val itemClick:(view:View,position:Int) -> Unit) : RecyclerView.ViewHolder(view) {

        init {

            itemView.setOnClickListener {
                itemClick.invoke(itemView,adapterPosition)
            }
        }
        fun bindItem(content: String) {
            if (itemView is TextView) {
                itemView.text = content
            }
        }

        companion object {
            fun createViewHolder(view: ViewGroup, itemClick:(view:View,position:Int) -> Unit) = InputHolder(
                LayoutInflater.from(view.context).inflate(
                    R.layout.item_input_pwd,
                    view,
                    false
                )
            ,itemClick)
        }
    }


}