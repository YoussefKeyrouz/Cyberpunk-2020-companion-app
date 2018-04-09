package com.y2thez.cyberpad.widgets

import android.content.Context
import android.util.AttributeSet
import android.preference.PreferenceManager
import android.support.v7.widget.AppCompatEditText
import com.y2thez.cyberpad.Cyberpad
import com.y2thez.cyberpad.R


/**
 * Created by Y on 4/7/2018.
 */

open class PreferenceEditText : AppCompatEditText {

    var preferenceKey : String? = null
        set(value) {
            field = value
            loadText()
        }
    var preferenceType : Int = 0
        set(value) {
            field = value
            loadText()
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        extractAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        extractAttributes(attrs)
    }

    private fun extractAttributes(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PreferenceEditText)
        for (i in 0 until a.indexCount) {
            val attr = a.getIndex(i)
            when (attr) {
                R.styleable.PreferenceEditText_preferenceKey -> {
                    preferenceKey = a.getString(attr)
                }

                R.styleable.PreferenceEditText_preferenceType -> {
                    preferenceType = a.getInt(attr, 0)
                }
            }
        }
        a.recycle()
        loadText()
    }

    private fun loadText() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val key = preferenceKey
        if (key != null) {
            when (preferenceType) {
                0 -> {
                    val value = prefs.getInt(preferenceKey, 0)
                    super.setText(value.toString())
                }
                1 -> {
                    val value = prefs.getString(preferenceKey, "")
                    super.setText(value)
                }
            }
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if(isFocused && text?.length ?: 0 > 0) {
            saveText(text?.toString())
        }
    }

    private fun saveText(text: String?) {
        val key = preferenceKey
        if (key != null) {
            val prefEdits = Cyberpad.prefsEdit
            when (preferenceType) {
                0 -> {
                    val value = text?.toIntOrNull() ?: 0
                    prefEdits.putInt(key, value)
                    prefEdits.commit()
                }
                1 -> {
                    prefEdits.putString(key, text ?: "")
                    prefEdits.commit()
                }
            }
        }

    }
}