package com.akash.sischatapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ItemProfileBinding
import com.akash.sischatapp.model.User
import com.bumptech.glide.Glide

class HomeChatAdapter(var context: Context, var userList: ArrayList<User>):
RecyclerView.Adapter<HomeChatAdapter.HomeChatViewHolder>()
{
    inner class HomeChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemProfileBinding = ItemProfileBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false)
        return HomeChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        val x = userList.size
        return userList.size
    }

    override fun onBindViewHolder(holder: HomeChatViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.chatUserName.text = user.fullName
        holder.binding.chatMsgHint.text = user.bio
        Glide
            .with(context)
            .load(user.profileImage)
            .placeholder(R.drawable.upload_icon)
            .into(holder.binding.chatDp)
    }
}