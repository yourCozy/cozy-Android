package com.example.cozy.views.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cozy.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_map.*

class BottomSheetFragment(private val sectionIdx : (Int) -> Unit) : BottomSheetDialogFragment(){

    private var num = 1;

    override fun getTheme(): Int = R.style.RoundBottomSheetDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_map,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_yongsan.setOnClickListener{
            num = 1
        }
        layout_mapo.setOnClickListener {
            num = 2

        }
        layout_gwanak.setOnClickListener {
            num = 3

        }
        layout_nowon.setOnClickListener {
            num = 4

        }
        layout_seocho.setOnClickListener {
            num = 5

        }
        layout_jongro.setOnClickListener {
            num = 6

        }
        btn_map.setOnClickListener {
            sectionIdx(num)
            val popF = this as BottomSheetDialogFragment
            popF.dismiss()
        }
    }

    fun yongsan_click(){
        layout_yongsan.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_yongsan.setTextColor(resources.getColor(R.color.white))
        tv_yongsan_num.setTextColor(resources.getColor(R.color.white))
        ys_view.visibility = View.INVISIBLE
    }
    fun mapo_click(){
        layout_mapo.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_mapo.setTextColor(resources.getColor(R.color.white))
        tv_mapo_num.setTextColor(resources.getColor(R.color.white))
        ys_view.visibility = View.INVISIBLE
        mp_view.visibility = View.INVISIBLE
    }

    fun gwanak_click(){
        layout_gwanak.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_gwanak.setTextColor(resources.getColor(R.color.white))
        tv_gwanak_num.setTextColor(resources.getColor(R.color.white))
        mp_view.visibility = View.INVISIBLE
        ga_view.visibility = View.INVISIBLE
    }

    fun nowon_click(){
        layout_nowon.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_nowon.setTextColor(resources.getColor(R.color.white))
        tv_yongsan_num.setTextColor(resources.getColor(R.color.white))
        ga_view.visibility = View.INVISIBLE
        nw_view.visibility = View.INVISIBLE
    }

    fun seocho_click(){
        layout_seocho.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_seocho.setTextColor(resources.getColor(R.color.white))
        tv_seocho_num.setTextColor(resources.getColor(R.color.white))
        nw_view.visibility = View.INVISIBLE
        sc_view.visibility = View.INVISIBLE
    }

    fun jongro_click(){
        layout_jongro.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_jongro.setTextColor(resources.getColor(R.color.white))
        tv_jongro_num.setTextColor(resources.getColor(R.color.white))
        sc_view.visibility = View.INVISIBLE
        gr_view.visibility = View.INVISIBLE
    }



}