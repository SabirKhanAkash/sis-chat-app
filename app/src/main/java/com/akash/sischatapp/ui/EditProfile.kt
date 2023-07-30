package com.akash.sischatapp.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityEditProfileBinding
import com.akash.sischatapp.model.User
import com.akash.sischatapp.util.LoadingDialog
import com.akash.sischatapp.util.SharedPref
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import showTopToast
import java.util.Date

class EditProfile : AppCompatActivity() {
    var binding: ActivityEditProfileBinding? = null
    private val sharedPref: SharedPref = SharedPref()
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val loadingDialog = LoadingDialog(this@EditProfile)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database =
            FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app")

        binding!!.fullNameEt.setText(sharedPref.getString(applicationContext, "full_name"))
        binding!!.usernameEt.setText(sharedPref.getString(applicationContext, "user_name"))
        binding!!.bioEt.setText(sharedPref.getString(applicationContext, "bio"))

        if (!sharedPref.getString(applicationContext, "dp").isNullOrEmpty()) {
            Glide.with(this@EditProfile)
                .load(sharedPref.getString(applicationContext, "dp"))
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
            Glide.with(this@EditProfile)
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


        binding!!.back.setOnClickListener { onBackPressed() }
        binding!!.uploadBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 46)
        }
        binding!!.continueBtn.setOnClickListener {
            loadingDialog.startLoading()
            if (selectedImage != null) {
                val reference = storage!!.reference.child("Profile").child(auth!!.uid!!)
                reference.putFile(selectedImage!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        reference.downloadUrl.addOnCompleteListener { uri ->
                            val imageUri = uri.result.toString()
                            val uid = auth!!.uid
                            val phone = auth!!.currentUser!!.phoneNumber
                            val fullName: String = binding!!.fullNameEt.text.toString()
                            val userName: String = binding!!.usernameEt.text.toString()
                            val bio: String = binding!!.bioEt.text.toString()
                            val user = User(uid, fullName, userName, bio, phone, imageUri)
                            try {
                                database!!.reference
                                    .child("users")
                                    .child(uid!!)
                                    .setValue(user)
                                    .addOnCompleteListener { userTask ->
                                        loadingDialog.dismissLoading()
                                        if (userTask.isSuccessful) {
                                            startActivity(
                                                Intent(
                                                    this@EditProfile,
                                                    AppBottomNav::class.java
                                                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            )
                                            showTopToast(
                                                applicationContext,
                                                "Profile updated successfully!",
                                                "short",
                                                "positive"
                                            )
                                        } else {
                                            showTopToast(
                                                applicationContext,
                                                "Something went wrong! Try again",
                                                "short",
                                                "negative"
                                            )
                                        }
                                    }
                                    .addOnFailureListener {
                                        showTopToast(
                                            applicationContext,
                                            "Something went wrong! Try again",
                                            "short",
                                            "negative"
                                        )
                                    }
                            } catch (e: Exception) {
                                showTopToast(
                                    applicationContext,
                                    e.printStackTrace().toString(),
                                    "long",
                                    "neutral"
                                )
                            }
                        }
                    } else {
                        val uid = auth!!.uid
                        val phone = auth!!.currentUser!!.phoneNumber
                        val fullName: String = binding!!.fullNameEt.text.toString()
                        val userName: String = binding!!.usernameEt.text.toString()
                        val bio: String = binding!!.bioEt.text.toString()
                        val user = User(uid, fullName, userName, bio, phone, "No Image")
                        loadingDialog.dismissLoading()
                        database!!.reference
                            .child("users")
                            .child(uid!!)
                            .setValue(user)
                            .addOnCanceledListener {
//                                loadingDialog.dismissLoading()
                                startActivity(
                                    Intent(
                                        this@EditProfile,
                                        AppBottomNav::class.java
                                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                )
                                showTopToast(
                                    applicationContext,
                                    "Profile updated successfully!",
                                    "short",
                                    "positive"
                                )
                            }
//                        showTopSideToast(applicationContext, "Sorry! photo upload failed", "short")
                    }
                }
            } else {
                loadingDialog.dismissLoading()
                try {
                    val uid = auth!!.uid
                    val phone = auth!!.currentUser!!.phoneNumber
                    val fullName: String = binding!!.fullNameEt.text.toString()
                    val userName: String = binding!!.usernameEt.text.toString()
                    val bio: String = binding!!.bioEt.text.toString()
                    val profileImage: String =
                        sharedPref.getString(applicationContext, "dp").toString()
                    val user = User(uid, fullName, userName, bio, phone, profileImage)
                    database!!.reference
                        .child("users")
                        .child(uid!!)
                        .setValue(user)
                        .addOnCompleteListener { userTask ->
                            loadingDialog.dismissLoading()
                            if (userTask.isSuccessful) {
                                startActivity(
                                    Intent(
                                        this@EditProfile,
                                        AppBottomNav::class.java
                                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                )
                                showTopToast(
                                    applicationContext,
                                    "Profile updated successfully!",
                                    "short",
                                    "positive"
                                )
                            } else {
                                showTopToast(
                                    applicationContext,
                                    "Something went wrong! Try again",
                                    "short",
                                    "negative"
                                )
                            }
                        }
                        .addOnFailureListener {
                            showTopToast(
                                applicationContext,
                                "Something went wrong! Try again",
                                "short",
                                "negative"
                            )
                        }
                } catch (e: Exception) {
                    showTopToast(
                        applicationContext,
                        e.printStackTrace().toString(),
                        "long",
                        "neutral"
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                val uri = data.data
                val storage = FirebaseStorage.getInstance()
                val time = Date().time
                val reference = storage.reference
                    .child("Profile")
                    .child(time.toString() + "")
                reference.putFile(uri!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        reference.downloadUrl.addOnCompleteListener { uri ->
                            val filePath = uri.toString()
                            val obj = HashMap<String, Any>()
                            obj["image"] = filePath
                            database!!.reference
                                .child("users")
                                .child(FirebaseAuth.getInstance().uid!!)
                                .updateChildren(obj).addOnSuccessListener { }
                        }
                    }
                }
                binding!!.uploadBtn.setImageURI(data.data)
                selectedImage = data.data
            }
        }
    }
}