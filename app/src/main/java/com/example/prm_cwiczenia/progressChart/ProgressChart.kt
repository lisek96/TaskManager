package com.example.prm_cwiczenia.progressChart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.prm_cwiczenia.R

class ProgressChart(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
    private var thispercentage = attributes.getInt(R.styleable.CustomView_percentage, 25)
    private val textPaint =
        Paint().apply {
            isAntiAlias = true
            color = Color.RED
            style = Paint.Style.FILL
        }

    private val textPaintGreen =
        Paint().apply {
            strokeWidth = 10f
            isAntiAlias = true
            color = Color.GREEN
            style = Paint.Style.STROKE
        }
    private val textPaintBlack =
        Paint().apply {
            strokeWidth = 12f
            isAntiAlias = true
            color = Color.BLACK
            style = Paint.Style.STROKE
        }

    private val border =
        Paint().apply {
            strokeWidth = 17f
            isAntiAlias = true
            color = Color.BLACK
            style = Paint.Style.STROKE
        }

    private val dsfsd =
        Paint().apply {
            strokeWidth = 2f
            textSize = 48f
            isAntiAlias = true
            color = Color.BLACK
            style = Paint.Style.FILL_AND_STROKE
        }


    override fun onDraw(canvas: Canvas?) {
        val density = resources.displayMetrics.density
        val ovalPaddingLeft = attributes.getInt(R.styleable.CustomView_leftpadding, 50)*density
        val ovalPaddingTop = attributes.getInt(R.styleable.CustomView_toppadding, 40)*density
        val circleMiddleX = 100f * density + ovalPaddingLeft
        val circleMiddleY = 100f * density + ovalPaddingTop
        val totalWidth = 200 * density + ovalPaddingLeft
        val totalHeight = 200 * density + ovalPaddingTop
        val totalBorderWidth = (200+3) * density + ovalPaddingLeft
        val totalBorderHeight = (200+3) * density + ovalPaddingTop

        canvas?.drawOval(RectF(ovalPaddingLeft, ovalPaddingTop,
            totalWidth, totalHeight), textPaint)
        canvas?.drawOval(RectF(ovalPaddingLeft-3*density, ovalPaddingTop-3*density
            , totalBorderWidth, totalBorderHeight), border)

        var percentage = thispercentage
        var lastPointX = 0f
        var lastPointY = 0f
        var currentI = 0f

        for (i in 0..getRightIterations(percentage)) {
            currentI = i.toFloat()
            var x = getX(circleMiddleX, circleMiddleY, ovalPaddingTop + i / 5.toFloat(), true)
            if(x.isNaN()){
                currentI = i.toFloat()
                break
            }
            canvas?.drawLine(circleMiddleX, circleMiddleY, x, ovalPaddingTop + i / 5.toFloat(), textPaintGreen)
            if (getRightIterations(percentage) == i) {
                lastPointX = x
                lastPointY = ovalPaddingTop + i / 5.toFloat()
            }
        }

        var firstX = 0f
        var firstY = 0f
        if(percentage>50) {
            for (i in getLeftIterations(percentage)..currentI.toInt()) {
                var x = getX(circleMiddleX, circleMiddleY, ovalPaddingTop + i / 5.toFloat(), false)
                if (x.isNaN()) {
                    break
                }else{
                    if(firstX == 0f){
                        firstX = x
                        firstY = ovalPaddingTop + i / 5.toFloat()
                    }
                }
                canvas?.drawLine(circleMiddleX, circleMiddleY,
                    x,
                    ovalPaddingTop + i / 5.toFloat(),
                    textPaintGreen
                )
            }
        }
        //lastpoints

        canvas?.drawLine(circleMiddleX, circleMiddleY+4*density, circleMiddleX, ovalPaddingTop, border)
        if(percentage == 50){
           lastPointX = circleMiddleX
            lastPointY = totalHeight
        }
        if(percentage > 50){
            lastPointY = firstY
            lastPointX = firstX
        }
        canvas?.drawLine(circleMiddleX, circleMiddleY, lastPointX, lastPointY, border)
        if(percentage <= 50){
            if(percentage>=38){
                canvas?.drawText("$percentage%", lastPointX+10*density, lastPointY+20*density, dsfsd)
            }else if(percentage<=11){
                canvas?.drawText("$percentage%", lastPointX, lastPointY-15*density, dsfsd)
            }
            else {
                canvas?.drawText("$percentage%", lastPointX + 17 * density, lastPointY, dsfsd)
            }
        }else{
            if(percentage <= 60){
                canvas?.drawText("$percentage%", lastPointX-30*density, lastPointY+30*density, dsfsd)
            }else if(percentage in 61..84){
                canvas?.drawText("$percentage%", lastPointX-44 * density, lastPointY+5*density, dsfsd)
            }else{
                canvas?.drawText("$percentage%", lastPointX-35 * density, lastPointY-15*density, dsfsd)
            }
        }
//        canvas?.drawText(lastPointX.toString(), 100*density, 100*density, dsfsd)
        super.onDraw(canvas)
    }

    fun getX(circleMiddleX: Float, circleMiddleY: Float, pointY: Float, rightSide: Boolean): Float {
        val density = resources.displayMetrics.density
        val p = ( circleMiddleX * circleMiddleX)- ((100*density)*(100*density))
        val c = ((pointY - circleMiddleY) * (pointY - circleMiddleY)) + p
        val b = -2 * circleMiddleX
        val delta = b * b - 4 * c
        val x1: Float = ((-b - Math.sqrt(delta.toDouble())) / 2).toFloat()
        val x2: Float = ((-b + Math.sqrt(delta.toDouble())) / 2).toFloat()
        var xToReturn = 0f;
        if (rightSide) {
            xToReturn = x2
        } else {
            xToReturn = x1;
        }
        return xToReturn
    }

    fun getRightIterations(percent: Int): Int {
        val full = 2800


        if (percent >= 50) {
            return full
        }
            return full / 50 * percent

    }

    fun getLeftIterations(percent: Int) : Int {
        return 2800 - (percent-50) * 2800/50
    }

    fun setPercentage(percent: Int){
        thispercentage = percent
    }
}