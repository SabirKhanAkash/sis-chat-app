package com.akash.sischatapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akash.sischatapp.databinding.FragmentEmptyHomeBinding
import com.akash.sischatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private var empty_binding: FragmentEmptyHomeBinding? = null
    var flag = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        empty_binding = FragmentEmptyHomeBinding.inflate(layoutInflater)

        if(flag == 1) {
            binding!!.noMsg.visibility = View.INVISIBLE
        }

        return if(flag == 0)
            empty_binding!!.root
        else
            binding!!.root
    }
}