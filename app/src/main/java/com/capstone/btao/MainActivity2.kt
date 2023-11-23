package com.capstone.btao

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.btao.api.UserSession
import com.capstone.btao.auth.LoginActivity
import com.capstone.btao.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val userSession = UserSession(this)

        binding.cvLogout.setOnClickListener {
            userSession.edit?.putString("username", "")
            userSession.edit?.apply()
            Log.e("Activity reached", "logout")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.cvSearchDriver.setOnClickListener {
            val intent = Intent(this, SearchDriverActivity::class.java)
            startActivity(intent)
        }
//        binding.cvJoined.setOnClickListener {
//            val intent = Intent(this, ProgramActivity::class.java)
//            intent.putExtra("programType", "joinedPrograms")
//            startActivity(intent)
//        }

//        binding.cvHistory.setOnClickListener {
//            val intent = Intent(this, ProgramActivity::class.java)
//            intent.putExtra("programType", "historyPrograms")
//            startActivity(intent)
//        }

    }
}