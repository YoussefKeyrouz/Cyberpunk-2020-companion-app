package com.y2thez.cyberpad.widgets

/**
 * Created by Y on 3/8/2018.
 */
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

import java.util.Random

class MatrixEffect(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var canvasWidth: Int = 0
    private var canvasHeight: Int = 0
    private var canvas: Canvas? = null
    private var canvasBmp: Bitmap? = null
    private val fontSize = 40
    private var columnSize: Int = 0
    private val cars = "+-*/!^'([])#@&?,=$€°|%".toCharArray()
    private var txtPosByColumn: IntArray? = null
    private val paintTxt: Paint = Paint()
    private val paintBg: Paint
    private val paintBgBmp: Paint
    private val paintInitBg: Paint
    private val random = Random()

    init {
        paintTxt.style = Paint.Style.FILL
        paintTxt.color = Color.GREEN
        paintTxt.textSize = fontSize.toFloat()

        paintBg = Paint()
        paintBg.color = Color.BLACK
        paintBg.alpha = 5
        paintBg.style = Paint.Style.FILL

        paintBgBmp = Paint()
        paintBgBmp.color = Color.BLACK

        paintInitBg = Paint()
        paintInitBg.color = Color.BLACK
        paintInitBg.alpha = 255
        paintInitBg.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasWidth = w
        canvasHeight = h

        canvasBmp = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBmp!!)
        canvas!!.drawRect(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat(), paintInitBg)
        columnSize = canvasWidth / fontSize

        txtPosByColumn = IntArray(columnSize + 1)

        for (x in 0 until columnSize) {
            txtPosByColumn!![x] = random.nextInt(canvasHeight / 2) + 1
        }
    }

    private fun drawText() {
        for (i in txtPosByColumn!!.indices) {
            canvas!!.drawText("" + cars[random.nextInt(cars.size)], (i * fontSize).toFloat(), (txtPosByColumn!![i] * fontSize).toFloat(), paintTxt)

            if (txtPosByColumn!![i] * fontSize > canvasHeight && Math.random() > 0.975) {
                txtPosByColumn!![i] = 0
            }

            txtPosByColumn!![i]++
        }
    }

    private fun drawCanvas() {
        canvas!!.drawRect(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat(), paintBg)
        drawText()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBmp!!, 0f, 0f, paintBgBmp)
        drawCanvas()
        invalidate()
    }
}