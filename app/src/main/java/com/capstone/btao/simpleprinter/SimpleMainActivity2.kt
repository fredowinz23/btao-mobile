//package com.fredoware.pacitapos.simpleprinter
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.app.ProgressDialog
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothDevice
//import android.bluetooth.BluetoothSocket
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Message
//import android.util.Log
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatDelegate
//import com.example.kotlinprinterversion.UnicodeFormatter
//import com.fredoware.pacitapos.databinding.ActivitySmMainBinding
//import java.io.IOException
//import java.nio.ByteBuffer
//import java.util.*
//
//@SuppressLint("MissingPermission")
//class SimpleMainActivity2() : Activity(), Runnable {
//    var mBluetoothAdapter: BluetoothAdapter? = null
//    private val applicationUUID = UUID
//        .fromString("00001101-0000-1000-8000-00805F9B34FB")
//    private var mBluetoothConnectProgressDialog: ProgressDialog? = null
//    private var mBluetoothSocket: BluetoothSocket? = null
//    var mBluetoothDevice: BluetoothDevice? = null
//
//    lateinit var binding: ActivitySmMainBinding
//    public override fun onCreate(mSavedInstanceState: Bundle?) {
//        super.onCreate(mSavedInstanceState)
//
//        binding = ActivitySmMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//
//        binding.Scan.setOnClickListener {
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//            if (mBluetoothAdapter == null) {
//                Toast.makeText(this@SimpleMainActivity2, "Message1", Toast.LENGTH_SHORT).show()
//            } else {
//                if (!mBluetoothAdapter!!.isEnabled) {
//                    val enableBtIntent = Intent(
//                        BluetoothAdapter.ACTION_REQUEST_ENABLE
//                    )
//                    startActivityForResult(
//                        enableBtIntent,
//                        REQUEST_ENABLE_BT
//                    )
//                } else {
//                    ListPairedDevices()
//                    val connectIntent = Intent(
//                        this@SimpleMainActivity2,
//                        DeviceListActivity::class.java
//                    )
//                    startActivityForResult(
//                        connectIntent,
//                        REQUEST_CONNECT_DEVICE
//                    )
//                }
//            }
//        }
//
//        binding.mPrint.setOnClickListener {
//            val test = TestPrint()
//            test.printLayout(mBluetoothSocket)
//        }
//        binding.dis.setOnClickListener {
//            if (mBluetoothAdapter != null) mBluetoothAdapter!!.disable()
//        }
//    } // onCreate
//
//
//    public override fun onActivityResult(
//        mRequestCode: Int, mResultCode: Int,
//        mDataIntent: Intent
//    ) {
//        super.onActivityResult(mRequestCode, mResultCode, mDataIntent)
//        when (mRequestCode) {
//            REQUEST_CONNECT_DEVICE -> if (mResultCode == RESULT_OK) {
//                val mExtra = mDataIntent.extras
//                val mDeviceAddress = mExtra!!.getString("DeviceAddress")
//                Log.v(
//                    TAG,
//                    "Coming incoming address $mDeviceAddress"
//                )
//                mBluetoothDevice = mBluetoothAdapter!!.getRemoteDevice(mDeviceAddress)
//                mBluetoothConnectProgressDialog = ProgressDialog.show(
//                    this,
//                    "Connecting...", mBluetoothDevice!!.name + " : "
//                            + mBluetoothDevice!!.address, true, false
//                )
//                val mBlutoothConnectThread = Thread(this)
//                mBlutoothConnectThread.start()
//                // pairToDevice(mBluetoothDevice); This method is replaced by
//                // progress dialog with thread
//            }
//            REQUEST_ENABLE_BT -> if (mResultCode == RESULT_OK) {
//                ListPairedDevices()
//                val connectIntent = Intent(
//                    this@SimpleMainActivity2,
//                    DeviceListActivity::class.java
//                )
//                startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE)
//            } else {
//                Toast.makeText(this@SimpleMainActivity2, "Message", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun ListPairedDevices() {
//        val mPairedDevices = mBluetoothAdapter!!.bondedDevices
//        if (mPairedDevices.size > 0) {
//            for (mDevice: BluetoothDevice in mPairedDevices) {
//                Log.v(
//                    TAG, "PairedDevices: " + mDevice.name + "  "
//                            + mDevice.address
//                )
//            }
//        }
//    }
//
//    override fun run() {
//        try {
//            mBluetoothSocket = mBluetoothDevice!!.createRfcommSocketToServiceRecord(applicationUUID)
//            mBluetoothAdapter!!.cancelDiscovery()
//            mBluetoothSocket!!.connect()
//            mHandler.sendEmptyMessage(0)
//        } catch (eConnectException: IOException) {
//            Log.d(TAG, "CouldNotConnectToSocket", eConnectException)
//            closeSocket(mBluetoothSocket)
//            return
//        }
//    }
//
//    private fun closeSocket(nOpenSocket: BluetoothSocket?) {
//        try {
//            nOpenSocket!!.close()
//            Log.d(TAG, "SocketClosed")
//        } catch (ex: IOException) {
//            Log.d(TAG, "CouldNotCloseSocket")
//        }
//    }
//
//    private val mHandler: Handler = object : Handler() {
//        override fun handleMessage(msg: Message) {
//            mBluetoothConnectProgressDialog!!.dismiss()
//            Toast.makeText(this@SimpleMainActivity2, "DeviceConnected", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun sel(`val`: Int): ByteArray {
//        val buffer = ByteBuffer.allocate(2)
//        buffer.putInt(`val`)
//        buffer.flip()
//        return buffer.array()
//    }
//
//    companion object {
//        protected val TAG = "TAG"
//        private val REQUEST_CONNECT_DEVICE = 1
//        private val REQUEST_ENABLE_BT = 2
//        fun intToByteArray(value: Int): Byte {
//            val b = ByteBuffer.allocate(4).putInt(value).array()
//            for (k in b.indices) {
//                println(
//                    "Selva  [" + k + "] = " + "0x"
//                            + UnicodeFormatter.byteToHex(b[k])
//                )
//            }
//            return b[3]
//        }
//    }
//}
