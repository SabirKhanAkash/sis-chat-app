package com.akash.sischatapp.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.sischatapp.databinding.ItemProfileBinding
import com.akash.sischatapp.model.User

class HomeChatAdapter(var context: Context, var userList: ArrayList<User>):
RecyclerView.Adapter<HomeChatAdapter.HomeChatViewHolder>()
{
    inner class HomeChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemProfileBinding = ItemProfileBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChatViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HomeChatViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}