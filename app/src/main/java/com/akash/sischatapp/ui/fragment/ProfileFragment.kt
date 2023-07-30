package com.akash.sischatapp.ui.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.FragmentProfileBinding
import com.akash.sischatapp.ui.EditProfile
import com.akash.sischatapp.ui.Login
import com.akash.sischatapp.util.SharedPref
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val sharedPref: SharedPref = SharedPref()
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        binding!!.phoneNo.text = FirebaseAuth.getInstance().currentUser!!.phoneNumber
        binding!!.fullname.text = sharedPref.getString(requireContext(), "full_name")
        binding!!.username.text = sharedPref.getString(requireContext(), "user_name")
        binding!!.bio.text = sharedPref.getString(requireContext(), "bio")

        if (!sharedPref.getString(requireContext(), "dp").isNullOrEmpty()) {
            Glide.with(this@ProfileFragment)
                .load(sharedPref.getString(requireContext(), "dp"))
                .placeholder(R.drawable.upload_icon)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding!!.uploadBtn.setImageResource(0)
                        binding!!.uploadBtn.setBackgroundResource(0)
                        binding!!.uploadBtn.setImageResource(R.drawable.upload_icon)
                        binding!!.loader.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding!!.loader.visibility = View.GONE
                        return false
                    }
                })
                .into(binding!!.uploadBtn)
        } else {
            Glide.with(this@ProfileFragment)
                .load(R.drawable.upload_icon)
                .placeholder(R.drawable.upload_icon)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding!!.uploadBtn.setImageResource(0)
                        binding!!.uploadBtn.setBackgroundResource(0)
                        binding!!.uploadBtn.setImageResource(R.drawable.upload_icon)
                        binding!!.loader.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding!!.loader.visibility = View.GONE
                        return false
                    }
                })
                .into(binding!!.uploadBtn)
        }

        binding!!.editProfile.setOnClickListener {
            startActivity(Intent(context, EditProfile::class.java))
        }
        binding!!.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            sharedPref.clearData(requireContext())
            startActivity(
                Intent(
                    requireContext(),
                    Login::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }

    }
}