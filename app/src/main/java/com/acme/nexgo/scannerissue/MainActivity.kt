package com.acme.nexgo.scannerissue

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ajohnson.dlparserkotlin.models.License
import com.nexgo.oaf.apiv3.APIProxy

class MainActivity : AppCompatActivity() {

    private lateinit var docScanner: SimpleDocScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            docScanner.start()
        }

        val textView = findViewById<TextView>(R.id.textView)

        val nexgoEngine = APIProxy.getDeviceEngine(applicationContext)
        val scanner = nexgoEngine.scanner

        docScanner = SimpleDocScanner(scanner)
        docScanner.setListener(object : SimpleDocScanner.LicenseListener {
            override fun onLicense(license: License) {
                textView.text = license.toString()
                // stop scanner. We found what we wanted
                docScanner.stop()
            }

            override fun onError(error: String) {
                textView.text = error
                // stop scanner. It failed
                docScanner.stop()
            }
        })
    }
}
