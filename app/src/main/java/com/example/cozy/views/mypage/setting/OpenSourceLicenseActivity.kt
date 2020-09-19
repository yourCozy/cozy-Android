package com.example.cozy.views.mypage.setting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.R
import kotlinx.android.synthetic.main.activity_open_source.*

class OpenSourceLicenseActivity : AppCompatActivity() {
    val data = mutableListOf<LicenseData>()
    lateinit var licenseAdapter: LicenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)
        licenseAdapter = LicenseAdapter(applicationContext, data)

        rv_license.adapter = licenseAdapter
        loadLicense()
    }

    fun loadLicense(){
        data.apply {
            add(
                LicenseData(
                    getString(R.string.glide_license_link),
                    getString(R.string.glide_license)
                )
            )
            Log.d("OPENSOURCE", "오픈소스 추가는 됨")

        }
        licenseAdapter.data = data
        licenseAdapter.notifyDataSetChanged()
    }
}