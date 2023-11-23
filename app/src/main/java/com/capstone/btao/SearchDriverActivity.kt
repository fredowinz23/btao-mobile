package com.capstone.btao

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.btao.api.ApiInterface
import com.capstone.btao.api.RetrofitClient
import com.capstone.btao.api.UserSession
import com.capstone.btao.databinding.ActivitySearchDriverBinding
import com.capstone.btao.request.CreateDriverViolationRequest
import com.capstone.btao.request.DriverListRequest
import retrofit2.Call
import retrofit2.Response

class SearchDriverActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchDriverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.etSearch.addTextChangedListener {
            getPartsList(binding.etSearch.text.toString())
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getPartsList(keyword: String) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        val dataRequest = DriverListRequest(keyword)
        val call = retrofitAPI.searchDrivers(dataRequest)

        call.enqueue(object : retrofit2.Callback<DriverListRequest?> {
            override fun onResponse(call: Call<DriverListRequest?>, response: Response<DriverListRequest?>) {

                binding.progressBar.visibility = View.GONE

                val responseFromAPI: DriverListRequest? = response.body()

                val groupLinear = LinearLayoutManager(this@SearchDriverActivity)
                val data = responseFromAPI?.driver_list!!
                binding.rvList.layoutManager = groupLinear
                val adapter = DriverAdapter(this@SearchDriverActivity, data)
                binding.rvList.adapter = adapter
            }

            override fun onFailure(call: Call<DriverListRequest?>, t: Throwable) {

                binding.progressBar.visibility = View.GONE

                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@SearchDriverActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

    fun checkDialog(driverId: Int, driver: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Driver: ${driver}")
        builder.setMessage("Would you like to issue a ticket?")
        builder.setPositiveButton("Yes") { dialog, which ->
            createDriverViolation(driverId)
        }

        builder.setNegativeButton("No") { dialog, which ->
            Toast.makeText(this,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }

    private fun createDriverViolation(driverId: Int) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        val userSession = UserSession(this)
        val dataRequest = CreateDriverViolationRequest(userSession.username!!, driverId)
        val call = retrofitAPI.createDriverViolation(dataRequest)

        call.enqueue(object : retrofit2.Callback<CreateDriverViolationRequest?> {
            override fun onResponse(call: Call<CreateDriverViolationRequest?>, response: Response<CreateDriverViolationRequest?>) {

                binding.progressBar.visibility = View.GONE

                val responseFromAPI: CreateDriverViolationRequest? = response.body()

                val driverPenaltyId = responseFromAPI?.driverPenaltyId!!
                val intent = Intent(this@SearchDriverActivity, ViolationFormActivity::class.java)
                intent.putExtra("driverPenaltyId", driverPenaltyId)
                startActivity(intent)

            }

            override fun onFailure(call: Call<CreateDriverViolationRequest?>, t: Throwable) {

                binding.progressBar.visibility = View.GONE

                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@SearchDriverActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }
}