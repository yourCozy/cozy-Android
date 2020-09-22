package com.yourcozy.cozy.views.mypage.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourcozy.cozy.R

class LicenseAdapter(private val context: Context, var data : MutableList<LicenseData>) : RecyclerView.Adapter<LicenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_license_info, parent, false)
        return LicenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: LicenseData){
        data.add(item)
    }
}