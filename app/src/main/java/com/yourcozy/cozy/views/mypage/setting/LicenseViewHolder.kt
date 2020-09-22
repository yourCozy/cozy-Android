package com.yourcozy.cozy.views.mypage.setting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yourcozy.cozy.R

class LicenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val licenseInfo : TextView = itemView.findViewById(R.id.tv_license)
    val licenseLink : TextView = itemView.findViewById(R.id.tv_license_link)

    fun bind(data: LicenseData){
        licenseLink.text = data.link
        licenseInfo.text = data.info
    }

}