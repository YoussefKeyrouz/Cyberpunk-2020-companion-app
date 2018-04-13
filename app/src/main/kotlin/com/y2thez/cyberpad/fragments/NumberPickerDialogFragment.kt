package com.y2thez.cyberpad.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.y2thez.cyberpad.R
import kotlinx.android.synthetic.main.number_picker_dialog.*

/**
 * Created by Y on 4/13/2018.
 */
class NumberPickerDialogFragment : DialogFragment(), View.OnClickListener {

    private var isNegated: Boolean = false
    private var buttons: MutableList<Button> = mutableListOf()
    private var mListener: NumberPickerListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.number_picker_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillButtons()
        plus_minus.setOnClickListener {
            negate()
        }
    }

    fun setListener(listener: NumberPickerListener) {
        mListener = listener
    }

    private fun fillButtons() {
        buttons.add(number_0)
        buttons.add(number_1)
        buttons.add(number_2)
        buttons.add(number_3)
        buttons.add(number_4)
        buttons.add(number_5)
        buttons.add(number_6)
        buttons.add(number_7)
        buttons.add(number_8)
        buttons.add(number_9)
        buttons.add(number_10)
        for (button in buttons) {
            button.setOnClickListener(this)
        }
    }

    private fun negate() {
        isNegated = !isNegated
        applyNegation(isNegated)
    }

    private fun applyNegation(negated: Boolean) {
        for (button in buttons) {
            if (button != number_0) {
                button.text = if (negated) "-${button.text}" else button.text.removePrefix("-")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onClick(view: View?) {
        if (view is Button) {
            val value = view.text.toString().toInt()
            mListener?.onValueClicked(value)
            dismiss()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NumberPickerListener) {
            mListener = context
        } else if (parentFragment  is NumberPickerListener){
            mListener = parentFragment as NumberPickerListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface NumberPickerListener {
        fun onValueClicked(value: Int)
    }

    companion object {

        fun newInstance(): NumberPickerDialogFragment {
            val fragment = NumberPickerDialogFragment()
            return fragment
        }
    }

}