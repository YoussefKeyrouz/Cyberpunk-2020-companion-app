package com.y2thez.cyberpad.widgets

import android.content.Context
import android.util.AttributeSet
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.TintContextWrapper
import com.y2thez.cyberpad.Cyberpad
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.fragments.NumberPickerDialogFragment
import android.content.ContextWrapper
import android.app.Activity
import com.y2thez.cyberpad.utilities.getParentActivity


/**
 * Created by Y on 4/7/2018.
 */

open class PreferenceButton : AppCompatButton, NumberPickerDialogFragment.NumberPickerListener {
    override fun onValueClicked(value: Int) {
        saveText(value.toString())
    }

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
        setupInput()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        extractAttributes(attrs)
        setupInput()
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

    private fun setupInput() {
        this.setOnClickListener {
            showNumberPickerDialog()
        }
    }

    private fun showNumberPickerDialog() {
        val parent = context
        var fm : FragmentManager? = null
        if(parent is Fragment) {
            fm = parent.childFragmentManager
        } else if (parent is FragmentActivity) {
            fm = parent.supportFragmentManager
        } else {
            val parentActivity = getParentActivity()
            if (parentActivity is FragmentActivity) {
                fm = parentActivity.supportFragmentManager
            }
        }
        if (fm == null) {
            return
        }
        val numberPickerDialogFragment = NumberPickerDialogFragment.newInstance()
        numberPickerDialogFragment.setListener(this)
        numberPickerDialogFragment.show(fm, "fragment_numbers")
    }

    fun saveText(text: String?) {
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
        super.setText(text)

    }

}