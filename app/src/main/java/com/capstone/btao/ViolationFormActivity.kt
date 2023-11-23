package com.capstone.btao

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.btao.databinding.ActivityViolationFormBinding
import com.fredoware.pacitapos.simpleprinter.PrinterCommands
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class ViolationFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViolationFormBinding

    // For Printing
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
//    protected var printerName: String? = "JP58H-BT71_7EB1"
    protected var isChangingName = false
    protected var isTestingPrinter = false
    val bluetoothString = "86:67:7A:FA:A7:EO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViolationFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnPrint.setOnClickListener {
            printReceipt()
        }

    }

    private fun printReceipt() {
        val centerLine = "--------------------------------\n"

        var receipt = "\n\nWINGSXPRESS,\n"
        receipt += "SIZZLING HUB,\n\n"
        receipt += "Jasmin St Corner San Sebastian,\n"
        receipt += "Brgy 32, Bacolod City\n\n"
        receipt += centerLine

        printingProcess(receipt, bluetoothString)
    }

    fun printingProcess(BILL: String, name: String?) {
        mBluetoothDevice = mBluetoothAdapter!!.getRemoteDevice(name)
        try {
            mBluetoothSocket = mBluetoothDevice!!.createRfcommSocketToServiceRecord(applicationUUID)
            mBluetoothSocket!!.connect()
        } catch (eConnectException: IOException) {
            Toast.makeText(
                this@ViolationFormActivity,
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
                        printConfig(BILL, 2, 1, 0) //align 1=center
                        printNewLine()
                    }
                    closeSocket(mBluetoothSocket) //close the connection
                } catch (e: Exception) {
                    Log.e("MainActivity", "Exe ", e)
                }
            }
        }.start()
    }

    protected fun closeSocket(nOpenSocket: BluetoothSocket?) {
        try {
            nOpenSocket!!.close()
            Log.d(TAG, "SocketClosed")
        } catch (ex: IOException) {
            Log.d(TAG, "CouldNotCloseSocket")
        }
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

    protected fun printNewLine() {
        try {
            outputStream!!.write(PrinterCommands.FEED_LINE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}