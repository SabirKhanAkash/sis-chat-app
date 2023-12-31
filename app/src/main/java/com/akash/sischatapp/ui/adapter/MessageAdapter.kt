package com.akash.sischatapp.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ListviewReceiveMsgBinding
import com.akash.sischatapp.databinding.ListviewSendMsgBinding
import com.akash.sischatapp.model.Message
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class MessageAdapter(
    var context: Context,
    messages: ArrayList<Message>?,
    senderRoom: String,
    receiverRoom: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private lateinit var msgOptionPopUp: BottomSheetDialog
    lateinit var messages: ArrayList<Message>
    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2
    val senderRoom: String
    var receiverRoom: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.listview_send_msg, parent, false)
            SentMsgViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.listview_receive_msg, parent, false)
            ReceivedMsgViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val messages = messages[position]
        return if (FirebaseAuth.getInstance().uid == messages.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        val simple: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
        if (holder.javaClass == SentMsgViewHolder::class.java) {
            val viewHolder = holder as SentMsgViewHolder
            if (message.message.equals("photo")) {
                viewHolder.binding.chatBody.visibility = View.GONE
                viewHolder.binding.timestamp.visibility = View.GONE
                viewHolder.binding.mLinear.visibility = View.GONE
                viewHolder.binding.imageFl.visibility = View.VISIBLE
                viewHolder.binding.imgTimestamp.text = simple.format(Date(message.timeStamp))
                Glide
                    .with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .into(viewHolder.binding.img)
            }
            viewHolder.binding.chatBody.text = message.message
            viewHolder.binding.timestamp.text = simple.format(Date(message.timeStamp))
            viewHolder.itemView.setOnLongClickListener {
                msgOptionPopUp = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
                msgOptionPopUp.setContentView(R.layout.popup_message_option)
                msgOptionPopUp.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                msgOptionPopUp.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                msgOptionPopUp.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                msgOptionPopUp.window!!.setGravity(Gravity.CENTER)
                msgOptionPopUp.setCancelable(true)

                var _reply = msgOptionPopUp.window!!.findViewById<View>(R.id.reply_btn) as TextView
                var __copy = msgOptionPopUp.window!!.findViewById<View>(R.id.copy_btn) as TextView
                var __save =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.save_msg_btn) as TextView
                var __forward =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.forward_btn) as TextView
                var _delete =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.delete_btn) as TextView
                var _deleteAll =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.delete_all_btn) as TextView

                _deleteAll.setOnClickListener {
                    message.message = "This message is removed"
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!)
                            .setValue(message)
                    }
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(it1!!)
                            .setValue(message)
                    }
                    msgOptionPopUp.dismiss()
                }

                _delete.setOnClickListener {
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!)
                            .setValue(null)
                    }
                    msgOptionPopUp.dismiss()
                }

                msgOptionPopUp.show()
                false
            }
        } else {
            val viewHolder = holder as ReceivedMsgViewHolder
            if (message.message.equals("photo")) {
                viewHolder.binding.chatBody.visibility = View.GONE
                viewHolder.binding.timestamp.visibility = View.GONE
                viewHolder.binding.mLinear.visibility = View.GONE
                viewHolder.binding.imageFl.visibility = View.VISIBLE
                viewHolder.binding.imgTimestamp.text = simple.format(Date(message.timeStamp))
                Glide
                    .with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .into(viewHolder.binding.img)
            }
            viewHolder.binding.chatBody.text = message.message
            viewHolder.binding.timestamp.text = simple.format(Date(message.timeStamp))
            viewHolder.itemView.setOnLongClickListener {
                msgOptionPopUp = BottomSheetDialog(
                    context,
                    R.style.BottomSheetDialogTheme
                )
                msgOptionPopUp.setContentView(R.layout.popup_message_option)
                msgOptionPopUp.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                msgOptionPopUp.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                msgOptionPopUp.window!!.setGravity(Gravity.CENTER)
                msgOptionPopUp.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                msgOptionPopUp.setCancelable(true)

                var _reply = msgOptionPopUp.window!!.findViewById<View>(R.id.reply_btn) as TextView
                var __copy = msgOptionPopUp.window!!.findViewById<View>(R.id.copy_btn) as TextView
                var __save =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.save_msg_btn) as TextView
                var __forward =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.forward_btn) as TextView
                var _delete =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.delete_btn) as TextView
                var _deleteAll =
                    msgOptionPopUp.window!!.findViewById<View>(R.id.delete_all_btn) as TextView

                _deleteAll.setOnClickListener {
                    message.message = "This message is removed"
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!)
                            .setValue(message)
                    }
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(it1!!)
                            .setValue(message)
                    }
                    msgOptionPopUp.dismiss()
                }

                _delete.setOnClickListener {
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!)
                            .setValue(null)
                    }
                    msgOptionPopUp.dismiss()
                }

                msgOptionPopUp.show()
                false
            }
        }
    }

    inner class SentMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ListviewSendMsgBinding = ListviewSendMsgBinding.bind(itemView)
    }

    inner class ReceivedMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ListviewReceiveMsgBinding = ListviewReceiveMsgBinding.bind(itemView)
    }

    init {
        if (messages != null) {
            this.messages = messages
        }
        this.senderRoom = senderRoom
        this.receiverRoom = receiverRoom
    }
}