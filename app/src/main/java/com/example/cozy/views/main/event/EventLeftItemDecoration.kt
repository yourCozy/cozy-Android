package com.example.cozy.views.main.event

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EventLeftItemDecoration(context: Context, private val num: Int) : RecyclerView.ItemDecoration(){

    var size = dpToPx(context, num)
    var size2 = dpToPx(context, 20)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        when {
            position == 0 -> {
                outRect.left = size2
            }
            position == parent.adapter!!.itemCount -1 -> {
                //outRect.left = size
                outRect.right = size2
            }
            position != parent.adapter!!.itemCount -1 -> {
                outRect.left = size
            }
        }
    }

    private fun dpToPx(context: Context, dp:Int):Int{
        return return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            context.resources.displayMetrics).toInt()
    }
}