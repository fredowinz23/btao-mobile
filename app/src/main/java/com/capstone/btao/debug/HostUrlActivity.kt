package com.capstone.btao.debug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.btao.api.UserSession
import com.capstone.btao.auth.LoginActivity
import com.capstone.btao.databinding.ActivityHostUrlBinding

class HostUrlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostUrlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHostUrlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnChange.setOnClickListener {
            val newIP = binding.etIP.text.toString()
            val userSession = UserSession(this@HostUrlActivity)
            userSession.edit?.putString("base_url", "http://$newIP")
            userSession.edit?.apply()
            startActivity(Intent(this@HostUrlActivity, LoginActivity::class.java))
            finish()
        }
    }
}