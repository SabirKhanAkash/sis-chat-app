package com.akash.sischatapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.sischatapp.databinding.FragmentEmptyHomeBinding
import com.akash.sischatapp.databinding.FragmentHomeBinding
import com.akash.sischatapp.model.User
import com.akash.sischatapp.ui.adapter.HomeChatAdapter
import com.akash.sischatapp.util.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import showTopToast

class HomeFragment : Fragment() {
    var user: User? = null
    private var binding: FragmentHomeBinding? = null
    private var empty_binding: FragmentEmptyHomeBinding? = null
    lateinit var database: FirebaseDatabase
    var users: ArrayList<User>? = null
    var homeChatAdapter: HomeChatAdapter? = null
    val loadingDialog: LoadingDialog = LoadingDialog(this@HomeFragment)
    var flag = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        empty_binding = FragmentEmptyHomeBinding.inflate(layoutInflater)
        loadingDialog.startFragmentLoading()

        if(flag == 1) {
            binding!!.noMsg.visibility = View.INVISIBLE
            database = FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app")
            users = ArrayList<User>()
            homeChatAdapter = HomeChatAdapter(requireContext(), users!!)
            binding!!.chatRv.layoutManager = LinearLayoutManager(requireContext())
            database.reference
                .child("users")
                .child(FirebaseAuth.getInstance().uid!!)
                .addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        loadingDialog.dismissLoading()
                        user = snapshot.getValue(User::class.java)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        loadingDialog.dismissLoading()
                        showTopToast(requireContext(), "Sorry! Something went wrong...", "short", "negative")
                    }

                })
            binding!!.chatRv.adapter = homeChatAdapter
            database.reference
                .child("users")
                .addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        loadingDialog.dismissLoading()
                        users!!.clear()
                        for (i in snapshot.children) {
                            val user:User? = i.getValue(User::class.java)
                            if(!user!!.uid.equals(FirebaseAuth.getInstance().uid))
                                users!!.add(user)
                        }
                        homeChatAdapter!!.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        loadingDialog.dismissLoading()
                        showTopToast(requireContext(), "Sorry! Something went wrong...", "short", "negative")
                    }

                })
        }

        return if(flag == 0) {
            loadingDialog.dismissLoading()
            empty_binding!!.root
        } else
            binding!!.root
    }
}