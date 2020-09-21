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
            add(
                LicenseData(
                    getString(R.string.recycler_license_link),
                    getString(R.string.recycler_license)
                )
            )
            add(
                LicenseData(
                    getString(R.string.material_license_link),
                    getString(R.string.material_license)
                )
            )
            add(
                LicenseData(
                    getString(R.string.retrofit_license_link),
                    getString(R.string.retrofit_license)
                )
            )
            add(
                LicenseData(
                    getString(R.string.roundImage_license_link),
                    getString(R.string.roundImage_license)
                )
            )
            add(
                LicenseData(
                    getString(R.string.kakao_license_link),
                    getString(R.string.kakao_license)
                )
            )
            add(
                LicenseData(
                    getString(R.string.google_license_link),
                    getString(R.string.google_license)
                )
            )
            add(
                LicenseData(
                    getString(R.string.okhttp_license_link),
                    getString(R.string.okhttp_license)
                )
            )
            Log.d("OPENSOURCE", "오픈소스 추가는 됨")

        }
        licenseAdapter.data = data
        licenseAdapter.notifyDataSetChanged()
    }
}