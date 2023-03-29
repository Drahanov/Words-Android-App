package com.words.presentation.newWord.view.customview

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable


class CircleView(
    private val fillColor: Int,
    private val radius: Float
) :
    Drawable() {
    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas) {
        val x = bounds.centerX()
        val y = bounds.centerY()
        //draw fill color circle
        circlePaint.style = Paint.Style.FILL
        circlePaint.color = fillColor
        canvas.drawCircle(x.toFloat(), y.toFloat(), radius, circlePaint)
    }

    override fun setAlpha(alpha: Int) {
        circlePaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        circlePaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}