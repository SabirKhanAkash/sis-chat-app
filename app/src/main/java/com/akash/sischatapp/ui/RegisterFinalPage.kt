package com.akash.sischatapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.akash.sischatapp.databinding.ActivityRegisterFinalPageBinding
import com.akash.sischatapp.model.User
import com.akash.sischatapp.util.LoadingDialog
import com.akash.sischatapp.util.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import showTopSideToast
import java.util.Date

class RegisterFinalPage : ComponentActivity() {
    var binding: ActivityRegisterFinalPageBinding? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    private val sharedPref: SharedPref = SharedPref()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFinalPageBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val loadingDialog = LoadingDialog(this@RegisterFinalPage)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()

        binding!!.back.setOnClickListener { onBackPressed() }
        binding!!.uploadBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 45)
        }
        binding!!.continueBtn.setOnClickListener {
            loadingDialog.startLoading()
            if (selectedImage == null) {
                loadingDialog.dismissLoading()
                showTopSideToast(
                    this@RegisterFinalPage,
                    "Please upload your image",
                    "short"
                )
            }
            else {
                loadingDialog.dismissLoading()
                val reference = storage!!.reference.child("profile").child(auth!!.uid!!)
                reference.putFile(selectedImage!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        reference.downloadUrl.addOnCompleteListener { uri ->
                            val imageUri = uri.toString()
                            val uid = auth!!.uid
                            val phone = auth!!.currentUser!!.phoneNumber
                            val fullName: String? =
                                sharedPref!!.getString(applicationContext, "full_name")
                            val userName: String? =
                                sharedPref!!.getString(applicationContext, "user_name")
                            val bio: String? = sharedPref!!.getString(applicationContext, "bio")
                            val user = User(uid, fullName, userName, bio, phone, imageUri)
                            database!!.reference
                                .child("users")
                                .child(uid!!)
                                .setValue(user)
                                .addOnCompleteListener {
//                                    loadingDialog.dismissLoading()
                                    sharedPref.setString(applicationContext, "is_registered", "true")
                                    startActivity(Intent(this, ConfirmRegistation::class.java))
                                    finish()
                                }
                        }
                    }
                    else {
                        val uid = auth!!.uid
                        val phone = auth!!.currentUser!!.phoneNumber
                        val fullName: String? =
                            sharedPref!!.getString(applicationContext, "full_name")
                        val userName: String? =
                            sharedPref!!.getString(applicationContext, "user_name")
                        val bio: String? = sharedPref!!.getString(applicationContext, "bio")
                        val user = User(uid, fullName, userName, bio, phone, "No Image")
                        database!!.reference
                            .child("users")
                            .child(uid!!)
                            .setValue(user)
                            .addOnCanceledListener {
//                                loadingDialog.dismissLoading()
                                startActivity(Intent(this, ConfirmRegistation::class.java))
                                finish()
                            }
//                        showTopSideToast(applicationContext, "Sorry! photo upload failed", "short")
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null) {
            if(data.data != null) {
                val uri = data.data
                val storage = FirebaseStorage.getInstance()
                val time = Date().time
                val reference = storage.reference
                    .child("profile")
                    .child(time.toString() + "")
                reference.putFile(uri!!).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        reference.downloadUrl.addOnCompleteListener { uri ->
                            val filePath = uri.toString()
                            val obj = HashMap<String, Any>()
                            obj["image"] = filePath
                            database!!.reference
                                .child("users")
                                .child(FirebaseAuth.getInstance().uid!!)
                                .updateChildren(obj).addOnSuccessListener {  }
                        }
                    }
                }
                binding!!.uploadBtn.setImageURI(data.data)
                selectedImage = data.data
            }
        }
    }
}