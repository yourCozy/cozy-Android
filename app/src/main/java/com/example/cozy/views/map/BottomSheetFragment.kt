package com.example.cozy.views.map

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface.OnShowListener
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.cozy.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_map.*


class BottomSheetFragment(private val sectionIdx : (Int) -> Unit) : BottomSheetDialogFragment() {

    private var num = 2
    //체크되었을 때
    var isCheckedys : Int = 0
    var isCheckedmp : Int = 0
    var isCheckedga : Int = 0
    var isCheckednw : Int = 0
    var isCheckedsc : Int = 0
    var isCheckedjr : Int = 0

    //커스텀
    //override fun getTheme(): Int = R.style.RoundBottomSheet

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_map,container,false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(R.layout.bottomsheet_map)

        dialog.setOnShowListener {
            val castDialog = it as BottomSheetDialog
            val bottomSheet = castDialog.findViewById<View?>(R.id.design_bottom_sheet)
            val behavior = bottomSheet?.let { it1 -> BottomSheetBehavior.from(it1) }
            if (behavior != null) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            behavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        layout_yongsan.setOnClickListener{
            //isChecked가 1일 때(체크되어 있을 때)
            if(isCheckedys !=0) {
                initunClick()
                initisChecked()
                btnEnabled()
            }
            else {
                initisChecked()
                num = 1
                mapo_unclick()
                gwanak_unclick()
                nowon_unclick()
                seocho_unclick()
                jongro_unclick()
                yongsan_click()
                isCheckedys = 1
                btnEnabled()
            }
        }
        layout_mapo.setOnClickListener {
            if(isCheckedmp !=0) {
                initunClick()
                initisChecked()
                btnEnabled()
            }
            else {
                initisChecked()
                num = 2
                yongsan_unclick()
                gwanak_unclick()
                nowon_unclick()
                seocho_unclick()
                jongro_unclick()
                mapo_click()
                isCheckedmp = 1
                btnEnabled()
            }
        }
        layout_gwanak.setOnClickListener {
            if(isCheckedga !=0) {
                initunClick()
                initisChecked()
                btnEnabled()
            }
            else {
                initisChecked()
                num = 3
                yongsan_unclick()
                mapo_unclick()
                nowon_unclick()
                seocho_unclick()
                jongro_unclick()
                gwanak_click()
                isCheckedga = 1
                btnEnabled()
            }

        }
        layout_nowon.setOnClickListener {
            if(isCheckednw !=0) {
                initunClick()
                initisChecked()
                btnEnabled()
            }
            else {
                initisChecked()
                num = 4
                yongsan_unclick()
                mapo_unclick()
                gwanak_unclick()
                seocho_unclick()
                jongro_unclick()
                nowon_click()
                isCheckednw = 1
                btnEnabled()
            }

        }
        layout_seocho.setOnClickListener {
            if(isCheckedsc !=0) {
                initunClick()
                initisChecked()
                btnEnabled()
            }
            else {
                initisChecked()
                num = 5
                yongsan_unclick()
                mapo_unclick()
                gwanak_unclick()
                jongro_unclick()
                nowon_unclick()
                seocho_click()
                isCheckedsc = 1
                btnEnabled()
            }

        }
        layout_jongro.setOnClickListener {
            if(isCheckedjr !=0) {
                initunClick()
                initisChecked()
                btnEnabled()
            }
            else {
                initisChecked()
                num = 6
                yongsan_unclick()
                mapo_unclick()
                gwanak_unclick()
                seocho_unclick()
                nowon_unclick()
                jongro_click()
                isCheckedjr = 1
                btnEnabled()
            }

        }
        btn_map.setOnClickListener {
            sectionIdx(num)
            this.dismiss()
        }
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 78 / 100
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }


    fun yongsan_click(){
        layout_yongsan.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_yongsan.setTextColor(resources.getColor(R.color.white))
        tv_yongsan_num.setTextColor(resources.getColor(R.color.white))
        ys_view.visibility = View.INVISIBLE

    }
    fun yongsan_unclick(){
        layout_yongsan.setBackgroundColor(resources.getColor(R.color.white))
        tv_yongsan.setTextColor(resources.getColor(R.color.black))
        tv_yongsan_num.setTextColor(resources.getColor(R.color.colorAccent))
        ys_view.visibility = View.VISIBLE
    }

    fun mapo_click(){
        layout_mapo.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_mapo.setTextColor(resources.getColor(R.color.white))
        tv_mapo_num.setTextColor(resources.getColor(R.color.white))
        ys_view.visibility = View.INVISIBLE
        mp_view.visibility = View.INVISIBLE
    }

    fun mapo_unclick(){
        layout_mapo.setBackgroundColor(resources.getColor(R.color.white))
        tv_mapo.setTextColor(resources.getColor(R.color.black))
        tv_mapo_num.setTextColor(resources.getColor(R.color.colorAccent))
        ys_view.visibility = View.VISIBLE
        mp_view.visibility = View.VISIBLE
    }

    fun gwanak_click(){
        layout_gwanak.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_gwanak.setTextColor(resources.getColor(R.color.white))
        tv_gwanak_num.setTextColor(resources.getColor(R.color.white))
        mp_view.visibility = View.INVISIBLE
        ga_view.visibility = View.INVISIBLE
    }

    fun gwanak_unclick(){
        layout_gwanak.setBackgroundColor(resources.getColor(R.color.white))
        tv_gwanak.setTextColor(resources.getColor(R.color.black))
        tv_gwanak_num.setTextColor(resources.getColor(R.color.colorAccent))
        mp_view.visibility = View.VISIBLE
        ga_view.visibility = View.VISIBLE
    }

    fun nowon_click(){
        layout_nowon.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_nowon.setTextColor(resources.getColor(R.color.white))
        tv_nowon_num.setTextColor(resources.getColor(R.color.white))
        ga_view.visibility = View.INVISIBLE
        nw_view.visibility = View.INVISIBLE
    }

    fun nowon_unclick(){
        layout_nowon.setBackgroundColor(resources.getColor(R.color.white))
        tv_nowon.setTextColor(resources.getColor(R.color.black))
        tv_nowon_num.setTextColor(resources.getColor(R.color.colorAccent))
        ga_view.visibility = View.VISIBLE
        nw_view.visibility = View.VISIBLE
    }

    fun seocho_click(){
        layout_seocho.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_seocho.setTextColor(resources.getColor(R.color.white))
        tv_seocho_num.setTextColor(resources.getColor(R.color.white))
        nw_view.visibility = View.INVISIBLE
        sc_view.visibility = View.INVISIBLE
    }

    fun seocho_unclick(){
        layout_seocho.setBackgroundColor(resources.getColor(R.color.white))
        tv_seocho.setTextColor(resources.getColor(R.color.black))
        tv_seocho_num.setTextColor(resources.getColor(R.color.colorAccent))
        nw_view.visibility = View.VISIBLE
        sc_view.visibility = View.VISIBLE
    }

    fun jongro_click(){
        layout_jongro.setBackgroundColor(resources.getColor(R.color.colorAccent))
        tv_jongro.setTextColor(resources.getColor(R.color.white))
        tv_jongro_num.setTextColor(resources.getColor(R.color.white))
        sc_view.visibility = View.INVISIBLE
        gr_view.visibility = View.INVISIBLE
    }

    fun jongro_unclick(){
        layout_jongro.setBackgroundColor(resources.getColor(R.color.white))
        tv_jongro.setTextColor(resources.getColor(R.color.black))
        tv_jongro_num.setTextColor(resources.getColor(R.color.colorAccent))
        sc_view.visibility = View.VISIBLE
        gr_view.visibility = View.VISIBLE
    }

    fun initunClick(){
        yongsan_unclick()
        mapo_unclick()
        gwanak_unclick()
        nowon_unclick()
        seocho_unclick()
        jongro_unclick()
    }

    fun initisChecked(){
        isCheckedys = 0
        isCheckedmp = 0
        isCheckedga = 0
        isCheckednw = 0
        isCheckedsc = 0
        isCheckedjr = 0
    }

    fun btnEnabled(){
        btn_map.isEnabled = !(isCheckedga == 0 && isCheckedjr == 0 && isCheckedmp == 0 && isCheckednw == 0
                && isCheckedsc == 0 && isCheckedys == 0)
        if (btn_map.isEnabled){
            btn_map.setBackgroundColor(resources.getColor(R.color.colorAccent))
        }
        else{
            btn_map.setBackgroundColor(resources.getColor(R.color.disabled))
        }

    }
    override fun onResume() {
        super.onResume()
    }

}