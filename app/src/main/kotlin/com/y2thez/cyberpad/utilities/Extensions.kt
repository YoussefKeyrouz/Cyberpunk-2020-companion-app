package com.y2thez.cyberpad.utilities

import android.app.Activity
import android.content.ContextWrapper
import android.view.View

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