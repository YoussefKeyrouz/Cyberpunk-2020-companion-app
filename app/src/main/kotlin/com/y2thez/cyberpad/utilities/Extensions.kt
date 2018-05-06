package com.y2thez.cyberpad.utilities

import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color


/**
 * Created by Y on 4/13/2018.
 */

fun View.getParentActivity(): Activity? {
    var context = getContext()
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun View.getBitmap(): Bitmap {
    //Define a bitmap with the same size as the view
    val returnedBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    //Bind a canvas to it
    val canvas = Canvas(returnedBitmap)
    //Get the view's background
    val bgDrawable = this.background
    if (bgDrawable != null)
    //has background drawable, then draw it on the canvas
        bgDrawable.draw(canvas)
    else
    //does not have background drawable, then draw black background on the canvas
        canvas.drawColor(Color.BLACK)
    // draw the view on the canvas
    this.draw(canvas)
    //return the bitmap
    return returnedBitmap
}