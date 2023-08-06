package com.akash.sischatapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akash.sischatapp.databinding.ActivityChatPageBinding

class ChatPage : AppCompatActivity() {
    private var binding: ActivityChatPageBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


    }
}