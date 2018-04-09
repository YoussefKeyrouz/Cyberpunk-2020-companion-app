package com.y2thez.cyberpad.thirdparty

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.RelativeLayout

class SlidingRelativeLayout : RelativeLayout {
    private var yFraction = 0f
    private var xFraction = 0f

    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle)

    fun setYFraction(fraction: Float) {
        this.yFraction = fraction
        if (height == 0) {
            if (preDrawListener == null) {
                preDrawListener = ViewTreeObserver.OnPreDrawListener {
                    viewTreeObserver.removeOnPreDrawListener(
                            preDrawListener)
                    setYFraction(yFraction)
                    true
                }
                viewTreeObserver.addOnPreDrawListener(preDrawListener)
            }
            return
        }
        val translationY = height * fraction
        setTranslationY(translationY)
    }

    fun setXFraction(fraction: Float) {
        this.xFraction = fraction
        if (width == 0) {
            if (preDrawListener == null) {
                preDrawListener = ViewTreeObserver.OnPreDrawListener {
                    viewTreeObserver.removeOnPreDrawListener(
                            preDrawListener)
                    setXFraction(xFraction)
                    true
                }
                viewTreeObserver.addOnPreDrawListener(preDrawListener)
            }
            return
        }
        val translationX = width * fraction
        setTranslationX(translationX)
    }

    fun setGlide(fraction: Float) {
        val translationX = width * fraction
        setTranslationX(translationX)
        rotationY = 90 * fraction
        pivotX = 0f
    }

    fun setGlideBack(fraction: Float) {
        val translationX = width * fraction
        setTranslationX(translationX)
        rotationY = 90 * fraction
        pivotX = 0f
        pivotY = (height / 2).toFloat()
    }

    fun setCube(fraction: Float) {
        val translationX = width * fraction
        setTranslationX(translationX)
        rotationY = 90 * fraction
        pivotX = 0f
        pivotY = (height / 2).toFloat()
    }

    fun setCubeBack(fraction: Float) {
        val translationX = width * fraction
        setTranslationX(translationX)
        rotationY = 90 * fraction
        pivotY = (height / 2).toFloat()
        pivotX = width.toFloat()
    }

    fun setRotateDown(fraction: Float) {
        val translationX = width * fraction
        setTranslationX(translationX)
        rotation = 20 * fraction
        pivotY = height.toFloat()
        pivotX = (width / 2).toFloat()
    }

    fun setRotateUp(fraction: Float) {
        val translationX = width * fraction
        setTranslationX(translationX)
        rotation = -20 * fraction
        pivotY = 0f
        pivotX = (width / 2).toFloat()
    }

    fun setAccordionPivotZero(fraction: Float) {
        scaleX = fraction
        pivotX = 0f
    }

    fun setAccordionPivotWidth(fraction: Float) {
        scaleX = fraction
        pivotX = width.toFloat()
    }

    fun setTableHorizontalPivotZero(fraction: Float) {
        rotationY = 90 * fraction
        pivotX = 0f
        pivotY = (height / 2).toFloat()
    }

    fun setTableHorizontalPivotWidth(fraction: Float) {
        rotationY = -90 * fraction
        pivotX = width.toFloat()
        pivotY = (height / 2).toFloat()
    }

    fun setTableVerticalPivotZero(fraction: Float) {
        rotationX = -90 * fraction
        pivotX = (width / 2).toFloat()
        pivotY = 0f
    }

    fun setTableVerticalPivotHeight(fraction: Float) {
        rotationX = 90 * fraction
        pivotX = (width / 2).toFloat()
        pivotY = height.toFloat()
    }

    fun setZoomFromCornerPivotHG(fraction: Float) {
        scaleX = fraction
        scaleY = fraction
        pivotX = width.toFloat()
        pivotY = height.toFloat()
    }

    fun setZoomFromCornerPivotZero(fraction: Float) {
        scaleX = fraction
        scaleY = fraction
        pivotX = 0f
        pivotY = 0f
    }

    fun setZoomFromCornerPivotWidth(fraction: Float) {
        scaleX = fraction
        scaleY = fraction
        pivotX = width.toFloat()
        pivotY = 0f
    }

    fun setZoomFromCornerPivotHeight(fraction: Float) {
        scaleX = fraction
        scaleY = fraction
        pivotX = 0f
        pivotY = height.toFloat()
    }

    fun setZoomSlideHorizontal(fraction: Float) {
        translationX = width * fraction
        pivotX = (width / 2).toFloat()
        pivotY = (height / 2).toFloat()
    }

    fun setZoomSlideVertical(fraction: Float) {
        translationY = height * fraction
        pivotX = (width / 2).toFloat()
        pivotY = (height / 2).toFloat()
    }
}