package com.capstone.btao

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.btao.api.ApiInterface
import com.capstone.btao.api.RetrofitClient
import com.capstone.btao.databinding.ActivityViolationFormBinding
import com.capstone.btao.models.Driver
import com.capstone.btao.models.PenaltyItem
import com.capstone.btao.models.Violation
import com.capstone.btao.request.AddPenaltyItemRequest
import com.capstone.btao.request.RemovePenaltyItemRequest
import com.capstone.btao.request.ViolationFormRequest
import com.capstone.btao.simpleprinter.PrintViolationActivity
import com.capstone.btao.simpleprinter.SimpleMainActivity
import com.fredoware.pacitapos.simpleprinter.PrinterCommands
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class ViolationFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViolationFormBinding
    var driverPenaltyId = 0

    var driver: Driver? = null
    var penalty_item_list: List<PenaltyItem>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViolationFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        driverPenaltyId = intent.extras?.getInt("driverPenaltyId", 0)!!
        getViolationForm(driverPenaltyId)

        binding.btnPrint.setOnClickListener {
            val intent = Intent(this, PrintViolationActivity::class.java)
            intent.putExtra("driverPenaltyId", driverPenaltyId)
            startActivity(intent)
        }

        binding.btnTestPrint.setOnClickListener {
            val intent = Intent(this, SimpleMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getViolationForm(driverPenaltyId: Int?) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        //Access api/violation-form.php
        val dataRequest = ViolationFormRequest(driverPenaltyId!!)
        val call = retrofitAPI.getViolationForm(dataRequest)

        call.enqueue(object : Callback<ViolationFormRequest?> {
            override fun onResponse(call: Call<ViolationFormRequest?>, response: Response<ViolationFormRequest?>) {

                // we are getting response from our body
                // and passing it to our modal class.
                val responseFromAPI: ViolationFormRequest? = response.body()

                driver = responseFromAPI?.driver
                val violation_list = responseFromAPI?.violation_list
                penalty_item_list = responseFromAPI?.penalty_item_list
                driverInformation(driver)
                attachViolationList(violation_list)
                attachPenaltyItemList(penalty_item_list)


            }

            override fun onFailure(call: Call<ViolationFormRequest?>, t: Throwable) {
                // setting text to our text view when
                // we get error response from API.


                Log.e("Login Error", t.message.toString())
            }
        })
    }

    private fun attachPenaltyItemList(penaltyItemList: List<PenaltyItem>?) {
        val groupLinear = LinearLayoutManager(this@ViolationFormActivity)
        binding.rvPenaltyItemList.layoutManager = groupLinear
        val adapter = PenaltyItemAdapter(this@ViolationFormActivity, penaltyItemList!!)
        binding.rvPenaltyItemList.adapter = adapter
    }

    private fun attachViolationList(violationList: List<Violation>?) {
        val groupLinear = LinearLayoutManager(this@ViolationFormActivity)
        binding.rvOptionList.layoutManager = groupLinear
        val adapter = ViolationAdapter(this@ViolationFormActivity, violationList!!)
        binding.rvOptionList.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun driverInformation(driver: Driver?) {
        binding.tvName.text = "${driver!!.firstName} ${driver.middleInitial}. ${driver.lastName}"
        binding.tvLicenseNumber.text = driver.licenseNumber
        binding.tvPlateNumber.text = driver.plateNumber
        binding.tvAddress.text = driver.address
        binding.tvColorBrandModel.text = "${driver.color} ${driver.brand}. ${driver.model}"
    }

    fun addToPenaltyItem(violationId: Int) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        //Access api/violation-form.php
        val dataRequest = AddPenaltyItemRequest(violationId, driverPenaltyId)
        val call = retrofitAPI.addPenaltyItem(dataRequest)

        call.enqueue(object : Callback<AddPenaltyItemRequest?> {
            override fun onResponse(call: Call<AddPenaltyItemRequest?>, response: Response<AddPenaltyItemRequest?>) {

                getViolationForm(driverPenaltyId)

            }

            override fun onFailure(call: Call<AddPenaltyItemRequest?>, t: Throwable) {
                // setting text to our text view when
                // we get error response from API.

                Log.e("Login Error", t.message.toString())
            }
        })
    }

    fun removePenaltyItem(itemId: Int) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        //Access api/violation-form.php
        val dataRequest = RemovePenaltyItemRequest(itemId)
        val call = retrofitAPI.removePenaltyItem(dataRequest)

        call.enqueue(object : Callback<RemovePenaltyItemRequest?> {
            override fun onResponse(call: Call<RemovePenaltyItemRequest?>, response: Response<RemovePenaltyItemRequest?>) {

                getViolationForm(driverPenaltyId)

            }

            override fun onFailure(call: Call<RemovePenaltyItemRequest?>, t: Throwable) {
                // setting text to our text view when
                // we get error response from API.

                Log.e("Login Error", t.message.toString())
            }
        })
    }


}
