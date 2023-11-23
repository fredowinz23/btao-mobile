package com.capstone.btao.simpleprinter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.btao.api.ApiInterface
import com.capstone.btao.api.RetrofitClient
import com.capstone.btao.databinding.ActivitySmMainBinding
import com.capstone.btao.request.ViolationFormRequest
import com.fredoware.pacitapos.simpleprinter.PrinterCommands
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.OutputStream
import java.util.*

@SuppressLint("MissingPermission")
class PrintViolationActivity() : Activity(), Runnable {
    protected val TAG = "TAG"
    protected val REQUEST_CONNECT_DEVICE = 1
    protected val REQUEST_ENABLE_BT = 2
    protected val BT_ON = 3

    protected var mBluetoothAdapter: BluetoothAdapter? = null
    protected var applicationUUID = UUID
        .fromString("00001101-0000-1000-8000-00805F9B34FB")
    protected var mBluetoothConnectProgressDialog: ProgressDialog? = null
    protected var mBluetoothSocket: BluetoothSocket? = null
    protected var mBluetoothDevice: BluetoothDevice? = null
    protected var outputStream: OutputStream? = null
    var BILL = ""
    protected var printerName: String? = "JP58H-BT71_7EB1"
    protected var isChangingName = false
    protected var isTestingPrinter = false
    val bluetoothString = "86:67:7A:FA:A7:E0"



    var driverPenaltyId = 0

    lateinit var binding: ActivitySmMainBinding
    override fun onCreate(mSavedInstanceState: Bundle?) {
        super.onCreate(mSavedInstanceState)

        binding = ActivitySmMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        driverPenaltyId = intent.extras?.getInt("driverPenaltyId", 0)!!
        getViolationForm(driverPenaltyId)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    } // onCreate

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

                val driver = responseFromAPI?.driver
                val penalty_item_list = responseFromAPI?.penalty_item_list

                binding.mPrint.setOnClickListener{

                    isChangingName = false
                    isTestingPrinter = true

                    var bill = ""
                    bill += "${driver!!.firstName} ${driver.middleInitial}. ${driver.lastName} \n"
                    bill += "3 x 299 \t \t 299.50 \n"
                    bill += "\n"
                    bill += "Spicy Chicken and some drings \n"
                    bill += "3 x 299 \t \t 299.50 \n"
                    bill += "\n"
                    bill += "Spicy Chicken and some drings \n"
                    bill += "3 x 299 \t \t 299.50 \n"
                    bill += "\n"
                    bill += "Spicy Chicken and some drings \n"
                    bill += "3 x 299 \t \t 299.50 \n"
                    bill += "\n"
                    bill += "Spicy Chicken and some drings \n"
                    bill += "3 x 299 \t \t 299.50 \n"
                    bill += "\n"
                    printingProcess(bill, bluetoothString)

                }

            }

            override fun onFailure(call: Call<ViolationFormRequest?>, t: Throwable) {
                // setting text to our text view when
                // we get error response from API.


                Log.e("Login Error", t.message.toString())
            }
        })
    }


    fun printingProcess(BILL: String, name: String?) {
        mBluetoothDevice = mBluetoothAdapter!!.getRemoteDevice(name)
        try {
            mBluetoothSocket = mBluetoothDevice!!.createRfcommSocketToServiceRecord(applicationUUID)
            mBluetoothSocket!!.connect()
        } catch (eConnectException: IOException) {
            Toast.makeText(
                this@PrintViolationActivity,
                "The printer is not available. Check if it is on",
                Toast.LENGTH_SHORT
            ).show()
        }
        object : Thread() {
            override fun run() {
                try { //outputStream
                    outputStream = mBluetoothSocket!!.outputStream
                    if (isTestingPrinter) {
                        //invoice details
                        printConfig(BILL, 2, 1, 1) //align 1=center
                        printNewLine()
                    }
                    closeSocket(mBluetoothSocket) //close the connection
                } catch (e: Exception) {
                    Log.e("MainActivity", "Exe ", e)
                }
            }
        }.start()
    }

    protected fun printConfig(bill: String, size: Int, style: Int, align: Int) {
        //size 1 = large, size 2 = medium, size 3 = small
        //style 1 = Regular, style 2 = Bold
        //align 0 = left, align 1 = center, align 2 = right
        try {
            val format = byteArrayOf(27, 33, 0)
            val change = byteArrayOf(27, 33, 0)
            outputStream!!.write(format)

            //different sizes, same style Regular
            if (size == 1 && style == 1) //large
            {
                change[2] = 0x10.toByte() //large
                outputStream!!.write(change)
            } else if (size == 2 && style == 1) //medium
            {
                //nothing to change, uses the default settings
            } else if (size == 3 && style == 1) //small
            {
                change[2] = 0x3.toByte() //small
                outputStream!!.write(change)
            }

            //different sizes, same style Bold
            if (size == 1 && style == 2) //large
            {
                change[2] = (0x10 or 0x8).toByte() //large
                outputStream!!.write(change)
            } else if (size == 2 && style == 2) //medium
            {
                change[2] = 0x8.toByte()
                outputStream!!.write(change)
            } else if (size == 3 && style == 2) //small
            {
                change[2] = (0x3 or 0x8).toByte() //small
                outputStream!!.write(change)
            }
            when (align) {
                0 ->                     //left align
                    outputStream!!.write(PrinterCommands.ESC_ALIGN_LEFT)
                1 ->                     //center align
                    outputStream!!.write(PrinterCommands.ESC_ALIGN_CENTER)
                2 ->                     //right align
                    outputStream!!.write(PrinterCommands.ESC_ALIGN_RIGHT)
            }
            outputStream!!.write(bill.toByteArray())
        } catch (ex: Exception) {
            Log.e("error", ex.toString())
        }
    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy()
        try {
            if (mBluetoothSocket != null) mBluetoothSocket!!.close()
        } catch (e: Exception) {
            Log.e("Tag", "Exe ", e)
        }
    }

    override fun onBackPressed() {
        try {
            if (mBluetoothSocket != null) mBluetoothSocket!!.close()
        } catch (e: Exception) {
            Log.e("Tag", "Exe ", e)
        }
        setResult(RESULT_CANCELED)
        finish()
    }


    override fun run() {
        try {
            mBluetoothSocket = mBluetoothDevice!!.createRfcommSocketToServiceRecord(applicationUUID)
            mBluetoothAdapter!!.cancelDiscovery()
            mBluetoothSocket!!.connect()
            mHandler.sendEmptyMessage(0)
            Log.e("main run", "inside the main run")
        } catch (eConnectException: IOException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException)
            closeSocket(mBluetoothSocket)
            return
        }
    }

    protected fun closeSocket(nOpenSocket: BluetoothSocket?) {
        try {
            nOpenSocket!!.close()
            Log.d(TAG, "SocketClosed")
        } catch (ex: IOException) {
            Log.d(TAG, "CouldNotCloseSocket")
        }
    }

    protected var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            mBluetoothConnectProgressDialog!!.dismiss()
            // Toast.makeText(MainActivity.this, "Device Connected", Toast.LENGTH_SHORT).show();
        }
    }

    protected fun printNewLine() {
        try {
            outputStream!!.write(PrinterCommands.FEED_LINE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}