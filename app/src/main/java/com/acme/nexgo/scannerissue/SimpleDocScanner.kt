package com.acme.nexgo.scannerissue

import android.util.Log
import com.ajohnson.dlparserkotlin.models.License
import com.ajohnson.dlparserkotlin.parsers.DLParser
import com.nexgo.oaf.apiv3.SdkResult
import com.nexgo.oaf.apiv3.device.scanner.OnScannerListener
import com.nexgo.oaf.apiv3.device.scanner.Scanner
import com.nexgo.oaf.apiv3.device.scanner.ScannerCfgEntity

private const val TAG = "DocScanner"

class SimpleDocScanner(private val scanner: Scanner) {

    private var listener: LicenseListener? = null

    fun setListener(listener: LicenseListener) {
        this.listener = listener
    }

    fun start() {
        val cfgEntity = ScannerCfgEntity().apply {
            isAutoFocus = true
            isUsedFrontCcd = false
            isBulkMode = true
            interval = 400
        }

        scanner.initScanner(cfgEntity, innerListener)
    }

    fun stop() {
        listener = null
        scanner.stopScan()
    }

    private val innerListener = object : OnScannerListener {
        override fun onInitResult(resultCode: Int) {
            when (resultCode) {
                SdkResult.Success -> {
                    Log.d(TAG, "Scanner Init Success")
                    scanner.startScan(60, this)
                }

                else -> {
                    Log.e(TAG, "Scanner Failed to Init: $resultCode")
                }
            }
        }

        override fun onScannerResult(resultCode: Int, result: String) {
            when (resultCode) {
                SdkResult.Success -> {
                    Log.d(TAG, "DocScanner onScannerResult")

                    runCatching {
                        val parser = DLParser(result)
                        parser.parse()
                    }.fold(
                        onSuccess = {
                            if (it.isAcceptable) {
                                Log.d(TAG, "DriverLicense found")
                                listener?.onLicense(it)
                            } else {
                                Log.d(TAG, "It is not a DriverLicense. Continue scanning")
                            }
                        },
                        onFailure = {
                            Log.w(TAG, it)
                            listener?.onError("Incorrect barcode")
                        }
                    )
                }

                SdkResult.Scanner_Customer_Exit -> {
                    Log.d(TAG, "Requested Exit Scanner OK.")
                    listener?.onError("Customer exit")
                }

                SdkResult.Scanner_Other_Error -> {
                    Log.e(TAG, "Got Scanner Error OtherError")
                    listener?.onError("Scanner error $resultCode")
                }

                else -> {
                    Log.e(TAG, "Other general error.")
                    listener?.onError("Scanner error $resultCode")
                }
            }
        }
    }

    interface LicenseListener {
        fun onLicense(license: License)
        fun onError(error: String)
    }
}
