package com.example.cozy

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class CustomViewPager : ViewPager {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec

        val mode = MeasureSpec.getMode(heightMeasureSpec)

        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            var height = 0
//            for (i in 0 until childCount) {
//                val child = getChildAt(i)
//                child.measure(
//                    widthMeasureSpec,
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//                )
//                val h = child.measuredHeight
//                if (h > height) height = h
//            }

            val child: View = getChildAt(currentItem)
                child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
            height = child.measuredHeight

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}