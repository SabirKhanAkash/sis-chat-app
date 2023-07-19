package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R

class WelcomeScreen : ComponentActivity() {
    private lateinit var _join_now_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        _join_now_btn = findViewById(R.id.join_now)
        _join_now_btn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
        }
//        setContent {
//            SiSChatAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting()
//                }
//            }
//        }
    }
}

//@Composable
//fun Greeting(modifier: Modifier = Modifier) {
//    GreetingLogo()
//}
//
//@Composable
//fun GreetingLogo() {
//    Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
//
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingPreview() {
//    SiSChatAppTheme {
//        Greeting()
//    }
//}