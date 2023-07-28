package com.akash.sischatapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.FragmentProfileBinding
import com.akash.sischatapp.ui.EditProfile

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return binding!!.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.back.setOnClickListener {  }
        binding!!.editProfile.setOnClickListener {
            startActivity(Intent(context, EditProfile::class.java))
        }
    }
}