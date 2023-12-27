package com.base.ecomm.ui

import android.content.Context
import android.util.AttributeSet


class UI_AdjustImageView : androidx.appcompat.widget.AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = drawable

        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right
            // edges
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height =
                Math.ceil((width.toFloat() * d.intrinsicHeight.toFloat() / d.intrinsicWidth).toDouble()).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}
