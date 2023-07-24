package com.akash.sischatapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityOtpVerifyBinding
import com.akash.sischatapp.util.LoadingDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpVerify : ComponentActivity() {
    var countryCode: String = "+88"
    var binding: ActivityOtpVerifyBinding? = null
    var auth: FirebaseAuth? = null
    var verificationId: String? = null
    var resendEnabled: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val loadingDialog = LoadingDialog(this@OtpVerify)
        loadingDialog.startLoading()

        auth = FirebaseAuth.getInstance()
        binding!!.back.setOnClickListener { onBackPressed() }

        startCountDownTimer()
        binding!!.otpView.requestFocus()
        val phone = countryCode + intent.getStringExtra("phone")
        binding!!.phone.text = "Check your SMS messages. we've sent the verification code to $phone"

        val options = PhoneAuthOptions.newBuilder(auth!!).setPhoneNumber(phone!!)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(this@OtpVerify)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    val msg: FirebaseException = p0
                    Log.i("TAG", msg.toString())
//                    showTopSideToast(this@OtpVerify, "OTP sent successfully", "short")
//                    loadingDialog.dismissLoading()
//                    finish()
                }

                override fun onCodeSent(
                    verifyId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
//                    super.onCodeSent(verifyId, forceResendingToken)
                    loadingDialog.dismissLoading()
                    verificationId = verifyId
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    binding!!.otpView.requestFocus()
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding!!.resendOtpBtn.setOnClickListener {
            if (resendEnabled) {
                startCountDownTimer()
                loadingDialog.startLoading()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

        binding!!.otpView.setOtpCompletionListener { otp ->
            loadingDialog.startLoading()
            val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
            auth!!.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loadingDialog.dismissLoading()
                    var userDetails: FirebaseUser? = task.result.user
                    startActivity(Intent(this, RegisterPageOne::class.java))
                    finishAffinity()
                } else {
                    loadingDialog.dismissLoading()
                    binding!!.otpView.setText("")
                    binding!!.wrongOtpPrompt.visibility = View.VISIBLE
                    binding!!.otpView.setLineColor(Color.parseColor("#FB0000")) //RED
                }
            }
        }
        binding!!.continueBtn.isEnabled = false
        binding!!.continueBtn.setOnClickListener {
            startActivity(Intent(this, RegisterPageOne::class.java))
        }
    }

    //    Countdown timer for resend OTP button
    private fun startCountDownTimer() {
        resendEnabled = false
        binding!!.resendOtpBtn.setTextColor(Color.parseColor("#99000000"))
        object : CountDownTimer((60 * 1000).toLong(), 1000) {
            override fun onTick(l: Long) {
                binding!!.resendOtpBtn.text = "Resend OTP code"
                binding!!.timer.text = "" + l / 1000 + "s"
            }

            override fun onFinish() {
                resendEnabled = true
                binding!!.resendOtpBtn.text = "Resend OTP code"
                binding!!.timer.text = ""
                binding!!.resendOtpBtn.setTextColor(resources.getColor(R.color.blue_btn_color))
            }
        }.start()
    }
}