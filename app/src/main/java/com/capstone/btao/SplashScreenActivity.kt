package com.capstone.btao

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.capstone.btao.api.UserSession
import com.capstone.btao.auth.LoginActivity
import com.capstone.btao.simpleprinter.SimpleMainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val userSession = UserSession(this)
        Handler(Looper.getMainLooper()).postDelayed({
//            if (userSession.username.isNullOrBlank()){
//                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
//            }
//            else{
//                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
//            }
            startActivity(Intent(this@SplashScreenActivity, SimpleMainActivity::class.java))
            finish()
        }, 2000)
    }
}